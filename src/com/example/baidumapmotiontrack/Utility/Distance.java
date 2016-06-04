package com.example.baidumapmotiontrack.Utility;

import java.util.List;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;

public class Distance {
	private final static double EARTH_RADIUS = 6378.137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// 计算两个经纬点之间的距离,单位公里
	public static double distance(LatLng p1, LatLng p2) {
		if(p1==null||p2==null) return 0;
		double radLat1 = rad(p1.latitude);
		double radLat2 = rad(p2.latitude);
		double a = radLat1 - radLat2;
		double b = rad(p1.longitude) - rad(p2.longitude);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 10000;
		//返回米
		return s*1000;
	}

	public static double distance(double lat1,double lng1,double lat2,double lng2){
		double radLat1=rad(lat1);
		double radLat2=rad(lat2);
		double a=radLat1-radLat2;
		double b=rad(lng1)-rad(lng2);
		
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 10000;
		//返回米
		return s*1000;
	}
	
	public static double distance2(List<LatLng> points) {
		LatLng last = null;
		double s = 0;
		Log.d("MainActivity", "diatance2");
		for (LatLng p : points) {
			Log.d("MainActivity", "circle");
			if (p.equals(points.get(0))) {
				last = p;
			} else {
				s += distance(last, p);
				last = p;
			}
		}
		Log.d("MainActivity", "s="+s);
		//返回米
		return s;
	}
}
