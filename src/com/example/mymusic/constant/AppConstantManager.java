package com.example.mymusic.constant;

import android.database.sqlite.SQLiteDatabase;

public class AppConstantManager {

	public SQLiteDatabase sb;
	
	public AppConstantManager(){
		
	}

	public SQLiteDatabase getSb() {
		return sb;
	}

	public void setSb(SQLiteDatabase sb) {
		this.sb = sb;
	}
	
}
