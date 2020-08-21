import ext from "./utils/ext";

ext.runtime.onMessage.addListener(
  function(request, sender, sendResponse) {
    console.log("request = " + JSON.stringify(request) +"|" +JSON.stringify(sender));
    if(request.action === "setContent"){
      console.log("enter setContent");
      $.ajax({
        url: "http://127.0.0.1:8080/crx/setContent",
        data: JSON.stringify(request),
        contentType: "application/json;charset=utf-8",
        type: "POST",
        dataType: "json",
        success: function(data) {
          console.log("res = " + JSON.stringify(data));
        },
        error : function(e){
         console.log(e.status);
         console.log(e.responseText);
        }
      });

    }else if(request.action === "getCookie"){
      console.log("enter getCookie");
      chrome.cookies.getAll({
        url: request.url
      }, (cks) => {
        let cookie = cks.map((item) => {
            return item.name + "=" + item.value
          }).join(";") + ";";
        console.log(cookie);
        var data1 = {};
        data1.url= request.url;
        data1.cookie = cookie;
        console.log("getCookie =" + JSON.stringify(data1));
        $.ajax({
          url: "http://127.0.0.1:8080/crx/setCookie",
          data: JSON.stringify(data1),
          contentType: "application/json",
          type: "POST",
          dataType: "json",
          success: function(data) {
            console.log("res = " + JSON.stringify(data));
          },
          error : function(e){
           console.log(e.status);
           console.log(e.responseText);
          }
        });
      });
    }
    sendResponse({ action: "saved" });
  }
);
