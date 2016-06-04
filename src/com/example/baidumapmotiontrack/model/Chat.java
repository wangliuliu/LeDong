package com.example.baidumapmotiontrack.model;

import java.util.Date;

import android.provider.ContactsContract.Data;

public class Chat {
		 
	 public int id;
	 public int user_id1;
	 public int user_id2;
	 public String username;
	 public String content;
	 public Date time;
	 private boolean isComMeg = true;
	 
	 public Chat(){
		 
	 }
	 public Chat(int id,int user_id1,int user_id2,String username,
			 String content,Date time,boolean isComMeg){
		 super();
		 this.id=id;
		 this.user_id1=user_id1;
		 this.user_id2=user_id2;
		 this.username=username;
		 this.content=content;
		 this.time=time;
		 this.isComMeg=isComMeg;
	 }
	 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUser_id1() {
		return user_id1;
	}
	
	public void setUser_id1(int user_id1) {
		this.user_id1 = user_id1;
	}
	
	public int getUser_id2() {
		return user_id2;
	}
	
	public void setUser_id2(int user_id2) {
		this.user_id2 = user_id2;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	 
	public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }

}
