package com.example.baidumapmotiontrack.activity;

import java.util.Date;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.Utility.FormatDouble;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Run;
import com.example.baidumapmotiontrack.model.User;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity extends Activity implements OnClickListener {

	private TextView distance;
	private TextView time;
	private TextView calorie;
	private TextView count;
	private TextView name;
	private LinearLayout seeRecord;
	private MTDatabase db;
	private MyApp myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_layout);
		// 获得数据库操作类的实例
		db = MTDatabase.getInstance(this);
		myApp = (MyApp) getApplication();

		// 组件初始化
		distance = (TextView) findViewById(R.id.mine_total_distance);
		time = (TextView) findViewById(R.id.mine_total_time);
		calorie = (TextView) findViewById(R.id.mine_total_calorie);
		count = (TextView) findViewById(R.id.count_text);
		name = (TextView) findViewById(R.id.mine_username);
		seeRecord = (LinearLayout) findViewById(R.id.run_record_line);
		seeRecord.setOnClickListener(this);

		updateUI();
	}

	public void updateUI() {
		int id = myApp.getUserId();
		User user = db.findUser(id);
		if(user!=null){
			Toast.makeText(this, "user id is"+user.getId(), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "user id is null", Toast.LENGTH_SHORT).show();
		}
		distance.setText(FormatDouble.format(user.getTotalDistance()) + "KM");
		time.setText(FormatDouble.format(user.getTotalTime() / 60) + "h");
		calorie.setText(FormatDouble.format(user.getTotalCalorie()) + "卡");
		count.setText(String.valueOf(user.getTotalCount()));
		name.setText(user.getUsername());
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
		Toast.makeText(this, "mineactivity destroy", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.run_record_line:
			Intent intent = new Intent(this, RunRecordActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		updateUI();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new Builder(MineActivity.this);
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