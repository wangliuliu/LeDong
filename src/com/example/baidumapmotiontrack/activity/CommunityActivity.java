package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommunityActivity extends Activity implements OnClickListener {

	private ImageView toContacts;
	private RelativeLayout toMoment,toMyfriend,rl_addfriend;
	private RelativeLayout rl_newinform,rl_chatrecord;
	private TextView tv_num;
	
	private MTDatabase db;
	private MyApp myApp;
	int uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.community);
		
		db=MTDatabase.getInstance(this);
		myApp=(MyApp)getApplication();
		uid=myApp.getUserId();
		
		// 初始化组件
		tv_num=(TextView) findViewById(R.id.tv_num);
		toContacts = (ImageView) findViewById(R.id.to_contacts_list);
		toMoment = (RelativeLayout) findViewById(R.id.to_moment);
		toMyfriend = (RelativeLayout) findViewById(R.id.to_myfriend);
		rl_addfriend = (RelativeLayout) findViewById(R.id.rl_addfriend);
		rl_newinform = (RelativeLayout) findViewById(R.id.rl_newinform);
		rl_chatrecord = (RelativeLayout) findViewById(R.id.rl_chatrecord);
		
		int a=db.numRequest(uid);
		String s=String.valueOf(a);
		
		if(a==0)
			tv_num.setVisibility(View.INVISIBLE);
		tv_num.setText(s);
		
		// 添加监听事件
		toContacts.setOnClickListener(this);
		toMoment.setOnClickListener(this);
		toMyfriend.setOnClickListener(this);
		rl_addfriend.setOnClickListener(this);
		rl_newinform.setOnClickListener(this);
		rl_chatrecord.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.to_contacts_list:
			Intent intent1 = new Intent(this, FriendsMainActivity.class);
			startActivity(intent1);
			break;
		case R.id.to_moment:
			Intent intent2 = new Intent(this, MomentActivity.class);
			startActivity(intent2);
			break;
		case R.id.to_myfriend:
			Intent intent3 = new Intent(this, FriendsMainActivity.class);
			startActivity(intent3);
			break;
		case R.id.rl_addfriend:
			Intent intent4 = new Intent(this, AddFriendActivity.class);
			startActivity(intent4);
			break;
		case R.id.rl_newinform:
			int num=db.numRequest(uid);
			if(num==0){
				Intent intent5 = new Intent(this, NoInformActivity.class);
				startActivity(intent5);
			}
			else{
				Intent intent5 = new Intent(this, RequestNewActivity.class);
				startActivity(intent5);
			}
			
			break;
		case R.id.rl_chatrecord:
			Intent intent6 = new Intent(this, ChatRecordActivity.class);
			startActivity(intent6);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new Builder(CommunityActivity.this);
            builder.setMessage("确认退出吗？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int which)
                {
                    dialog.dismiss();
                   MyActivityManager.removeAll();
                }
            });

            builder.setNegativeButton("取消",new DialogInterface.OnClickListener() 
            {
                public void onClick(DialogInterface dialog,int which) 
                {
                    dialog.dismiss();
                }
            });
            builder.create().show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
