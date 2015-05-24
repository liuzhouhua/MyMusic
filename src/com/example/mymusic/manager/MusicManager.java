package com.example.mymusic.manager;

import android.content.Context;

public class MusicManager {
	
	private int local_music_count = 0;
	private int myfavor_music_count = 0;
	private int download_music_count = 0;
	
	public MusicManager(Context context) {
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
}
