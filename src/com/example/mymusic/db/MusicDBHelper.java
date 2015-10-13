package com.example.mymusic.db;

import java.io.File;

import com.example.mymusic.R;
import com.example.mymusic.constant.ApplicationContext;
import com.example.mymusic.constant.Constant;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.tools.SDCardUtils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class MusicDBHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "MusicDBHelper";
	private static MusicDBHelper instance = null;
	private SQLiteDatabase database = null;
	
	private MusicDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public synchronized static MusicDBHelper getInstance(Context context, String name, CursorFactory factory,
			int version){
		if(instance==null){
			instance = new MusicDBHelper(context,DBConstant.DB_NAME , null, DBConstant.DB_VERSION);
		}
		return instance;
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_LOCALMUSIC
				+ " ("+DBConstant.LOCAL_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "  + DBConstant.LOCAL_NAME
				+ " TEXT UNIQUE NOT NULL, "+ DBConstant.LOCAL_TITLE
				+ " TEXT UNIQUE NOT NULL, " + DBConstant.LOCAL_ARTIST + " TEXT, "
				+ DBConstant.LOCAL_ALBUM + " TEXT, " + DBConstant.LOCAL_PATH
				+ " TEXT NOT NULL, " + DBConstant.LOCAL_DURATION
				+ " LONG NOT NULL, " + DBConstant.LOCAL_FILE_SIZE
				+ " LONG NOT NULL, " + DBConstant.LOCAL_LRC_TITLE
				+ " TEXT, " + DBConstant.LOCAL_LRC_PATH + " TEXT, "
				+ DBConstant.LOCAL_ALBUM_IMG_TITLE + " TEXT, "
				+ DBConstant.LOCAL_ALBUM_IMG_PATH + " TEXT" +");");
		
		db.execSQL("CREATE°°TABLE IF NOT EXISTS " + DBConstant.TABLE_MYFAVOE
				+ " ("+DBConstant.FAVORITES_ID 
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.FAVORITES_LOCAL_ID
				+ " INTEGER UNIQUE NOT NULL);");
		
		db.execSQL("CREATE°°TABLE IF NOT EXISTS " + DBConstant.TABLE_ARTIST
				+ " ("+DBConstant.ARTIST_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ARTIST_LOCAL_ARTIST
				+ " TEXT UNIQUE NOT NULL);");
		
		db.execSQL("CREATE°°TABLE IF NOT EXISTS " + DBConstant.TABLE_ALBUM
				+ " ("+DBConstant.ALBUM_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ALUBM_LOCAL_ALBUM
				+ " TEXT UNIQUE NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	//≤Â»Î ˝æ›
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public synchronized long insert(String tableName,ContentValues content){
		if(database==null){
			try{
				database = getWritableDatabase();
			}catch(SQLiteFullException e){
				Log.e(TAG, "insert message encounter exception,in SQLiteFullException branch:"+e.getMessage());
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_your_msg_can_not_display), Toast.LENGTH_SHORT).show();
				}
				return -1;
			}catch(SQLiteCantOpenDatabaseException e){
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_app_cant_use_normal), Toast.LENGTH_SHORT).show();
				}
				return -1;
			}catch(Exception e){
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_app_cant_use_normal), Toast.LENGTH_SHORT).show();
				}
				return -1;
			}
		}
		return database.insert(tableName, null, content);
	}
}
