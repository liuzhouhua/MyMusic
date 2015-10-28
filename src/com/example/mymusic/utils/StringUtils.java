package com.example.mymusic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class StringUtils {
	
	private static String TAG = "StringUtils";

	//判断是否是字母
	public static boolean isLetters(String str){
		Log.d(TAG, "str :"+str);
		String regEx = "[a-zA-Z]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		Log.d(TAG, "找到 ："+m.find());
		return m.find();
	}
	//判断是否是数字
	public boolean isNumbers(String str){
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
