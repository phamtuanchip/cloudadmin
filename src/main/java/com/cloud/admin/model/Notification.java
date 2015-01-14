package com.cloud.admin.model;

import java.util.Date;

public class Notification {
	Date lastUpdated;
	String title;
	String content;
	String type;
	public Notification(){}
	public Notification(String type){
		this.type = type;
	}
	public Notification(String type, Date date, String title, String content){
		this.type = type;
		this.lastUpdated = date;
		this.title = title;
		this.content = content;
	}
	
	public String toString(){
		return "[]";
	}
}
