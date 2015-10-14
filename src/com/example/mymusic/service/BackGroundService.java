package com.example.mymusic.service;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackGroundService extends Service {
	private static final String TAG  = "BackGroundService";
	
	private PlayAndStopMusic mBinder = new PlayAndStopMusic();
	private MediaPlayer mPlayer= new MediaPlayer();

	public BackGroundService() {
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
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
