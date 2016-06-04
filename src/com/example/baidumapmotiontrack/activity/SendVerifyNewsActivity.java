package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SendVerifyNewsActivity extends Activity {
	
	private EditText et_reason;
	private Button btnSend;
	private MTDatabase db;
	private MyApp myApp;
	int pid,userId1;
	
	public void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sendverify);
		
		db=MTDatabase.getInstance(this);
		myApp=(MyApp)getApplication();
		userId1=myApp.getUserId();
		
		et_reason=(EditText)findViewById(R.id.et_reason);
		btnSend=(Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(myListener);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		String name=bundle.getString("NAME");
		String phone=bundle.getString("PHONE");
		pid=bundle.getInt("PID");
	}
	
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			String str=et_reason.getText().toString();
			db.addRequest(userId1, pid,str);
			Intent intent=new Intent();
			intent.setClass(SendVerifyNewsActivity.this, FriendsMainActivity.class);
			startActivity(intent);
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
