package com.example.baidumapmotiontrack.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.Distance;
import com.example.baidumapmotiontrack.Utility.FormatDouble;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.Utility.Optimize;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Run;
import com.example.baidumapmotiontrack.service.LocationService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends Activity {

	// ��ͼ���
	private MapView mapView;
	private BaiduMap baiduMap;
	private LocationClient locClient;
	private MyLocationListener myLocListener;

	// ��ʾ���ݵ����
	private TextView distanceText;
	private TextView timeText;
	private TextView speedText;
	private TextView calorieText;
	// ��ť
	private Button startBtn;
	private Button returnBtn;
	private Button stopBtn;
	private Button endBtn;

	// �����л��İ�ť��
	private RelativeLayout readyButtonLayout;
	private RelativeLayout runButtonLayout;

	// ���ڱ��澭γ��ı���
	private List<LatLng> points;// ������������ݿ��ȡ��һ�龭γ��
	private LatLng lastLL = null;// �ϴζ�ȡ�����һ����λ��
	private boolean isFirstLoc = true;
	private double distance;// �ƶ�����
	private int second;// ��
	private int fSecond;
	private int fMinute;
	private int fHour;
	private boolean startFlag = false;// ����Ƿ�����
	private boolean isRun=false;//�ܲ��Ƿ������δ��ʼ
	private Date date;

	// ��ť������
	private MyOnClickListener listener;

	// ���ݿ⸨����
	private MTDatabase db;

	private MyApp myApp;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.run_main);

		// ��ʼ�����
		distanceText = (TextView) findViewById(R.id.run_actual_distance);
		timeText = (TextView) findViewById(R.id.run_actual_time);
		speedText = (TextView) findViewById(R.id.run_actual_speed);
		calorieText = (TextView) findViewById(R.id.run_actual_calorie);
		startBtn = (Button) findViewById(R.id.run_start_button);
		returnBtn = (Button) findViewById(R.id.run_return_button);
		stopBtn = (Button) findViewById(R.id.run_stop_button);
		endBtn = (Button) findViewById(R.id.run_end_button);
		readyButtonLayout = (RelativeLayout) findViewById(R.id.ready_button_line);
		runButtonLayout = (RelativeLayout) findViewById(R.id.runing_button_line);
		// ��Ӽ����¼�
		listener = new MyOnClickListener();
		startBtn.setOnClickListener(listener);
		returnBtn.setOnClickListener(listener);
		stopBtn.setOnClickListener(listener);
		endBtn.setOnClickListener(listener);

		myApp = (MyApp) getApplication();

		// �������
		points = new ArrayList<LatLng>();
		db = MTDatabase.getInstance(this);
		// lastId = 0;
		distance = 0;
		// distance2 = 0;
		second++;
		date = new Date();

		// ��ʼ����ͼ���
		mapView = (MapView) findViewById(R.id.map_view);
		baiduMap = mapView.getMap();
		baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null));
		// ������λͼ��
		baiduMap.setMyLocationEnabled(true);
		// mapView.showZoomControls(false);
		locClient = new LocationClient(this);
		myLocListener = new MyLocationListener();
		locClient.registerLocationListener(myLocListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		locClient.setLocOption(option);
		locClient.start();

		Toast.makeText(getApplicationContext(), "first location",
				Toast.LENGTH_SHORT).show();
	}

	// �����˶�״̬
	private TimerTask timeTask = new TimerTask() {
		public void run() {
			if (startFlag) {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		}
	};

	// ����ʱ��
	private TimerTask timeTask2 = new TimerTask() {
		public void run() {
			if (startFlag) {
				second++;// ʱ���һ��
				Message message = new Message();
				message.arg1 = 1;
				handler.sendMessage(message);
			}

		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			switch (message.what) {
			case 1:
				drawRoute();
				// ���뱣����λС��
				double dis = FormatDouble.format(distance / 1000);
				distanceText.setText(dis + "KM");
				double spe = FormatDouble.format(distance / second * 3.6);
				speedText.setText(String.valueOf(spe));
				int cal = (int) (distance / 1000 * 86);
				calorieText.setText(String.valueOf(cal));
				break;
			default:
				break;
			}
			switch (message.arg1) {
			case 1:
				fSecond = second % 60;
				fMinute = second / 60;
				fHour = second / 3600;
				String str = "";
				if (fHour < 10) {
					str += "0" + fHour + ":";
				} else {
					str += fHour + ":";
				}
				if (fMinute < 10) {
					str += "0" + fMinute + ":";
				} else {
					str += fMinute + ":";
				}
				if (fSecond < 10) {
					str += "0" + fSecond;
				} else {
					str += fSecond;
				}
				timeText.setText(str);
				break;
			default:
				break;
			}
		}
	};

	// �������ӵ�·��
	public void drawRoute() {
		points.clear();
		// ��������ӵĶ�λ��
		points = db.loadAllLatLngs();
		if (points.size() > 2) {
			//db.clearLatLng();
			points=Optimize.pointsList(points);
			if (lastLL != null) {
				points.add(0, lastLL);
			}
			lastLL = points.get(points.size() - 1);
			OverlayOptions oPolyline = new PolylineOptions().width(10)
					.color(0xAAFF0000).points(points);
			baiduMap.addOverlay(oPolyline);
			distance += Distance.distance2(points);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyActivityManager.removeActivity(this);
		Toast.makeText(this, "runactivity destroy", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		drawRoute();
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**
			 * ��ʼ�ܲ�
			 */
			case R.id.run_start_button:
				locClient.stop();// �رջ�е�λ�ü�����
				// ��������
				intent = new Intent(RunActivity.this, LocationService.class);
				startService(intent);
				Toast.makeText(getApplicationContext(), "��ʼ�ܲ�!",
						Toast.LENGTH_SHORT).show();
				readyButtonLayout.setVisibility(View.GONE);
				runButtonLayout.setVisibility(View.VISIBLE);
				isRun=true;
				startFlag = true;
				isFirstLoc = true;
				// ��ʱ���켣
				Timer timer = new Timer();
				timer.schedule(timeTask, 0, 20000);// 10���ȡһ���µĹ켣��
				// �������
				Timer timer2 = new Timer();
				timer2.schedule(timeTask2, 0, 1000);
				break;
			case R.id.run_return_button:
				finish();
				break;
			case R.id.run_stop_button:
				Intent intent = new Intent(
						"com.example.baidumotiontrack.runstop");
				sendBroadcast(intent);
				if (startFlag) {
					startFlag = false;
					lastLL=null;
					stopBtn.setText("����");
				} else {
					startFlag = true;
					stopBtn.setText("��ͣ");
				}
				break;
			case R.id.run_end_button:
				new AlertDialog.Builder(RunActivity.this)
						.setTitle("��ʾ")
						.setMessage("�Ƿ�����ܲ���")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										isRun=false;
										startFlag = false;
										runButtonLayout
												.setVisibility(View.GONE);
										readyButtonLayout
												.setVisibility(View.VISIBLE);
										stopLocationService();
										saveRunRecord();
										initRunData();// �����ܲ�����
										RunActivity.this.finish();

									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();
				break;
			default:
				break;
			}
		}
	}

	public void stopLocationService() {
		Toast.makeText(getApplicationContext(), "stop service",
				Toast.LENGTH_SHORT).show();
		stopService(intent);
	}

	public void initRunData() {
		second=0;
		distanceText.setText("0.00KM");
		timeText.setText("00:00:00");
		speedText.setText("0.00");
		calorieText.setText("0");
	}

	// �����ܲ���¼
	public void saveRunRecord() {
		int userId = myApp.getUserId();
		Run run = new Run();
		run.setUserId(userId);
		Toast.makeText(getApplicationContext(), "distance="+FormatDouble.format(distance / 1000),Toast.LENGTH_SHORT).show();
		run.setDistance(FormatDouble.format(distance / 1000));// ��λǧ��
		run.setDate(date);
		run.setTime(FormatDouble.format(second * 1.0 / 60));
		run.setCalorie(FormatDouble.format(distance / 1000 * 86));
		db.saveRun(run);
	}

	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null || mapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				Toast.makeText(getApplicationContext(),
						"first location=" + location.getLatitude(),
						Toast.LENGTH_SHORT).show();
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(17.0f);
				baiduMap.animateMapStatus(MapStatusUpdateFactory
						.newMapStatus(builder.build()));
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(isRun){
				//����ܲ�δ�����������Ի�����ʾ
				new AlertDialog.Builder(RunActivity.this)
				.setTitle("��ʾ")
				.setMessage("�Ƿ�����ܲ���")
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								isRun=false;
								startFlag = false;
								runButtonLayout
										.setVisibility(View.GONE);
								readyButtonLayout
										.setVisibility(View.VISIBLE);
								stopLocationService();
								saveRunRecord();
								initRunData();// �����ܲ�����
								RunActivity.this.finish();

							}
						})
				.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();
			}else{
				Intent intent=new Intent(this,MineActivity.class);
				startActivity(intent);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
