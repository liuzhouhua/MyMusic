package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.event.RefreshLocalMusicFragmentEvent;
import com.example.mymusic.service.BackGroundService;
import com.example.mymusic.service.BackGroundService.PlayAndStopMusic;

import de.greenrobot.event.EventBus;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class LocalMusicFragment extends Fragment{
	
	private static final String TAG = "LocalMusicFragment";

	public ListView mLocalMusiclist;
	private ImageView mImageView;
	public SimpleCursorAdapter adapter;
	private Cursor mCursor;
	private BackGroundService.PlayAndStopMusic playAndStopMusicBinder;
	private List<String> mMusicUri = new ArrayList<String>();
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected");
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected");
			playAndStopMusicBinder = (PlayAndStopMusic) service;
		}
	};
	
	public ListView getmLocalMusiclist() {
		return mLocalMusiclist;
	}
	
	public LocalMusicFragment(){
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.local_music_fragment, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Intent service = new Intent(getActivity(), BackGroundService.class);
		getActivity().bindService(service, connection, getActivity().BIND_AUTO_CREATE);
		mLocalMusiclist = (ListView) getActivity().findViewById(R.id.local_music_list);
		mImageView = (ImageView) getActivity().findViewById(R.id.no_music_image);
		if(MusicDBHelper.getInstance(getActivity()).queryLocalMusicCount()<=0){
			mLocalMusiclist.setVisibility(View.GONE);
			mImageView.setVisibility(View.VISIBLE);
		}else{
			mImageView.setVisibility(View.GONE);
			mLocalMusiclist.setVisibility(View.VISIBLE);
		}
			String selection = MediaStore.Audio.Media.DURATION +">30000";
			mCursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null, selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			String[] From = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST};
			int[] To = {R.id.item_title,R.id.item_artist};
			adapter = new SimpleCursorAdapter(getActivity(), R.layout.music_list_item, mCursor, From, To,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			mLocalMusiclist.setAdapter(adapter);
			
			while(mCursor.moveToNext()){
				mMusicUri.add(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
			}
			
			mLocalMusiclist.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.d(TAG, "position :"+position+" id :"+id);
					String itemUri = mMusicUri.get(position);
					Log.d(TAG, "itemUri :"+itemUri);
					if(playAndStopMusicBinder!=null){
						playAndStopMusicBinder.initData(itemUri, mMusicUri, position);
						playAndStopMusicBinder.playMusic();
					}
				}
				
			});
			
			EventBus.getDefault().register(this);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		getActivity().unbindService(connection);
	}
	
	public void onEventMainThread(RefreshLocalMusicFragmentEvent event){
		if(MusicDBHelper.getInstance(getActivity()).queryLocalMusicCount()<=0){
			mLocalMusiclist.setVisibility(View.GONE);
			mImageView.setVisibility(View.VISIBLE);
		}else{
			mImageView.setVisibility(View.GONE);
			mLocalMusiclist.setVisibility(View.VISIBLE);
		}
	}
	
}
