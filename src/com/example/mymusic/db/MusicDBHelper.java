package com.example.mymusic.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.R;
import com.example.mymusic.constant.ApplicationContext;
import com.example.mymusic.constant.Constant;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.tools.SDCardUtils;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
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
	
	public synchronized static MusicDBHelper getInstance(Context context){
		if(instance==null){
			instance = new MusicDBHelper(context,DBConstant.DB_NAME , null, DBConstant.DB_VERSION);
		}
		return instance;
	}
	
	private static final String CREATE_LOCALMUSIC = "CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_LOCALMUSIC
				+ " ("+DBConstant.LOCAL_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "  + DBConstant.LOCAL_NAME
				+ " TEXT, "+ DBConstant.LOCAL_TITLE
				+ " TEXT, " + DBConstant.LOCAL_ARTIST + " TEXT, "
				+ DBConstant.LOCAL_ALBUM + " TEXT, " + DBConstant.LOCAL_PATH
				+ " TEXT UNIQUE NOT NULL, " + DBConstant.LOCAL_DURATION
				+ " LONG, " + DBConstant.LOCAL_FILE_SIZE
				+ " LONG, " + DBConstant.LOCAL_LRC_TITLE
				+ " TEXT, " + DBConstant.LOCAL_LRC_PATH + " TEXT, "
				+ DBConstant.LOCAL_ALBUM_IMG_TITLE + " TEXT, "
				+ DBConstant.LOCAL_ALBUM_IMG_PATH + " TEXT" +");";
	
	private static final String CREATE_MYFAVOE = "CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_MYFAVOE
			+ " ("+DBConstant.FAVORITES_ID 
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.FAVORITES_LOCAL_TITLE
			+ " TEXT, "+ DBConstant.FAVORITES_LOCAL_SINGER
			+ " TEXT, "+ DBConstant.FAVORITES_LOCAL_PATH + "TEXT UNIQUE NOT NULL);";
	
	private static final String CREATE_ARTIST = "CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_ARTIST
			+ " ("+DBConstant.ARTIST_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ARTIST_LOCAL_TITLE
			+ " TEXT, "+ DBConstant.ARTIST_LOCAL_SINGER
			+ " TEXT, "+ DBConstant.ARTIST_LOCAL_PATH
			+ " TEXT UNIQUE NOT NULL, "+ DBConstant.ARTIST_LOCAL_PINYIN
			+ " TEXT, "+ DBConstant.ARTIST_LOCAL_FIRSTLETTER
			+ " TEXT);";

	private static final String CREATE_ALBUM = "CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_ALBUM
			+ " ("+DBConstant.ALBUM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + DBConstant.ALUBM_LOCAL_ALBUMNAME
			+ " TEXT, " + DBConstant.ALUBM_LOCAL_TITLE
			+ " TEXT, " + DBConstant.ALUBM_LOCAL_SINGER
			+ " TEXT, " + DBConstant.ALUBM_LOCAL_PATH
			+ " TEXT UNIQUE NOT NULL, " + DBConstant.ALUBM_LOCAL_PINYIN
			+ " TEXT, " + DBConstant.ALUBM_LOCAL_FIRSTLETTER
			+ " TEXT);";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_LOCALMUSIC);
		
		db.execSQL(CREATE_MYFAVOE);
		
		db.execSQL(CREATE_ARTIST);
		
		db.execSQL(CREATE_ALBUM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch(oldVersion){
		case 1:
			db.execSQL(CREATE_MYFAVOE);
			db.execSQL(CREATE_ARTIST);
			db.execSQL(CREATE_ALBUM);
		default:
		}
	}
	
	public synchronized long insert(String tableName,ContentValues content){
		if(database==null){
			database = getDatabase();
		}
		return database.insert(tableName, null, content);
	}
	
	public synchronized long update(String tableName,ContentValues content,
			String whereClause, String[] whereArgs){
		if(database==null){
			database = getDatabase();
		}
		return database.update(tableName, content, whereClause, whereArgs);
	}
	
	public  boolean isDataExitsByPath(String tableName,String field,String uname){
		return isExistsByField(tableName,field, uname);
	}
	
	//判断是否存在
	public synchronized Boolean isExistsByField(String table, String field, String value) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ").append(table).append(" WHERE ")
				.append(field).append(" =?");
		try {
			return isExistsBySQL(sql.toString(), new String[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public synchronized Boolean isExistsBySQL(String sql, String[] selectionArgs) {
		SQLiteDatabase dataBase = null;

		Cursor cursor = null;
		try {
			dataBase = getReadableDatabase();
			cursor = dataBase.rawQuery(sql, selectionArgs);
			if (cursor.moveToFirst()) {
				boolean isExist = cursor.getInt(0) > 0;
				cursor.close();
				return isExist;
			} else {
				cursor.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		return false;
	}
	
	//查询音乐数量
	public synchronized int queryLocalMusicCount(){
		if(database==null){
			database = getDatabase();
		}
		if(database!=null){
			return database.query(DBConstant.TABLE_LOCALMUSIC, null, null, null, null, null, null).getCount();
		}
		return 0;
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public synchronized SQLiteDatabase getDatabase(){
		if(database==null){
			try{
				database = getWritableDatabase();
			}catch(SQLiteFullException e){
				Log.e(TAG, "insert message encounter exception,in SQLiteFullException branch:"+e.getMessage());
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getmContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_your_msg_can_not_display), Toast.LENGTH_SHORT).show();
				}
				return null;
			}catch(SQLiteCantOpenDatabaseException e){
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getmContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_app_cant_use_normal), Toast.LENGTH_SHORT).show();
				}
				return null;
			}catch(Exception e){
				e.printStackTrace();
				Context context = ApplicationContext.getInstance().getmContext();
				String SDPath = SDCardUtils.getSDCardPath();
				File file = new File(SDPath);
				if(SDPath==null||file==null||file.getUsableSpace()<Constant.LOW_CAPACITY_THRESHOLD){
					Toast.makeText(context, context.getString(R.string.sdcard_full_app_cant_use_normal), Toast.LENGTH_SHORT).show();
				}
				return null;
			}
		}
		return database;
	}
	
	public interface RowMapper<T> {
		public T mapRow(Cursor cursor, int count);
	}
	
	public synchronized <T> T queryForObject(RowMapper<T> rowMapper, String sql,
			String[] args){
		SQLiteDatabase dataBase = null;

		Cursor cursor = null;
		T object = null;
		try {
			dataBase = getReadableDatabase();
			cursor = dataBase.rawQuery(sql, args);
			if (cursor.moveToFirst()) {
				object = rowMapper.mapRow(cursor, cursor.getCount());
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		return object;
	}
	
	public synchronized <T> List<T> queryForList(RowMapper<T> rowMapper, String sql,
			String[] args){
		SQLiteDatabase dataBase = null;

		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = getReadableDatabase();
			cursor = dataBase.rawQuery(sql, args);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				if(rowMapper.mapRow(cursor, cursor.getCount())!=null){
					list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
				}
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		return list;
	}
}
