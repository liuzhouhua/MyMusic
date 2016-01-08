package com.example.mymusic.event;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.model.Music;

public class RefreshPlayerEvent {

	private String currentUri="";
	private List<Music> mMusics = new ArrayList<Music>();
	private int position;
	
	public RefreshPlayerEvent(String currentUri,List<Music> mMusics,int position) {
		this.currentUri = currentUri;
		this.mMusics = mMusics;
		this.position = position;
	}
	public String getCurrentUri() {
		return currentUri;
	}
	public void setCurrentUri(String currentUri) {
		this.currentUri = currentUri;
	}
	public List<Music> getmMusics() {
		return mMusics;
	}
	public void setmMusics(List<Music> mMusics) {
		this.mMusics = mMusics;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
