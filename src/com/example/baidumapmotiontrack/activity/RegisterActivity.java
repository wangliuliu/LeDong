package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
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
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	private EditText nameText;
	private EditText pwdText;
	private EditText pwdText2;
	private EditText phoneText;
	private Button submit;
	private MTDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		//初始化组件
		nameText=(EditText)findViewById(R.id.register_name);
		pwdText=(EditText)findViewById(R.id.register_pwd);
		pwdText2=(EditText)findViewById(R.id.register_pwd2);
		phoneText=(EditText)findViewById(R.id.register_phone);
		submit=(Button)findViewById(R.id.register_submit);
		submit.setOnClickListener(this);
		//数据库
		db=MTDatabase.getInstance(this);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name=nameText.getText().toString();
		String pwd1=pwdText.getText().toString();
		String pwd2=pwdText2.getText().toString();
		String phone=phoneText.getText().toString();
		if(name==null||pwd1==null){
			Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
		}else if(db.findUser(name)!=null){
			Toast.makeText(this, "该用户名已被注册！", Toast.LENGTH_SHORT).show();
		}else if(!pwd1.equals(pwd2)){
			Toast.makeText(this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
		}else{
			User user=new User();
			user.setUsername(name);
			user.setPassword(pwd1);
			user.setPhone(phone);
			db.saveUser(user);
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
	}
}
