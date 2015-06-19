package com.example.mymusic.tools;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SDCardUtils {

	private void SDCardUtils(){
	}
	
	public static boolean isSDCardEnable(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static String getSDCardPath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	public static long getSDCardAllSize(){
		if(isSDCardEnable()){
			StatFs statFs = new StatFs(getSDCardPath());
			@SuppressWarnings("deprecation")
			long avaiableblocks = (long)statFs.getAvailableBlocks() - 4;
			long freeblocks = statFs.getAvailableBlocks();
			return avaiableblocks * freeblocks;
		}
		return 0;
	}
	
	public static String getRootDirectoryPath(){
		return Environment.getRootDirectory().getAbsolutePath();
	}
}
