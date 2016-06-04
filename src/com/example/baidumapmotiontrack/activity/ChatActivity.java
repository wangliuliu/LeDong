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
        
        //��ȡ��¼�û�Id
      	userId=myApp.getUserId();
      	
        //����activityʱ���Զ����������
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
		
		System.out.println("�û���"+userId);
		System.out.println("��ϵ��"+pid);
		
		// ���������¼
		mDataArrays = db.getChat(userId,pid);
		// ��ʼ��ListView
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
	
	//������Ϣ
	private void send()
	{
		String contString = et_sendmessage.getText().toString();
		if(contString.length() <= 0){
    		Toast.makeText(getApplicationContext(), "����Ϊ�գ�",Toast.LENGTH_SHORT).show();
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
			mAdapter.notifyDataSetChanged();// ֪ͨListView�������ѷ����ı�			
			et_sendmessage.setText("");	// ��ձ༭������  		
			listView.setSelection(listView.getCount() - 1);// ����һ����Ϣʱ��ListView��ʾѡ�����һ��
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
}
