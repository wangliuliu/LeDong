package com.example.baidumapmotiontrack.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;



public class Moment  implements Comparable<Object>{
	public String login_user;
    public Integer id;
	public Integer user_id;
	public String username;
	public String  content;
	public Date time;
	
	public List<Praise> praiseList=new ArrayList<Praise>();
	public List<Comment> commentList=new ArrayList<Comment>();
	List<Praise> newPraise=new ArrayList<Praise>();//要添加的
	List<Comment> newComment=new ArrayList<Comment>();//要添加的
	
	public List<Praise> getNewPraise() {
		return newPraise;
	}

	public void setNewPraise(List<Praise> newPraise) {
		this.newPraise = newPraise;
	}

	public List<Comment> getNewComment() {
		return newComment;
	}

	public void setNewComment(List<Comment> newComment) {
		this.newComment = newComment;
	}


	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Praise> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<Praise> praiseList) {
		this.praiseList = praiseList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public Moment(){}
	
	public Moment(Integer user_id,String content){
		this.user_id=user_id;
		this.content=content;
		
	}
	
	
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
	
	public void saveComment(String content){
		Comment comment=new Comment(content,user_id,username,id);
		newComment.add(comment);
		commentList.add(comment);
	}
	
	public void withdrawPraise(){
		for(int i=0;i< newPraise.size();++i){
			if(newPraise.get(i).getU_name()==login_user){
				newPraise.remove(i);
				break;
			}
		}
		for(int i=0;i< praiseList.size();++i){
			if(praiseList.get(i).getU_name()==login_user){
				praiseList.remove(i);
				break;
			}
		}
		
	}
	
	public boolean find(Praise p,List<Praise> list){
		if(list==null) return false;
		for(Praise r: list){
		    if(p.getU_name().equals(r.getU_name()) && p.getId()==r.getId()){
		    	return true;
		    }
		    else
		    	continue;
		    	
		}
		return false;
	}
	
	
	public void savePraise(){
		Praise p = new Praise(login_user,id);
		if(!find(p,praiseList)){	
		   newPraise.add(p);
		   praiseList.add(p);
		}
		else
			return;
	}

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		Moment mo=(Moment)another;
		return getTime().compareTo(mo.getTime());
	}
	
}
