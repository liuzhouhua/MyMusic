package com.example.mymusic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class LocalMusicActivity extends FragmentActivity implements OnClickListener{
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localmusicactivity);
	}
	
	@Override
	public void onClick(View v) {
		
	}

}
