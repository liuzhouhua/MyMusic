package com.example.mymusic.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;

public class MusicManager {
	
	private int local_music_count = 0;
	private int myfavor_music_count = 0;
	private int download_music_count = 0;
	private static MusicManager instance = null;
	private Context mContext;
	private MusicDBHelper mDBHelper = null;
	private List<String> mMusicUri = new ArrayList<String>();
	
	public MusicManager(Context context) {
		this.mContext = context;
		mDBHelper = MusicDBHelper.getInstance(context);
	}

	public static MusicManager getInstance(Context context){
		if(instance==null){
			instance = new MusicManager(context);
		}
		return instance;
	}
	
	public int getLocal_music_count() {
		return local_music_count;
	}

	public void setLocal_music_count(int local_music_count) {
		this.local_music_count = local_music_count;
	}

	public int getMyfavor_music_count() {
		return myfavor_music_count;
	}

	public void setMyfavor_music_count(int myfavor_music_count) {
		this.myfavor_music_count = myfavor_music_count;
	}

	public int getDownload_music_count() {
		return download_music_count;
	}

	public void setDownload_music_count(int download_music_count) {
		this.download_music_count = download_music_count;
	}
	
	public void ScanSDCardMusic(){
		String selection = MediaStore.Audio.Media.DURATION +">30000";
    	Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    			null, selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    	cursor.moveToFirst();
    	mMusicUri.clear();
    	ContentValues contentValues = new ContentValues();
    	do{
    		contentValues.put(DBConstant.LOCAL_TITLE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
    		contentValues.put(DBConstant.LOCAL_ALBUM, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
    		contentValues.put(DBConstant.LOCAL_ARTIST, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
//    		mMusicUri.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
    		contentValues.put(DBConstant.LOCAL_PATH, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
    		contentValues.put(DBConstant.LOCAL_DURATION, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
    		contentValues.put(DBConstant.LOCAL_FILE_SIZE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
    		contentValues.put(DBConstant.LOCAL_NAME, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
    		mDBHelper.insert(DBConstant.TABLE_LOCALMUSIC,contentValues);
    		contentValues.clear();
    	}while(cursor.moveToNext());
	}

	public List<String> getmMusicUri() {
		return mMusicUri;
	}

	
}
