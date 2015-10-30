package com.example.mymusic.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.example.mymusic.MainActivity;
import com.example.mymusic.R;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BackGroundService extends Service {
	private static final String TAG  = "BackGroundService";
	
	private PlayAndStopMusic mBinder = new PlayAndStopMusic();
	private MediaPlayer mPlayer= new MediaPlayer();
	private WindowManager manager;
	private WindowManager.LayoutParams params;
	private View view;

	private ImageButton mPlayBtn,mPlayNextBtn;
	private TextView mSongName,mSinger;

	public BackGroundService() {
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		manager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();
		view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.medioplayer_bar,null);
		mPlayBtn = (ImageButton) view.findViewById(R.id.local_miniplayer_play);
		mPlayNextBtn = (ImageButton) view.findViewById(R.id.local_miniplayer_next);
		mSongName = (TextView) view.findViewById(R.id.local_miniplayer_song);
		mSinger = (TextView) view.findViewById(R.id.local_miniplayer_artist);
		
		createView();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void createView(){
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		params.gravity = Gravity.BOTTOM;
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.RGBA_8888;
		manager.addView(view, params);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public class PlayAndStopMusic extends Binder{
		public void playMusic(String uri){
			Log.d(TAG, "playMusic :"+uri);
			if(mPlayer==null){
				mPlayer = new MediaPlayer();
			}
			try {
				mPlayer.reset();
				mPlayer.setDataSource(uri);
				mPlayer.prepare();
				mPlayer.start();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void pauseMusic(){
			
		}
		public void playNextMusic(){
			
		}
		public void playbeforeMusic(){
			
		}
		public void stopMusic(){
			
		}
	}
}
