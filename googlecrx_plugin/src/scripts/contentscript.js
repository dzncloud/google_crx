import ext from "./utils/ext";

$(function(){
 
  var fdom = '<div id="fudong" style="position: absolute; right: 200px; top: 100px;padding:10px 5px 15px;">'
  +'<input id="baidu_hot" type="button" value="百度热点添加" /><br/>'
  +'<input id="zhihu_hot" type="button" value="知乎热点添加" /><br/>'
  +'<input id="weibo_hot" type="button" value="微博热点添加" /><br/>'
  +'</div>';
  $('body').append(fdom);
  $("#baidu_hot").click(function(){
    var tdata = extractTags();
    tdata.site="baidu";
    tdata.action="setContent";
    chrome.runtime.sendMessage(tdata, async function (response) {
      console.log(response);
      alert("添加成功");
    });
  });
  $("#zhihu_hot").click(function(){
    var tdata = extractTags();
    tdata.site="zhihu";
    tdata.action="setContent";
    chrome.runtime.sendMessage(tdata, async function (response) {
      console.log(response);
      alert("添加成功");
    });
  });
  $("#weibo_hot").click(function(){
    var tdata = extractTags();
    tdata.site="weibo";
    tdata.action="setContent";
    chrome.runtime.sendMessage(tdata, async function (response) {
      console.log(response);
      alert("添加成功");
    });
  });
});
var extractTags = () => {
  var url = document.location.href;
  if(!url || !url.match(/^http/)) return;

  var data = {
    //标题
    title: "",
    //描述
    abs: "",
    //链接地址
    link: document.location.href
  }

  var ogTitle = document.querySelector("meta[property='og:title']");
  if(ogTitle) {
    data.title = ogTitle.getAttribute("content")
  } else {
    data.title = document.title
  }

  var descriptionTag = document.querySelector("meta[property='og:description']") || document.querySelector("meta[name='description']")
  if(descriptionTag) {
    data.abs = descriptionTag.getAttribute("content")
  }

  return data;
}

//获取浏览器cookie
window.addEventListener("load", myMain, false);

function getCookies(url) {
  chrome.runtime.sendMessage({url: url,action:"getCookie"}, async function (response) {
    console.log(response);
  });
}

function myMain(evt) {
  console.log("Cookie share helper running!");
  getCookies(document.URL)
}