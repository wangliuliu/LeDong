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
import android.widget.TextView;
import android.widget.Toast;

public class AddFriendActivity extends Activity {
	
	private EditText et_search;
	//private TextView tv_show,tv_wait;
	private Button btnSearch,btnAdd;
	private MTDatabase db;
	private MyApp myApp;
	int userId1;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addfriend);
		
		
		db=MTDatabase.getInstance(this);
		myApp=(MyApp)getApplication();
		et_search=(EditText)findViewById(R.id.et_search);
		//tv_show=(TextView)findViewById(R.id.tv_show);
		//tv_wait=(TextView)findViewById(R.id.tv_wait);
		btnSearch=(Button)findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(myListener);
		userId1=myApp.getUserId();
	}
	
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			String pname=et_search.getText().toString();
			User user = db.findUser(pname);
			
			if (user == null) {
				Toast.makeText(getApplication(), "您查找的用户不存在", Toast.LENGTH_SHORT).show();
				et_search.setText("");
			}
			else{
				int aid=user.getId();
			    System.out.println("aid"+aid);
				if(db.isFriend(userId1, aid)==1){
					Toast.makeText(getApplication(), "您查找的用户已经是您的好友", Toast.LENGTH_SHORT).show();
					et_search.setText("");
				}
				else if(db.isFriend(userId1, aid)==2){
					Toast.makeText(getApplication(), "您查找的用户是您本人", Toast.LENGTH_SHORT).show();
					et_search.setText("");
				}
				else if(db.isFriend(userId1, aid)==0){
					Intent intent=new Intent();
				    intent.setClass(AddFriendActivity.this, AddFriendInfo.class);
				    int pid=user.getId();
				    String uname=user.getUsername();
				    String pphone=user.getPhone();
				    Bundle bundle=new Bundle();
				    bundle.putString("NAME", uname);
				    bundle.putString("PHONE", pphone);
				    bundle.putInt("PID", pid);
				    intent.putExtras(bundle);
				    startActivity(intent);
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
