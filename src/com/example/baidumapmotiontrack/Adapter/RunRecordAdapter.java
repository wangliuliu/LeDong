package com.example.baidumapmotiontrack.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.model.Run;

public class RunRecordAdapter extends ArrayAdapter<Run>{

	private int resourceId;
	
	public RunRecordAdapter(Context context,int resource,List<Run> object){
		super(context,resource,object);
		this.resourceId=resource;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		View view;
		Run run=getItem(position);
		ViewHolder viewHolder;
		if(convertView==null){
			view=(View)LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.itemDate=(TextView)view.findViewById(R.id.item_date);
			viewHolder.itemDistance=(TextView)view.findViewById(R.id.item_distance);
			viewHolder.itemTime=(TextView)view.findViewById(R.id.item_time);
			viewHolder.itemCalorie=(TextView)view.findViewById(R.id.item_calorie);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		viewHolder.itemDate.setText(run.getDate());
		viewHolder.itemDistance.setText(run.getDistance()+"KM");
		viewHolder.itemTime.setText(run.getTime()+"min");
		viewHolder.itemCalorie.setText(run.getCalorie()+"¿¨");
		return view;
	}
	
	class ViewHolder{
		TextView itemDate;
		TextView itemDistance;
		TextView itemTime;
		TextView itemCalorie;
	}
}
