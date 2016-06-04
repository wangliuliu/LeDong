package com.example.baidumapmotiontrack.view;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.activity.CommunityActivity;
import com.example.baidumapmotiontrack.activity.MineActivity;
import com.example.baidumapmotiontrack.activity.MoreActivity;
import com.example.baidumapmotiontrack.activity.NewsActivity;
import com.example.baidumapmotiontrack.activity.RunActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomLayout extends LinearLayout {

	private MyOnClickListener listener;
	ImageView mineImage;
	TextView mineText;
	ImageView newImage;
	TextView newText;
	Button startRun;
	ImageView momentImage; 
	TextView momentText;
	ImageView moreImage;
	TextView moreText;

	Context context;
	public BottomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.bottom_layout, this);
		listener = new MyOnClickListener();

		// 初始化
		mineImage = (ImageView) findViewById(R.id.mine_image);
		mineText = (TextView) findViewById(R.id.mine_text);
		newImage = (ImageView) findViewById(R.id.new_image);
		newText = (TextView) findViewById(R.id.new_text);
		startRun = (Button) findViewById(R.id.start_run);
		momentImage = (ImageView) findViewById(R.id.moment_image);
		momentText = (TextView) findViewById(R.id.moment_text);
		moreImage = (ImageView) findViewById(R.id.more_image);
		moreText = (TextView) findViewById(R.id.more_text);

		// 添加监听事件
		mineImage.setOnClickListener(listener);
		mineText.setOnClickListener(listener);
		newImage.setOnClickListener(listener);
		newText.setOnClickListener(listener);
		startRun.setOnClickListener(listener);
		momentImage.setOnClickListener(listener);
		momentText.setOnClickListener(listener);
		moreImage.setOnClickListener(listener);
		moreText.setOnClickListener(listener);

	}

	public class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mine_image:
			case R.id.mine_text:
				Intent int0=new Intent(context,MineActivity.class);
				context.startActivity(int0);
				break;
			case R.id.new_image:
			case R.id.new_text:
				Intent int1=new Intent(context,NewsActivity.class);
				context.startActivity(int1);
				break;
			case R.id.start_run:
				//跳转到跑步活动
				Intent intent=new Intent(context,RunActivity.class);
				context.startActivity(intent);
				break;
			case R.id.moment_image:
			case R.id.moment_text:
				Intent intent3=new Intent(context,CommunityActivity.class);
				context.startActivity(intent3);
				break;
			case R.id.more_image:
			case R.id.more_text:
				Intent intent4=new Intent(context,MoreActivity.class);
				context.startActivity(intent4);
				break;
			default:
				break;
			}
		}

	}
}
