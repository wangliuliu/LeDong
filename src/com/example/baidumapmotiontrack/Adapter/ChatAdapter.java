
package com.example.baidumapmotiontrack.Adapter;

import android.R.integer;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.model.Chat;

public class ChatAdapter extends BaseAdapter {
	
	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0; //收到消息
		int IMVT_TO_MSG = 1;  //发送消息
	}
	
    private static final String TAG = ChatAdapter.class.getSimpleName();

    private List<Chat> coll;

    private Context ctx;
    
    private LayoutInflater mInflater;

    public ChatAdapter(Context context, List<Chat> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    


	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	Chat entity = coll.get(position);
	 	
	 	if (entity.getMsgType())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;  //收到消息
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;  //发送消息
	 	}
	 	
	}

    //消息类型总数
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	Chat entity = coll.get(position);
    	boolean isComMsg = entity.getMsgType();
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (isComMsg)
			  {
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			  }else{
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  }

	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg = isComMsg;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	
	    Date t=entity.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        viewHolder.tvSendTime.setText(format.format(t));
	    viewHolder.tvUserName.setText(entity.getUsername());
	    viewHolder.tvContent.setText(entity.getContent());
	    
	    return convertView;
    }
    

    static class ViewHolder { 
        public Object time;
		public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }


}
