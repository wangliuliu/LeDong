package com.example.baidumapmotiontrack.view;

import com.example.baidumapmotiontrack.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TitleLayout extends RelativeLayout{

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.title,this);
		Button btnBack=(Button)findViewById(R.id.btn_back);
		Button btnEdit=(Button)findViewById(R.id.btn_edit);
		btnBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				((Activity)getContext()).finish();
			}
		});
		
		btnEdit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(getContext(), "You click edit!", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
