package com.example.baidumapmotiontrack.model;

public class Praise {

	//public int p_id;//主键
	public String u_name;//用户姓名
	public int id;//说说id
	public int u_id;//用户id
	
	public Praise(){}

	
	
	public Praise(String u_name, int u_id) {
		this.u_name = u_name;
		this.u_id = u_id;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	
	
}
