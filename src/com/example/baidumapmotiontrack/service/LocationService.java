package com.example.baidumapmotiontrack.service;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.example.baidumapmotiontrack.Utility.Distance;
import com.example.baidumapmotiontrack.db.MTDatabase;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service{

	private LocationClient locationClient;
	private MyLocationListener myLocListener;
    private MTDatabase db;
    private boolean isSaveToDB;
    private MyReceiver myReceiver;
    
    private int wrongNum;
    private LatLng lastLL;
    
    private double last;
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onCreate(){
		super.onCreate();
		db=MTDatabase.getInstance(this);
		isSaveToDB=true;
		
		//注册广播接收器
		myReceiver=new MyReceiver();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("com.example.baidumotiontrack.runstop");//暂停或继续跑步
		registerReceiver(myReceiver,intentFilter);
		
        db.clearLatLng();
        
        wrongNum=1;
        lastLL=null;
        
        locationClient=new LocationClient(this);
        myLocListener = new MyLocationListener();
		locationClient.registerLocationListener(myLocListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		locationClient.setLocOption(option);
		locationClient.start();
     
        Toast.makeText(getApplicationContext(), "service start", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();	
		Toast.makeText(getApplicationContext(), "service stop", Toast.LENGTH_SHORT).show();
		db.clearLatLng();
		locationClient.stop();
	}
	
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null ) {
				return;
			}
			LatLng p=new LatLng(location.getLatitude(),location.getLongitude());
			//Toast.makeText(getApplicationContext(), " "+location.getLatitude(), Toast.LENGTH_SHORT).show();
			if(Distance.distance(lastLL, p)<(13*wrongNum)){//有效点
				if(wrongNum!=1) wrongNum=1;
				if(isSaveToDB){//存入到数据库
					db.saveLatLng(p);
					if(last!=location.getLatitude()){
						Toast.makeText(getApplicationContext(), " "+location.getLatitude(), Toast.LENGTH_SHORT).show();
					}
					last=location.getLatitude();
				}else{
					Toast.makeText(getApplicationContext(), "暂停中", Toast.LENGTH_SHORT).show();
				}	
			}else{
				wrongNum++;
				Toast.makeText(getApplicationContext(), "wrongNum="+wrongNum, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "receivered broadcast", Toast.LENGTH_SHORT).show();;
			if(isSaveToDB){
				isSaveToDB=false;
				Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
			}else{
				isSaveToDB=true;
			}
		}		
	}
	
}
