package com.example.mymusic;

import com.example.mymusic.service.BackGroundService;
import com.example.mymusic.service.BackGroundService.PlayAndStopMusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class BaseActivity extends FragmentActivity {
	private FrameLayout mContentContainer;
	public View mMusicPlayer;
	public Context mContext;
	public ImageButton mPlayBtn;
	public ImageButton mPlayNextBtn;
	public TextView mSongName;
	public TextView mSinger;
	public BackGroundService.PlayAndStopMusic playAndStopMusicBinder;
	public boolean isChecked = false;
	
	public ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			playAndStopMusicBinder = (PlayAndStopMusic) service;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent service = new Intent(this, BackGroundService.class);
		bindService(service, connection, BIND_AUTO_CREATE);
		mContext = getApplicationContext();
		ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
		mContentContainer = (FrameLayout) ((ViewGroup)mDecorView.getChildAt(0)).getChildAt(1);
		mMusicPlayer = LayoutInflater.from(mContext).inflate(R.layout.custom_medioplayer_bar, null);
		mPlayBtn = (ImageButton) mMusicPlayer.findViewById(R.id.local_miniplayer_play);
		mPlayNextBtn = (ImageButton) mMusicPlayer.findViewById(R.id.local_miniplayer_next);
		mSongName = (TextView) mMusicPlayer.findViewById(R.id.local_miniplayer_song);
		mSinger = (TextView) mMusicPlayer.findViewById(R.id.local_miniplayer_artist);
		initListener();
	}


	private void initListener() {
		mPlayBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(playAndStopMusicBinder.isDataNull()){
					return;
				}
				isChecked = !isChecked;
				if(isChecked){
					mPlayBtn.setImageResource(R.drawable.player_pause);
					playAndStopMusicBinder.playMusic();
				}else if(!isChecked){
					mPlayBtn.setImageResource(R.drawable.player_play);
					playAndStopMusicBinder.pauseMusic();
				}
			}
		});
		mPlayNextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(playAndStopMusicBinder.isDataNull()){
					return;
				}
				playAndStopMusicBinder.playNextMusic();
				mPlayBtn.setImageResource(R.drawable.player_pause);
				setChecked(true);
			}
		});
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.BOTTOM;
		mContentContainer.addView(mMusicPlayer, layoutParams);
	}

	@Override
	public void startActivity(Intent intent) {
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		super.startActivity(intent);
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unbindService(connection);
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
