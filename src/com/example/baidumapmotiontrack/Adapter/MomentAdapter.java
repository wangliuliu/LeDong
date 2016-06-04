package com.example.baidumapmotiontrack.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;









import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.activity.MomentActivity;
import com.example.baidumapmotiontrack.model.Comment;
import com.example.baidumapmotiontrack.model.Moment;
import com.example.baidumapmotiontrack.model.Praise;
import com.example.baidumapmotiontrack.view.MyCustomDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MomentAdapter extends BaseAdapter{
	//private int resourceId;
	private LayoutInflater mInflater;
	// 定义操作面板状态常量
	public static final int PANEL_STATE_GONE = 0;
	public static final int PANEL_STATE_VISIABLE = 1;
	//操作面板状态
	public static int panelState = PANEL_STATE_GONE;
	private String praiseFlag;//点赞标示，判断这个人有没有点过
	private MomentActivity mContext;//上下文
	private List<Moment> moments;
	
	public MomentAdapter(MomentActivity context, List<Moment> objects) {
		mInflater = LayoutInflater.from(context);
		mContext=context;
		moments=objects;
		//super(context, resource, objects);
		// TODO Auto-generated constructor stub
		//resourceId=resource;
	}
	
	public String getPraiseTag(Moment moment){
		String tag="";
		if(moment.getPraiseList().size()>0){
        	int k=0;
        	for(int i=0;i<moment.praiseList.size();++i){
        		if(moment.getLogin_user().equals(moment.praiseList.get(i).getU_name())){
        			tag="Y";
        			k=1;
        			break;
        		}
        		else continue;
        	}
        	if(k==0)
        		tag="N";	
        	
        }
        else
        	tag="N";
		
		return tag;
	}
	
	private void switchPanelState(LinearLayout liearLayoutIgnore,Button btnComment,Button btnPraise) {
		// TODO Auto-generated method stub
		switch (panelState) {
		case PANEL_STATE_GONE:
			
			liearLayoutIgnore.setVisibility(View.GONE);
			btnComment.setVisibility(View.GONE);
			btnPraise.setVisibility(View.GONE);
			break;
		case PANEL_STATE_VISIABLE:
//			holder.liearLayoutIgnore.startAnimation(animation);//评论的显示动画
			liearLayoutIgnore.setVisibility(View.VISIBLE);
			btnComment.setVisibility(View.VISIBLE);
			btnPraise.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final Moment moment=moments.get(position);
		View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            //view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        	view =  mInflater.inflate(R.layout.cotent_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.username.setText(moment.getUsername());
        viewHolder.context.setText(moment.getContent());
        Date t=moment.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        viewHolder.time.setText(format.format(t));
        
        //viewHolder.btnPraise.setTag(getItem(position).getPraiseflag());//点赞标示，用来判断是否点过
//        if(moment.getPraiseList().size()>0){
//        	int k=0;
//        	for(int i=0;i<moment.praiseList.size();++i){
//        		if(moment.getLogin_user().equals(moment.praiseList.get(i).getU_name())){
//        			viewHolder.btnPraise.setTag("Y");
//        			k=1;
//        			break;
//        		}
//        		else continue;
//        	}
//        	if(k==0)
//        		viewHolder.btnPraise.setTag("N");	
//        	
//        }
//        else
//        	viewHolder.btnPraise.setTag("N");
        
        viewHolder.btnPraise.setTag(getPraiseTag(moment));
        
        viewHolder.btnIgnore.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				praiseFlag=viewHolder.btnPraise.getTag().toString();
				viewHolder.liearLayoutIgnore.setVisibility(View.VISIBLE);
				if("Y".equals(praiseFlag)){
					viewHolder.btnPraise.setText("取消");
					//moment.withdrawPraise();
				}else if("N".equals(praiseFlag)){
					viewHolder.btnPraise.setText("点赞");
					//moment.savePraise();
				}
				if(1==panelState){
					panelState = PANEL_STATE_GONE;
					switchPanelState(viewHolder.liearLayoutIgnore,viewHolder.btnComment,viewHolder.btnPraise);
				}else{
					panelState = PANEL_STATE_VISIABLE;
					switchPanelState(viewHolder.liearLayoutIgnore,viewHolder.btnComment,viewHolder.btnPraise);
				}
				
				//MomentAdapter.this.notifyDataSetChanged();
			}
        });
        
        viewHolder.btnPraise.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(viewHolder.btnPraise.getText().equals("点赞")){
					moment.savePraise();
					for(Praise p: moment.getNewPraise()){
						System.out.println(p.getU_name());
					}
					viewHolder.btnPraise.setText("取消");
				}
				else{
					moment.withdrawPraise();
					for(Praise p: moment.getPraiseList()){
						System.out.println(p.getU_name());
					}
					for(Praise p: moment.getNewPraise()){
						System.out.println(p.getU_name());
					}
					viewHolder.btnPraise.setText("点赞");
				} 
				MomentAdapter.this.notifyDataSetChanged();
			}
        	
        });
        
        viewHolder.btnComment.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//显示评论的对话框
				MyCustomDialog dialog = new MyCustomDialog(mContext,R.style.add_dialog,"评论"+moment.getUsername()+"的说说",new MyCustomDialog.OnCustomDialogListener() {
                    //点击对话框'提交'以后
                    public void back(String content) {
                    	//先隐藏再提交评论
                    	panelState = PANEL_STATE_GONE;
    					switchPanelState(viewHolder.liearLayoutIgnore,viewHolder.btnComment,viewHolder.btnPraise);
                    	//submitComment(moment.getId(),,moment.getUser_id(),bean.getUname(),content);//提交评论
    					moment.saveComment(content);
    					Toast.makeText(mContext, "提交评论", 0).show();
    					MomentAdapter.this.notifyDataSetChanged();
                    }
				});
			dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog  
            dialog.show();
			}
		});
        
        
        viewHolder.layoutPraise.removeAllViews();
      	viewHolder.view.setVisibility(View.GONE);
      	viewHolder.layout01.setVisibility(View.GONE);
        if(moment.praiseList.size()>0){
        	viewHolder.layout01.setVisibility(View.VISIBLE);
			viewHolder.layoutPraise.setVisibility(View.VISIBLE);
			
			LinearLayout ll=new LinearLayout(mContext);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.layout(3, 3, 3, 3);
					
			ImageView i1=new ImageView(mContext);
			i1.setBackgroundResource(R.drawable.micro_praise_button);
			i1.setLayoutParams(new LayoutParams(20,18));
			TextView t2=new TextView(mContext);
			t2.setTextColor(0xff2C78B8);
			t2.setTextSize(11);
			ll.addView(i1);
			
			StringBuffer uName=new StringBuffer();
			uName.append(" ");
			for(Praise p:moment.praiseList){
				if(null!=p){
					uName.append(p.getU_name()+" ,");
				}
			}
			uName.deleteCharAt(uName.length()-1);
			t2.setText(uName);
			ll.addView(t2);
			viewHolder.layoutPraise.addView(ll);
			
        }
      	
        //显示评论
      	viewHolder.layout.removeAllViews();
      	if(moment.commentList.size()>0){
      		viewHolder.layout01.setVisibility(View.VISIBLE);
			viewHolder.layout.setVisibility(View.VISIBLE);
			for(Comment c : moment.getCommentList()){
				LinearLayout ll=new LinearLayout(mContext);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				ll.layout(3, 3, 3, 3);
				TextView t1=new TextView(mContext);
				TextView t2=new TextView(mContext);
				t1.setText(" "+c.getU_name()+":");
				t1.setTextColor(0xff2C78B8);
				t1.setTextSize(13);
				t2.setTextSize(13);
				t2.setText(c.getComment_content());
				ll.addView(t1);
				ll.addView(t2);
				viewHolder.layout.addView(ll);
			}
			
      	}
      	
        return view;
	}
	
	class ViewHolder{
		TextView username;
		TextView context;
		TextView time;
		Button btnIgnore;
		LinearLayout liearLayoutIgnore;
		Button btnPraise;
		Button btnComment;
		LinearLayout layout01;
		LinearLayout layoutPraise;
		TextView text;
		LinearLayout layout;
		TextView view;
		
		public ViewHolder(){}
		
		public ViewHolder(View v){
			username=(TextView)v.findViewById(R.id.name);
			context=(TextView)v.findViewById(R.id.content);
			time=(TextView)v.findViewById(R.id.time);
			btnIgnore=(Button)v.findViewById(R.id.btnIgnore);
			btnPraise=(Button)v.findViewById(R.id.btnPraise);
			btnComment=(Button)v.findViewById(R.id.btnComment);
			liearLayoutIgnore=(LinearLayout)v.findViewById(R.id.liearLayoutIgnore);
			layout01=(LinearLayout)v.findViewById(R.id.layout01);
			layout=(LinearLayout)v.findViewById(R.id.layout);
			view=(TextView)v.findViewById(R.id.view);
			text=(TextView)v.findViewById(R.id.text);
			layoutPraise=(LinearLayout)v.findViewById(R.id.layoutParise);
			
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return moments==null?0:moments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return moments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ((Moment)getItem(position)).getId();
	}

}
