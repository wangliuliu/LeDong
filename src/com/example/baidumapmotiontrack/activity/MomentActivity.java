package com.example.baidumapmotiontrack.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.MomentAdapter;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.Moment;
import com.example.baidumapmotiontrack.model.User;
import com.example.baidumapmotiontrack.view.CustomListView;
import com.example.baidumapmotiontrack.view.CustomListView.OnLoadMoreListener;
import com.example.baidumapmotiontrack.view.CustomListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MomentActivity extends Activity {
	private List<Moment> list = new ArrayList<Moment>();
	private List<Moment> list1 = new ArrayList<Moment>();
	private List<Moment> tempList = new ArrayList<Moment>();

	private MyApp myApp;
	private MTDatabase db;

	public CustomListView myMoments;
	// private DBManager mgr;
	private ImageButton add;
	private final static int ACTIVITY_EDIT = 1; // 标记
	private int userId;
	private User user;
	private View header;
	public MomentAdapter adapter;
	int now = 0, count = 0, k = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.moment_main);
		
		add = (ImageButton) findViewById(R.id.add);

		db = MTDatabase.getInstance(this);
		myApp = (MyApp) getApplication();

		// myMoments=(ListView)findViewById(R.id.myMoments);
		/*
		 * mgr=new DBManager(this); //mgr.drop(); if(!mgr.isExistM())
		 * initMoment(); if(!mgr.isExistU()) initUser(); if(!mgr.isExistF())
		 * mgr.initFriends(); if(!mgr.isExistC()) mgr.initComment();
		 * if(!mgr.isExistP()) mgr.initPraise(); user=mgr.getUser("Tom");
		 */
		userId = myApp.getUserId();
		user = db.findUser(userId);
		init();
		// getDataAll(user.getId());
		// setListViewData(list);

		// add.setOnClickListener(new View.OnClickListener(){
		// public void onClick(View v){
		// Intent intent=new Intent();
		// intent.setClass(MainActivity.this, WriteMoment.class);
		// startActivityForResult(intent,ACTIVITY_EDIT);
		// }
		// });

		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				add.showContextMenu();
			}
		});

		add.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, 1, 0, "拍照");
				menu.add(0, 2, 0, "发动态");
			}

		});

	}

	public void init() {
		header = LayoutInflater.from(this).inflate(R.layout.micro_list_header,
				null);
		myMoments = (CustomListView) findViewById(R.id.myMoments);
		myMoments.setVerticalScrollBarEnabled(false);
		myMoments.setDivider(null);
		myMoments.addHeaderView(header);
		getDataAll(user.getId());
		getMicroList(0, true);
		setListViewData(list);

		// adapter=new MomentAdapter(MainActivity.this,list);
		// myMoments.setAdapter(adapter);
		//
		// myMoments.setOnRefreshListener(new OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// // TODO Auto-generated method stub
		// String s="下拉刷新";
		// getData(s);
		// }
		//
		// });
		// //上拉加载更多
		// myMoments.setOnLoadListener(new OnLoadMoreListener() {
		//
		// public void onLoadMore() {
		// String s="上拉加载更多";
		// getData(s);
		//
		// }
		// });
		//
	}

	public void getData(String s) {
		// TODO Auto-generated method stub
		if ("下拉刷新".equals(s)) {

			getMicroList(0, true);

			myMoments.onRefreshComplete();
		} else {
			getMicroList(now, true);

			myMoments.onLoadMoreComplete(); // 加载更多完成
		}
	}

	public void getMicroList(int a, boolean t) {
		if (list1.size() == 0) {
			return;
		}
		if (a == 0) {
			tempList.clear();
			tempList = list;
			list.clear();
			k = 0;

			if (list1 == null || list1.size() == 0) {
				if (a == 0) {
					myMoments.onRefreshComplete();
				} else {
					myMoments.onLoadMoreComplete(false);
				}
			} else {
				if (a == 0) {
					myMoments.onRefreshComplete();
				} else {
					myMoments.onLoadMoreComplete();
				}
				list = list1;
				count++;
			}
			k = list.size();
			now = k > 0 ? k - 1 : 0;
			setListViewData(list);
			// adapter.notifyDataSetChanged();
		}
	}

	// 拍照或发动态
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getGroupId()) {

		case 0: {
			switch (item.getItemId()) {
			case 1: {
				letCamera();
			}
				break;
			case 2: {
				Intent intent = new Intent();
				intent.setClass(MomentActivity.this, WriteMomentActivity.class);
				startActivityForResult(intent, ACTIVITY_EDIT);
			}
				break;
			} // end get item id
		}
		default:
			break;
		}
		return true;
	}

	public void setListViewData(List<Moment> lists) {
		adapter = new MomentAdapter(MomentActivity.this, lists);
		myMoments.setAdapter(adapter);
		myMoments.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				String s = "下拉刷新";
				getData(s);
			}

		});
		// 上拉加载更多
		myMoments.setOnLoadListener(new OnLoadMoreListener() {

			public void onLoadMore() {
				String s = "上拉加载更多";
				getData(s);

			}
		});

		adapter.notifyDataSetChanged();

	}

	// take a photo
	protected void letCamera() {
		// TODO Auto-generated method stub
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// String strImgPath = Environment.getExternalStorageDirectory()
		// .toString() + "/dlion/";// 存放照片的文件夹
		String strImgPath = Environment.getExternalStorageDirectory()
				+ File.separator + Environment.DIRECTORY_DCIM;
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()) + ".jpg";// 照片命名
		File out = new File(strImgPath);
		if (!out.exists()) {
			out.mkdirs();
		}
		out = new File(strImgPath, fileName);
		strImgPath = strImgPath + fileName;// 该照片的绝对路径
		Uri uri = Uri.fromFile(out);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(imageCaptureIntent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_EDIT) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String myContent = bundle.getString("CONTENT");
				Moment newMoment = new Moment(userId, myContent);
				db.saveMoment(newMoment);
				getDataAll(user.getId());
				setListViewData(list);
			}
			if (resultCode == RESULT_CANCELED) {
			}

		}

	}

	/*
	 * public void initMoment(){ List<Moment> moments=new ArrayList<Moment>();
	 * Moment m1=new Moment(1,"今天跑了3公里，继续加油！"); moments.add(m1); Moment m2=new
	 * Moment(2,"下雨天不能跑步，好伤心啊~~~"); moments.add(m2); Moment m3=new
	 * Moment(3,"今天你跑了吗？"); moments.add(m3); Moment m4=new
	 * Moment(4,"晚上，跑步和音乐更配哦！"); moments.add(m4); Moment m5=new
	 * Moment(5,"爱上跑步之后，整个人的精神面貌都不一样了。"); moments.add(m5); Moment m6=new
	 * Moment(6,
	 * "8月1日，今天收获的礼物就是和我们厦大福州学长跑鼓山，黑暗中不断地转弯、爬坡，8公里的上坡等同平地的13-14公里，一口气跑完……8月10日，早上，北京，吴敏训练营，见到好多熟人……8月13日，今早浦东源深体育场，‘全城热练世纪公园戈友群”转战这里，这会儿‘550’的跑友也在……"
	 * ); moments.add(m6); Moment m7=new Moment(7,"我爱跑步，我爱跑步，我爱跑步，重要的事情说三遍！！！");
	 * moments.add(m7); mgr.addM(moments); }
	 */

	/*
	 * public void initUser(){ List<User> users=new ArrayList<User>(); User
	 * u1=new User("Tom","123456","666555"); users.add(u1); User u2=new
	 * User("Amy","123456","222333"); users.add(u2); User u3=new
	 * User("Amber","123456","222111"); users.add(u3); User u4=new
	 * User("Sally","123456","000999"); users.add(u4); User u5=new
	 * User("Tim","123456","777888"); users.add(u5); User u6=new
	 * User("Sam","123456","444555"); users.add(u6); User u7=new
	 * User("Fancy","123456","666777"); users.add(u7); mgr.addU(users);
	 * 
	 * }
	 */

	public void getDataAll(int id) {
		list1 = db.queryAllMoment(id);
	}

	public void onPause() {
		super.onPause();
		for (Moment m : list) {
			if (m.getNewPraise() != null) {
				db.addP(m.getNewPraise());
			}
			if (m.getNewComment() != null) {
				db.addC(m.getNewComment());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
}
