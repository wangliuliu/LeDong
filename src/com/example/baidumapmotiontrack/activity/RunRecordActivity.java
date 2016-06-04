package com.example.baidumapmotiontrack.activity;

import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.RunRecordAdapter;
import com.example.baidumapmotiontrack.Utility.FormatDouble;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Run;
import com.example.baidumapmotiontrack.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView; 

public class RunRecordActivity extends Activity{
    
	private List<Run> runList;
	
	private RunRecordAdapter adapter;
	
	private ListView listView;
	private TextView totalDistance;
	private TextView count;
	
	private MTDatabase db;
	
	private MyApp myApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.run_record);
		//��ʼ�����ݿ�
		db=MTDatabase.getInstance(this);
		
		myApp=(MyApp)getApplication();
	    int userId=myApp.getUserId();
	    
		//��ʼ�����
		listView=(ListView)findViewById(R.id.record_list_view);
		totalDistance=(TextView)findViewById(R.id.record_total_distance);
		count=(TextView)findViewById(R.id.record_count);
		
		User user=db.findUser(userId);
		totalDistance.setText("�����"+FormatDouble.format(user.getTotalDistance())+"KM");
		count.setText("��"+user.getTotalCount()+"����¼");
		
		//��ʼ���б�
		runList=db.loadRuns(userId);
		adapter=new RunRecordAdapter(this, R.layout.record_item, runList);
		listView.setAdapter(adapter);
	}
}
