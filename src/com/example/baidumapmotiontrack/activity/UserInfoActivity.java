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

public class UserInfoActivity extends Activity {

	// private TextView txtShow;
	private TextView tv_id, tv_name, tv_phone;
	private Button btnSend;
	int muId;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userinfo);
		tv_id = (TextView) findViewById(R.id.tv_id);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		// txtShow=(TextView)findViewById(R.id.tv_show);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(myListener);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String name = bundle.getString("NAME");
		String phone = bundle.getString("PHONE");
		int uId = bundle.getInt("uId");
		muId = uId;
		tv_id.setText("##" + uId);
		tv_name.setText(name);
		tv_phone.setText(phone);
		// String s="号："+uId+"\n\r"+"姓名："+name+"\n\r"+"联系电话："+phone;
		// txtShow.setText(s);
	}

	private Button.OnClickListener myListener = new Button.OnClickListener() {
		public void onClick(View v) {
			// finish();
			Intent intent = new Intent();
			intent.setClass(UserInfoActivity.this, ChatActivity.class);
			int pid = muId;
			Bundle bundle = new Bundle();
			bundle.putInt("PID", pid);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};

	public void back(View view) {

		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
}
