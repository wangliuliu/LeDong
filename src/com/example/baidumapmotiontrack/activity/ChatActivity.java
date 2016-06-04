package com.example.baidumapmotiontrack.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;











import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.ChatAdapter;
import com.example.baidumapmotiontrack.Adapter.ChatRecordAdapter;
import com.example.baidumapmotiontrack.Adapter.MomentAdapter;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Chat;
import com.example.baidumapmotiontrack.model.Moment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatActivity extends Activity {
	
	private Button btn_send,btn_back;
	private EditText et_sendmessage;
	private ListView listView;
	
	private MTDatabase db;
	private MyApp myApp;
	private int userId;
	
	int pid;
	
	private ChatAdapter mAdapter;
	private List<Chat> mDataArrays = new ArrayList<Chat>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat);
        
        db = MTDatabase.getInstance(this);
        myApp=(MyApp)getApplication();
        
        //获取登录用户Id
      	userId=myApp.getUserId();
      	
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        btn_send=(Button)findViewById(R.id.btn_send);
        btn_back=(Button)findViewById(R.id.btn_back);
        et_sendmessage=(EditText)findViewById(R.id.et_sendmessage);
        listView = (ListView) findViewById(R.id.listview);
        btn_send.setOnClickListener(myListener);
        btn_back.setOnClickListener(myListener);
        
        Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		pid=bundle.getInt("PID");
		
		System.out.println("用户："+userId);
		System.out.println("联系人"+pid);
		
		// 加载聊天记录
		mDataArrays = db.getChat(userId,pid);
		// 初始化ListView
		setListViewData(mDataArrays);
        initData();
    }
	
	public void setListViewData(List<Chat> lists) {
		ChatAdapter adapter = new ChatAdapter(this,lists);
		//myMoments = (ListView) findViewById(R.id.myMoments);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	 public void initData()
	    {
	    	mAdapter = new ChatAdapter(this, mDataArrays);
			listView.setAdapter(mAdapter);
			
	    }
	
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			switch(v.getId())
			{
			case R.id.btn_send:
				send();
				break;
			case R.id.btn_back:
				finish();
				break;
			}
		}
	};
	
	//发送消息
	private void send()
	{
		String contString = et_sendmessage.getText().toString();
		if(contString.length() <= 0){
    		Toast.makeText(getApplicationContext(), "不能为空！",Toast.LENGTH_SHORT).show();
    	}
		else if (contString.length() > 0)
		{
			Chat entity = new Chat();
			entity.setUser_id1(userId);
			entity.setUser_id2(pid);
			Date date=new Date();
			entity.setTime(date);
			entity.setUsername(db.getUserName(userId));
			entity.setMsgType(false);
			entity.setContent(contString);
			
			db.saveChat(entity);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变			
			et_sendmessage.setText("");	// 清空编辑框数据  		
			listView.setSelection(listView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
}
