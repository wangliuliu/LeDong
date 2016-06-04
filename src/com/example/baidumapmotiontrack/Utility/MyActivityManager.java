package com.example.baidumapmotiontrack.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;

public class MyActivityManager {

	public static List<Activity> activityList;
	
	public static void addActivity(Activity activity){
		if(activityList==null){
			activityList=new ArrayList<Activity>();
		}
		activityList.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		activityList.remove(activity);
	}
	
	public static void removeAll(){
		for(Activity activity:activityList){
			activity.finish();
		}
		activityList.clear();
	}
}
