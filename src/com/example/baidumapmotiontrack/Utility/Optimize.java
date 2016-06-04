package com.example.baidumapmotiontrack.Utility;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;

public class Optimize {

	public static List<LatLng> pointsList(List<LatLng> list){
		List<LatLng> result=new ArrayList<LatLng>();
		result.add(list.get(0));
		for(int i=0;i<list.size()-1;i++){
			double t=(list.get(i).latitude+list.get(i+1).latitude)/2;
			double g=(list.get(i).longitude+list.get(i+1).longitude)/2;
			LatLng ll=new LatLng(t, g);
			result.add(ll);
		}
		result.add(list.get(list.size()-1));
		return result;
	}
}
