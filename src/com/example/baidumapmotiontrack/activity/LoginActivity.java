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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText useText;
	private EditText pwdText;
	private Button login;
	private TextView toRegister;
	private TextView toFindPwd;

	private MTDatabase db;
	
	private MyApp myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout );

		db = MTDatabase.getInstance(this);

		myApp=(MyApp)getApplication();
		
		// 初始化组件
		useText = (EditText) findViewById(R.id.login_username);
		pwdText = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login_button);
		toRegister = (TextView) findViewById(R.id.to_register);
		toFindPwd = (TextView) findViewById(R.id.to_find_password);
		// 添加监听事件
		login.setOnClickListener(this);
		toRegister.setOnClickListener(this);


	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated methostub
		switch (v.getId()) {
		case R.id.login_button:
			String username = useText.getText().toString();
			String password = pwdText.getText().toString();
			User user = db.findUser(username);
			if (user == null) {
				Toast.makeText(this, "用户名不存在！请重新输入！", Toast.LENGTH_SHORT)
						.show();
			} else if (!user.getPassword() .equals(password)) {
				Toast.makeText(this, "密码与用户名不符！请重新输入！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Intent intent = new Intent(this, MineActivity.class);
				//intent.putExtra("isLogin", true);
				//intent.putExtra("user_id", user.getId());
				myApp.setUserId(user.getId());
				startActivity(intent);
			}
			break;
		case R.id.to_register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
