package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RequestNewActivity extends Activity {
	
	private TextView tv_show,tv_name,tv_verify;
	private Button btnAccept,btnRefuse;
	private MTDatabase db;
	private MyApp myApp;
	long pid1,pid2,userId1;
	String sname,str;
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.requestnew);
		
		db=MTDatabase.getInstance(this);
		myApp=(MyApp)getApplication();
		userId1=myApp.getUserId();
		
		tv_show=(TextView)findViewById(R.id.tv_show);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_verify=(TextView)findViewById(R.id.tv_verify);
		btnAccept=(Button)findViewById(R.id.btnAccept);
		btnRefuse=(Button)findViewById(R.id.btnRefuse);
		btnAccept.setOnClickListener(myListener);
		btnAccept.setOnClickListener(myListener);
		//Cursor cursor=db.getRequest(4);
		//pid1=Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id2")));
		//pid2=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
		
		/*int num=db.numRequest(userId1);
		if(num==0)
			setContentView(R.layout.no_inform);
		else{
			setContentView(R.layout.requestnew);*/
		
		Cursor cursor=db.getRequest(userId1);
		if(cursor!=null&&cursor.getCount()>=0){
			pid1=Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id1")));
			pid2=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
			Cursor c=db.getUser(pid1);
			sname=c.getString(c.getColumnIndex("username"));
			str=cursor.getString(cursor.getColumnIndex("content"));
			tv_name.setText(sname);
			tv_verify.setText("验证消息："+str);
			tv_show.setText(sname+"请求添加你为好友");
		}
		else
			tv_show.setText("没有新消息");
		//}
	}
	
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.btnAccept:
			{
				db.addfriend(pid1, userId1);
				tv_show.setText("已经同意");
				db.deleteRequest(pid2);
				finish();
				break;
			}
			case R.id.btnRefuse:
			{
				db.deleteRequest(pid2);
				tv_show.setText("已拒绝");
				finish();
				break;
			}
			}
		}
	};
	
	public void back(View view) {

        finish();
    }
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}

}
