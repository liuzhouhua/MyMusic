package com.example.mymusic.layout;

import com.example.mymusic.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MusicPlayer extends LinearLayout {

	public MusicPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.medioplayer_bar, this);
	}

}
