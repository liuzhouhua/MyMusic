package com.example.mymusic.utils;

import java.util.Comparator;

import android.util.Log;

import com.example.mymusic.model.Music;

public class PinyinComparator implements Comparator<Music>{
	private int type = 0;

	public PinyinComparator(int type){
		this.type = type;
	}

	@Override
	public int compare(Music lhs, Music rhs) {
		if(type==0){
			//歌手拼音
			return lhs.getmLetterofSinger().compareTo(rhs.getmLetterofSinger());
		}else{
			//专辑拼音
			return lhs.getmLetterofAlbum().compareTo(rhs.getmLetterofAlbum());
		}
	}

}
