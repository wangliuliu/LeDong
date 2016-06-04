package com.example.baidumapmotiontrack.model;

import com.example.baidumapmotiontrack.Utility.Utils;




public class User {
    private int id;
    private int photoId=0;
    private String username;
    private String password;
    private String phone;
    private double totalTime;
    private double totalDistance;
    private double totalCalorie;
    private int totalCount;
    private String location;
    private String pinyin;
	private String alpha;
    
    public User(){
    	this.username=null;
    	this.password=null;
    	this.phone=null;
    	this.totalTime=0;
    	this.totalDistance=0;
    	this.totalCalorie=0;
    	this.totalCount=0;
    	this.location=null;
    }
       
	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public double getTotalDistance() {
		return totalDistance;
	}
	public double getTotalCalorie() {
		return totalCalorie;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public String getLocation() {
		return location;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
		String py=Utils.toPinyin(username);
		this.setPinyin(py);
		String alpha=Utils.formatAlpha(py);
		this.setAlpha(alpha);
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	public void setTotalCalorie(double totalCalorie) {
		this.totalCalorie = totalCalorie;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
