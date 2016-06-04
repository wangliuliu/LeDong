package com.example.baidumapmotiontrack.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Run {

	private int id;
	private int userId;
	private Date date;
	private double time;
	private double distance;
	private double calorie;

	public Run() {
		this.id = 0;
		this.date = null;
		this.time = 0;
		this.distance = 0;
		this.calorie = 0;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String dateString=format.format(date);
		return dateString;
	}

	public double getTime() {
		return time;
	}

	public double getDistance() {
		return distance;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
