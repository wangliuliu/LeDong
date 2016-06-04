package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class NewsContentActivity extends Activity{

	private TextView titleText;
	private TextView contentText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_content);
		titleText=(TextView)findViewById(R.id.a_title);
		contentText=(TextView)findViewById(R.id.a_content);
		Intent intent=getIntent();
		String title=intent.getStringExtra("news_title");
		String content=intent.getStringExtra("news_content");
        titleText.setText(title);
        contentText.setText(content);
	}
}
