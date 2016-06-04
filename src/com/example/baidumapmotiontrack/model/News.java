package com.example.baidumapmotiontrack.model;

public class News {
	
	private int id;
	private int imageId;
	private String title;
	private String content;
	
	public News(int id,int imageId,String title,String content){
		this.id=id;
		this.imageId=imageId;
		this.title=title;
		this.content=content;
	}
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
