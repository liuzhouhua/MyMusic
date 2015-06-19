package com.example.mymusic.db;

import java.io.File;

import com.example.mymusic.tools.SDCardUtils;

public class SDBHelper {

	public static String DB_DIR = SDCardUtils.getSDCardPath() + "MyMusic" + File.separator + "databases";
	static{
			while(!SDCardUtils.isSDCardEnable()){
				try{
				Thread.sleep(500);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
			File dbFloder = new File(DB_DIR);
			if(!dbFloder.exists()){
				dbFloder.mkdirs();
			}
	}
	
}
