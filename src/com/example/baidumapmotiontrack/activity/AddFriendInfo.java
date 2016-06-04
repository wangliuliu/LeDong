package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AddFriendInfo extends Activity {
	
	private Button btnSend;
	private TextView tv_id,tv_name,tv_phone;
	String name,phone;
	int  pid;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userinfo);
		tv_id=(TextView)findViewById(R.id.tv_id);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_phone=(TextView)findViewById(R.id.tv_phone);
		btnSend=(Button)findViewById(R.id.btnSend);
		btnSend.setText("ÃÌº”∫√”—");
		btnSend.setOnClickListener(myListener);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		name=bundle.getString("NAME");
		phone=bundle.getString("PHONE");
		pid=bundle.getInt("PID");
		tv_id.setText("##"+pid);
		tv_name.setText(name);
		tv_phone.setText(phone);
	}
	
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			Intent intent = new Intent();
			intent.setClass(AddFriendInfo.this,SendVerifyNewsActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("NAME", name);
			bundle.putString("PHONE", phone);
			bundle.putInt("PID", pid);
			intent.putExtras(bundle);
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
