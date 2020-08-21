/**
 * 
 */
package com.haici.googlecrx.domain;

import java.io.Serializable;

/**
 * @author John
 *
 */
public class Content implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sentUrl = "";
	//headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	//headers.put("Content-Type", "application/json; charset=UTF-8");
	private String type = "0";
	private String abs;
	private String link;
	private String reading = "0";
	private String site;
	private String sortNo = "0";
	private String title;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSentUrl() {
		return sentUrl;
	}
	public void setSentUrl(String sentUrl) {
		this.sentUrl = sentUrl;
	}
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getReading() {
		return reading;
	}
	public void setReading(String reading) {
		this.reading = reading;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
