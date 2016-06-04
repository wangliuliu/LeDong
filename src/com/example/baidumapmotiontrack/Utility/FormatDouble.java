package com.example.baidumapmotiontrack.Utility;

public class FormatDouble {
	public static double format(Double d) {
		int s = (int) (d * 100);
		double dd = s * 1.0 / 100;
		return dd;
	}
}
