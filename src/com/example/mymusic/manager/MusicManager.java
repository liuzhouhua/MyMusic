package com.example.mymusic.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusic.constant.Constant;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.model.Music;
import com.example.mymusic.utils.CharacterParser;

public class MusicManager {
	
	private int local_music_count = 0;
	private int myfavor_music_count = 0;
	private int download_music_count = 0;
	private static MusicManager instance = null;
	private Context mContext;
	private MusicDBHelper mDBHelper = null;
	
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
	
	public int ScanSDCardMusic(){
		String selection = MediaStore.Audio.Media.DURATION +">30000";
    	Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    			null, selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    	cursor.moveToFirst();
    	ContentValues contentValues = new ContentValues();
    	ContentValues contentValues2 = new ContentValues();
    	ContentValues contentValues3 = new ContentValues();
    	String path = "";
    	do{
    		contentValues.put(DBConstant.LOCAL_TITLE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
    		contentValues.put(DBConstant.LOCAL_ALBUM, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
    		contentValues.put(DBConstant.LOCAL_ARTIST, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
    		path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
    		contentValues.put(DBConstant.LOCAL_DURATION, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
    		contentValues.put(DBConstant.LOCAL_FILE_SIZE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
    		contentValues.put(DBConstant.LOCAL_NAME, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
    		if(mDBHelper.isDataExitsByPath(DBConstant.TABLE_LOCALMUSIC, DBConstant.LOCAL_PATH, path)){
    			mDBHelper.update(DBConstant.TABLE_LOCALMUSIC, contentValues, DBConstant.LOCAL_PATH+"=?", new String[]{path});
    		}else{
    			contentValues.put(DBConstant.LOCAL_PATH, path);
    			mDBHelper.insert(DBConstant.TABLE_LOCALMUSIC,contentValues);
    		}
    		
    		contentValues2.put(DBConstant.ARTIST_LOCAL_TITLE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
    		contentValues2.put(DBConstant.ARTIST_LOCAL_SINGER, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
    		contentValues2.put(DBConstant.ARTIST_LOCAL_PINYIN, CharacterParser.getPingYin(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))));
    		contentValues2.put(DBConstant.ARTIST_LOCAL_FIRSTLETTER, CharacterParser.getPinYinFirstHeadChar(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))));
    		if(mDBHelper.isDataExitsByPath(DBConstant.TABLE_ARTIST, DBConstant.ARTIST_LOCAL_PATH, path)){
    			mDBHelper.update(DBConstant.TABLE_ARTIST, contentValues2, DBConstant.ARTIST_LOCAL_PATH+"=?", new String[]{path});
    		}else{
    			contentValues2.put(DBConstant.ARTIST_LOCAL_PATH, path);
    			mDBHelper.insert(DBConstant.TABLE_ARTIST,contentValues2);
    		}
    		
    		contentValues3.put(DBConstant.ALUBM_LOCAL_TITLE, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
    		contentValues3.put(DBConstant.ALUBM_LOCAL_SINGER, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
    		contentValues3.put(DBConstant.ALUBM_LOCAL_ALBUMNAME, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
    		path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
    		contentValues3.put(DBConstant.ALUBM_LOCAL_PINYIN, CharacterParser.getPingYin(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))));
    		contentValues3.put(DBConstant.ALUBM_LOCAL_FIRSTLETTER, CharacterParser.getPinYinFirstHeadChar(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))));
    		if(mDBHelper.isDataExitsByPath(DBConstant.TABLE_ALBUM, DBConstant.ALUBM_LOCAL_PATH, path)){
    			mDBHelper.update(DBConstant.TABLE_ALBUM, contentValues3, DBConstant.ALUBM_LOCAL_PATH+"=?", new String[]{path});
    		}else{
    			contentValues3.put(DBConstant.ALUBM_LOCAL_PATH, path);
    			mDBHelper.insert(DBConstant.TABLE_ALBUM, contentValues3);
    		}
    		
    		path = "";
    		contentValues.clear();
    		contentValues2.clear();
    		contentValues3.clear();
    	}while(cursor.moveToNext());
    	
    	return 0;
	}

	public  List<Music> getSingerMusicFromDB(String signer){
		return mDBHelper.queryForList(new RowMapper<Music>() {
			
			public Music mapRow(Cursor cursor, int count){
				Music music = new Music();
				music.setmMusicName(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_TITLE)));
				music.setmMusicSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_SINGER)));
				music.setmMusicUrl(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_PATH)));
				music.setmPinYinofSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_PINYIN)));
				music.setmLetterofSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_FIRSTLETTER)));
				return music;
			}
			
		}, "select * from "+ DBConstant.TABLE_ARTIST +" where local_singer=?", new String[]{signer});
	}
}
