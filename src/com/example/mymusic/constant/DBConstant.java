package com.example.mymusic.constant;

import java.io.File;

import com.example.mymusic.db.SDBHelper;

public class DBConstant {
	//���ݿ�����
	public static final String DB_NAME = SDBHelper.DB_DIR + File.separator + "SmallBoallsDataBase";
	//���ݿ�汾
	public static final int DB_VERSION = 2;
	//���ݿ����
	public static final String TABLE_LOCALMUSIC = "localmusic";
	public static final String TABLE_MYFAVOE = "myfavor";
	public static final String TABLE_ARTIST = "artist";
	public static final String TABLE_ALBUM = "album";
	//���Ӧ����
	public static final String LOCAL_ID = "_id";
	public static final String LOCAL_NAME = "name";
	public static final String LOCAL_TITLE = "title";
	public static final String LOCAL_ARTIST = "artist";
	public static final String LOCAL_ALBUM = "album";
	public static final String LOCAL_PATH = "path";
	public static final String LOCAL_DURATION = "duration";
	public static final String LOCAL_FILE_SIZE = "file_size";
	public static final String LOCAL_LRC_TITLE = "lrc_title";
	public static final String LOCAL_LRC_PATH = "lrc_path";
	public static final String LOCAL_ALBUM_IMG_TITLE = "album_img_title";
	public static final String LOCAL_ALBUM_IMG_PATH = "album_img_path";
	
	public static final String ARTIST_ID = "_id";
	public static final String ARTIST_LOCAL_SINGER = "local_singer";
	public static final String ARTIST_LOCAL_TITLE = "local_title";
	public static final String ARTIST_LOCAL_PATH = "local_path";
	public static final String ARTIST_LOCAL_PINYIN = "local_pinyin";
	public static final String ARTIST_LOCAL_FIRSTLETTER = "local_firstletter";
	
	public static final String ALBUM_ID = "_id";
	public static final String ALUBM_LOCAL_SINGER = "local_singer";
	public static final String ALUBM_LOCAL_TITLE = "local_title";
	public static final String ALUBM_LOCAL_PATH = "local_path";
	public static final String ALUBM_LOCAL_ALBUMNAME = "local_albumname";
	public static final String ALUBM_LOCAL_PINYIN = "local_pinyin";
	public static final String ALUBM_LOCAL_FIRSTLETTER = "local_firstletter";
	
	public static final String FAVORITES_ID = "_id";
	public static final String FAVORITES_LOCAL_TITLE = "local_favorite_title";
	public static final String FAVORITES_LOCAL_SINGER = "local_favorite_singer";
	public static final String FAVORITES_LOCAL_PATH = "local_favorite_path";
}
