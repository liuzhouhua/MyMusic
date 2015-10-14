package com.example.mymusic.constant;

import android.content.Context;

public class ApplicationContext {
	
	private static ApplicationContext instance;
	private Context mContext;

	public ApplicationContext() {
	}
	
	public void init(Context context){
		this.mContext = context;
	}
	
	public static ApplicationContext getInstance(){
		if(instance==null){
			instance = new ApplicationContext();
		}
		return instance;
	}

	public Context getmContext() {
		return mContext;
	}

}
