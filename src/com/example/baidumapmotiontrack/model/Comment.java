package com.example.baidumapmotiontrack.model;

public class Comment {

	public int m_id;//主键
	public String comment_content;//评论内容
	public int u_id;//评论者id
	public String u_name;//用户姓名
	public int id;//说说id
	
	public Comment(){}
	
	public Comment( String comment_content, int u_id, String u_name) {
		super();
		this.comment_content = comment_content;
		this.u_id = u_id;
		this.u_name = u_name;
	}
	
	

	public Comment(String comment_content, int u_id, String u_name, int id) {
		super();
		this.comment_content = comment_content;
		this.u_id = u_id;
		this.u_name = u_name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	
	
	
	
}
