package com.example.baidumapmotiontrack.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Adapter.MomentAdapter.ViewHolder;
import com.example.baidumapmotiontrack.model.Chat;
import com.example.baidumapmotiontrack.model.Moment;

public class ChatRecordAdapter extends ArrayAdapter<Chat>{
	
	private int resourceId;

	public ChatRecordAdapter(Context context, int resource, List<Chat> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId=resource;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Chat chat=getItem(position);
		View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.username.setText(chat.getUsername());
        //viewHolder.context.setText(chat.getContent());
        /*Date t=moment.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        viewHolder.time.setText(format.format(t));*/
        return view;
	}
	
	class ViewHolder{
		TextView username;
		TextView context;
		//TextView time;
		
		public ViewHolder(){}
		
		public ViewHolder(View v){
			username=(TextView)v.findViewById(R.id.username);
			context=(TextView)v.findViewById(R.id.content);
			//time=(TextView)v.findViewById(R.id.time);
		}
	}

}
