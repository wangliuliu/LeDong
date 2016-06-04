package com.example.baidumapmotiontrack.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.ChatRecordAdapter;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Chat;
import com.example.baidumapmotiontrack.model.Moment;
import com.example.baidumapmotiontrack.model.News;
import com.example.baidumapmotiontrack.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatRecordActivity extends Activity {
	
	private List<Chat> list = new ArrayList<Chat>();
	private ListView chats;
	private ChatRecordAdapter adapter;
	
	private MTDatabase db;
	private MyApp myApp;
	private int userId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_record);
		
		db = MTDatabase.getInstance(this);
		myApp = (MyApp) getApplication();
		userId = myApp.getUserId();
		// 加载相应说说
		list = db.getAllChat(userId);
		// 初始化ListView
		setListViewData(list);
	}
	
	public void setListViewData(List<Chat> lists) {
		//ChatRecordAdapter adapter = new ChatRecordAdapter(ChatRecordActivity.this,R.layout.chatrecord_item, lists);
		adapter = new ChatRecordAdapter(ChatRecordActivity.this,
				R.layout.chatrecord_item, lists);
		chats = (ListView) findViewById(R.id.lv_chat);
		chats.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		// listView的点击事件
		chats.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				Chat chat = (Chat)adapter.getItem(position);
				String name=chat.getUsername();
				int pid=db.findUserId(name);
				System.out.println("id号"+pid);
				Intent intent=new Intent();
				intent.setClass(ChatRecordActivity.this, ChatActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("PID",pid);
				intent.putExtras(bundle);
				startActivity(intent);
				}
			});
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		list=db.getAllChat(userId);
		setListViewData(list);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
	public void back(View view) {

        finish();
    }

}
