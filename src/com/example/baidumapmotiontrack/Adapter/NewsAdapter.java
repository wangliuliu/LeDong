package com.example.baidumapmotiontrack.Adapter;

import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.model.News;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<News>{
	
	private int resourceId;
	
	public NewsAdapter(Context context, int resource, List<News> objects) {
		//Log.d("mainActivity", "construct1");
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId=resource;
		Log.d("mainActivity", "construct");
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		News news=getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
			Log.d("mainActivity", "start load item layout");
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.newsImage=(ImageView)view.findViewById(R.id.news_image);
			viewHolder.newsTitle=(TextView)view.findViewById(R.id.news_title);
			Log.d("mainActivity", "finish load item layout");
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		viewHolder.newsImage.setImageResource(news.getImageId());
		viewHolder.newsTitle.setText(news.getTitle());
		Log.d("mainActivity", "success set item message");
		return view;
	}
	
	class ViewHolder{
		ImageView newsImage;
		TextView newsTitle;
	}

}
