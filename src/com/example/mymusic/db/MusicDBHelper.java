package com.example.mymusic.db;

import java.io.File;

import com.example.mymusic.constant.DBConstant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class MusicDBHelper extends SQLiteOpenHelper{
	
	public MusicDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
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
		
//		db.execSQL("CREATE¡¡TABLE IF NOT EXISTS " + DBConstant.TABLE_MYFAVOE
//				+ " ("+DBConstant.FAVORITES_ID 
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.FAVORITES_LOCAL_ID
//				+ " INTEGER UNIQUE NOT NULL);");
//		
//		db.execSQL("CREATE¡¡TABLE IF NOT EXISTS " + DBConstant.TABLE_ARTIST
//				+ " ("+DBConstant.ARTIST_ID
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ARTIST_LOCAL_ARTIST
//				+ " TEXT UNIQUE NOT NULL);");
//		
//		db.execSQL("CREATE¡¡TABLE IF NOT EXISTS " + DBConstant.TABLE_ALBUM
//				+ " ("+DBConstant.ALBUM_ID
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ALUBM_LOCAL_ALBUM
//				+ " TEXT UNIQUE NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
