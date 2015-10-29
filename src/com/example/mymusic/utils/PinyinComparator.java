package com.example.mymusic.utils;

import java.util.Comparator;

import com.example.mymusic.model.Singer;

public class PinyinComparator implements Comparator<Singer>{
	private int type = 0;

	public PinyinComparator(int type){
		this.type = type;
	}

	@Override
	public int compare(Singer lhs, Singer rhs) {
		if(type==0){
			//歌手拼音
			return lhs.getLetterForSinger().compareTo(rhs.getLetterForSinger());
		}else{
			//专辑拼音
			return 0;
		}
	}

}
