package com.example.mymusic.event;

import com.example.mymusic.model.Music;

public class RefreshPlayerViewEvent {
	
	private Music music;
	
	public RefreshPlayerViewEvent(Music music) {
		this.music = music;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

}
