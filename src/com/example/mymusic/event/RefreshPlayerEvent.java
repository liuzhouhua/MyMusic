package com.example.mymusic.event;

import java.util.ArrayList;
import java.util.List;

public class RefreshPlayerEvent {

	private String currentUri="";
	private List<String> mMusicUri = new ArrayList<String>();
	private int position;
	
	public RefreshPlayerEvent(String currentUri,List<String> mMusicUri,int position) {
		this.currentUri = currentUri;
		this.mMusicUri = mMusicUri;
		this.position = position;
	}
	public String getCurrentUri() {
		return currentUri;
	}
	public void setCurrentUri(String currentUri) {
		this.currentUri = currentUri;
	}
	public List<String> getmMusicUri() {
		return mMusicUri;
	}
	public void setmMusicUri(List<String> mMusicUri) {
		this.mMusicUri = mMusicUri;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
