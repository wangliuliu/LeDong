package com.example.baidumapmotiontrack.activity;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NoInformActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.no_inform);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}

}
