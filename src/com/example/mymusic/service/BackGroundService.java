package com.example.mymusic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.event.RefreshPlayerViewEvent;
import com.example.mymusic.manager.MusicManager;
import com.example.mymusic.model.Music;

import de.greenrobot.event.EventBus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackGroundService extends Service {
	private static final String TAG  = "BackGroundService";
	
	private PlayAndStopMusic mBinder = new PlayAndStopMusic();
	private MediaPlayer mPlayer= new MediaPlayer();
	private List<Music> mPlayList = new ArrayList<Music>();
	private int currentPosition;
	private String currentUrl;
	private Context mContext;

	public BackGroundService() {
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();	
		mContext = getApplicationContext();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mBinder.playNextMusic();
			}
		});

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
		private boolean isPause = false;
		public void initData(String uri,List<Music> mList,int mPosition){
			currentUrl = uri;
			mPlayList = mList;
			currentPosition = mPosition;
		}
		public void playMusic(){
			if(mPlayer==null){
				mPlayer = new MediaPlayer();
			}
			if(isPause){
				mPlayer.start();
				MusicManager.getInstance(mContext).setPlaying(true);
				isPause = false;
				return;
			}
			if(currentUrl==null){
				return;
			}
			try {
				mPlayer.reset();
				mPlayer.setDataSource(currentUrl);
				mPlayer.prepare();
				mPlayer.start();
				EventBus.getDefault().post(new RefreshPlayerViewEvent(mPlayList.get(currentPosition)));
				MusicManager.getInstance(mContext).setPlaying(true);
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
			mPlayer.pause();
			MusicManager.getInstance(mContext).setPlaying(false);
			isPause  = true;
		}
		public void playNextMusic(){
			currentPosition = currentPosition +1;
			if(currentPosition<mPlayList.size()){
				currentUrl = mPlayList.get(currentPosition).getmMusicUrl();
				isPause = false;
				playMusic();
			}
		}
		public void playbeforeMusic(){
			currentPosition = currentPosition -1;
			if(currentPosition>=0){
				currentUrl = mPlayList.get(currentPosition).getmMusicUrl();
				isPause = false;
				playMusic();
			}
		}
		public void stopMusic(){
			isPause = false;
			mPlayer.stop();
			MusicManager.getInstance(mContext).setPlaying(false);
		}
		
		public boolean isDataNull(){
			return currentUrl==null;
		}
	}
}
