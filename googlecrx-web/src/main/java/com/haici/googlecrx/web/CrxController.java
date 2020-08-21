package com.haici.googlecrx.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.haici.googlecrx.domain.Content;
import com.haici.googlecrx.domain.CookieInfo;
import com.haici.googlecrx.domain.R;
import com.haici.googlecrx.utils.HttpClientHelper;
import com.haici.googlecrx.utils.HttpHelper;
import com.haici.googlecrx.utils.callback.ResponseBodyCallback;

@RestController
@RequestMapping(value = "/crx")
public class CrxController {


    private final Logger logger = LoggerFactory.getLogger(CrxController.class);
    
    @Autowired
    private HttpHelper httpHelper;
    
    static Map<String,Map<String,String>>  cookieDB = new HashMap<String,Map<String,String>>();

    
    @RequestMapping(value = "setCookie", method = RequestMethod.POST)
    public R<Void> setCookie(@RequestBody CookieInfo cookieInfo) {
    	logger.info("setCookie ==>" + JSON.toJSONString(cookieInfo));
    	if(cookieInfo == null || cookieInfo.getUrl()==null || cookieInfo.getCookie()==null)
    		return R.sucess();
    	String domain = getDomain(cookieInfo.getUrl());
    	cookieDB.put(domain, cookieInfo.getCookieMap());
        return R.sucess();
    }
    
    private String getDomain(String url) {
    	if(url == null || url.equals("")) {
    		return null;
    	}
    	Pattern p =  Pattern.compile("(?isu)(http|https)://([^/]+)");
    	Matcher m = p.matcher(url);
    	if(m.find()) {
    		return m.group(2);
    	}
    	return null;
    }


    @RequestMapping(value = "setContent", method = RequestMethod.POST)
    public R<String> setContent(@RequestBody Content content) {
    	logger.info("setContent ==>" + JSON.toJSONString(content));
    	ResponseBodyCallback callback = null;
        callback = new ResponseBodyCallback("UTF-8");
    	String address = content.getSentUrl();
    	Map<String, String> data = new HashMap<String,String>();
    	data.put("abs", content.getAbs());
    	data.put("link", content.getLink());
    	data.put("reading", content.getReading());
    	data.put("site", content.getSite());
    	data.put("sortNo", content.getSortNo());
    	data.put("title", content.getTitle());
    	Map<String, String> cookies = cookieDB.get(getDomain(content.getSentUrl()));
    	Map<String, String> headers = new HashMap<String,String>();
    	if(content.getType().equals("0")) {
    		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    	}else {
    		headers.put("Content-Type", "application/json; charset=UTF-8");
    	}
    	try {
			httpHelper.post(address, data, "UTF-8", cookies, headers, callback);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new R<String>(200,"请求成功！",callback.getBody());
    }

    
    public static void main(String args[]) throws IOException {
    	HttpClientHelper hh = new HttpClientHelper();
    	ResponseBodyCallback callback = null;
        callback = new ResponseBodyCallback("UTF-8");
    	String address = "";
    	Map<String, String> data = new HashMap<String,String>();
    	data.put("abs", "程序测试摘要11");
    	data.put("link", "http://www.baidu.com");
    	data.put("reading", "0");
    	data.put("site", "baidu");
    	data.put("sortNo", "0");
    	data.put("title", "测试标题11");
    	Map<String, String> cookies = new HashMap<String,String>();
    	cookies.put("COOKIE_JSESSIONID", "1597915383680-F64EBAA6A36E64C6C64337");
    	Map<String, String> headers = new HashMap<String,String>();
    	headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    	int s =hh.post(address, data, "UTF-8", cookies, headers, callback);
    	System.out.println("code = " +s);
    	System.out.println(callback.getBody());
    }
}
