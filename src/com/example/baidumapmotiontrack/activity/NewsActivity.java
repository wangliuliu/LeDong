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
		
		News news1 = new News(1, R.drawable.img01, "�Ӻ�ҹ�ܳ�����һ�����", "��Щ�羰����ֻ���ܹ��Ÿ�������������Ϊƣ������������Ϊ��֪���յ㲻Զ��");
		News news2 = new News(2, R.drawable.img02, "Ϊʲô�ܲ�ʱ������", "��ྭ�������ܲ��������˶���һ���й����Ƶľ��������˶�ʱ���������ܲ�������...");
		News news3 = new News(3, R.drawable.img03, "ϸ�ھ����ɰ�", "���ڰ��ܲ���Ů����˵���˶�����������Ҫ��װ��֮һ��ѡ��һ�����ʵ��˶�����������Ҫ");
		News news4 = new News(4, R.drawable.img04, "ûʱ��Ҳ���ܲ���", "���ûʱ��ȥ�ܲ����������������Ҫ;����һ�����ӣ��������Ч�ʣ�����������ɢʱ��");
		News news5 = new News(5, R.drawable.img05, "��ν��ܲ����е���",
				"����Ҫѡ���ʺ��Լ����˶���ʽ�������Զ��Ҫ���Լ������ֵ�����ܲ������Ӧ����Ч�Կ��������ص��µ��ܲ��ƻ�����");
		News news6 = new News(6, R.drawable.img06,
				"������������������Խ�ĸ�ɽ", "���˾��ǲ�����ս����");
		News news7 = new News(7, R.drawable.img07, "����orҹ��",
				"����������ҹ����һ�������أ���������������һ��ȫ��λ�ıȽ�");
		News news8 = new News(8, R.drawable.img08,
				"һ�����ߵ���������", "������������Ц����������ѧЩ�ܲ��Ļ�������Ϊ�ѡ�");
		News news9 = new News(9, R.drawable.img09, "������������ʳ����", "���������ɿ����ڼ����ֶ�����Ϊ��ҿ�һƬ��ʳר��");
		News news10 = new News(10, R.drawable.img10, "�ܲ����˵��¶���ʪ��",
				"�ܲ����˵��¶���ʪ��");
		News news11 = new News(11, R.drawable.img11, "��������ɳɼ���16��ѵ������", "��������ɳɼ���16��ѵ������");
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
	            builder.setMessage("ȷ���˳���");
	            builder.setTitle("��ʾ");
	            builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener()
	            {
	                public void onClick(DialogInterface dialog,int which)
	                {
	                    dialog.dismiss();
	                   MyActivityManager.removeAll();
	                }
	            });

	            builder.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() 
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
