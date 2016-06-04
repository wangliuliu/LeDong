package com.example.baidumapmotiontrack.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.NewsAdapter;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.model.News;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class NewsActivity extends Activity {

	private List<News> data = new ArrayList<News>();
	private NewsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_main);
		ListView listView = (ListView) findViewById(R.id.listView);
		initNews();
		adapter = new NewsAdapter(NewsActivity.this, R.layout.news_item, data);
		Log.d("mainActivity", "set adapter");
		listView.setAdapter(adapter);
	 	listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// News news=data.get(position);
				Intent intent = new Intent(NewsActivity.this,
						NewsContentActivity.class);
				News news = (News)adapter.getItem(position);
				intent.putExtra("news_title", news.getTitle());
				intent.putExtra("news_content",news.getContent());
				startActivity(intent);
			}

		});

	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
		Toast.makeText(this, "newsactivity destroy", Toast.LENGTH_SHORT).show();
	}
	

	public void initNews() {
		
		News news1 = new News(1, R.drawable.img01, "从黑夜跑出的那一道曙光", "有些风景或许只有跑过才更美，或许是因为疲惫，或许是因为你知道终点不远了");
		News news2 = new News(2, R.drawable.img02, "为什么跑步时你会岔气", "许多经常进行跑步锻炼的运动者一定有过类似的经历：在运动时，尤其是跑步过程中...");
		News news3 = new News(3, R.drawable.img03, "细节决定成败", "对于爱跑步的女生来说，运动文胸是最重要的装备之一。选择一件合适的运动文胸至关重要");
		News news4 = new News(4, R.drawable.img04, "没时间也能跑步吗", "解决没时间去跑步这个问题有三个重要途径，一是重视，二是提高效率，三是利用零散时间");
		News news5 = new News(5, R.drawable.img05, "如何将跑步进行到底",
				"首先要选择适合自己的运动方式，其次永远不要让自己厌恶升值憎恨跑步，最后，应该有效对抗心理因素导致的跑步计划泡汤");
		News news6 = new News(6, R.drawable.img06,
				"征服心中那座不可逾越的高山", "铁人就是不断挑战自我");
		News news7 = new News(7, R.drawable.img07, "晨跑or夜跑",
				"究竟晨跑与夜跑哪一个更好呢？下面让我们来做一个全方位的比较");
		News news8 = new News(8, R.drawable.img08,
				"一个跑者的自我修养", "随心所欲必闹笑话，还是先学些跑步的基本礼仪为佳。");
		News news9 = new News(9, R.drawable.img09, "兰州马拉松美食攻略", "兰州马拉松开赛在即，乐动特意为大家开一片美食专栏");
		News news10 = new News(10, R.drawable.img10, "跑步适宜的温度与湿度",
				"跑步适宜的温度与湿度");
		News news11 = new News(11, R.drawable.img11, "提高马拉松成绩的16种训练方法", "提高马拉松成绩的16种训练方法");
		data.add(news1);
		data.add(news2);
		data.add(news3);
		data.add(news4);
		data.add(news5);
		data.add(news6);
		data.add(news7);
		data.add(news8);
		data.add(news9);
		data.add(news10);
		data.add(news11);
		Log.d("mainActivity", "intiNews finish");
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
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	AlertDialog.Builder builder = new Builder(NewsActivity.this);
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
