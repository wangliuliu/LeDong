package com.example.baidumapmotiontrack.Utility;

import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Utils {
	
	public static String toPinyin(String s){
		char[] t1=s.toCharArray();
		String[] t2=new String[5];//用于存放一个汉字的不同拼音
		
		HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//拼音由小写字母组成
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		String result="";
		
		try{
			for(int i=0;i<t1.length;i++){
				//判断能否为汉字字符
				if(Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")){
					t2=PinyinHelper.toHanyuPinyinStringArray(t1[i], format);
					result+=t2[0]+" ";
				}else{
					result+=Character.toString(t1[i]);
				}
			}
		}catch(BadHanyuPinyinOutputFormatCombination c){
			c.printStackTrace();
		}
		return result;
	}
	
	public static String formatAlpha(String str) {
		String pinyin=Utils.toPinyin(str);
		if (pinyin == null) {
			return "#";
		}
		// trim()方法去除字符串头尾空格
		if (pinyin.trim().length() == 0) {
			return "#";
		}

		char c = pinyin.trim().substring(0, 1).charAt(0);
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {// 如果首字母是a-z或A-Z的字母
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}
}
