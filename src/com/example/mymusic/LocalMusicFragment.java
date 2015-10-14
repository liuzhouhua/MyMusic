package com.example.mymusic;

import com.example.mymusic.manager.MusicManager;
import com.example.mymusic.service.BackGroundService;
import com.example.mymusic.service.BackGroundService.PlayAndStopMusic;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class LocalMusicFragment extends Fragment{
	
	private static final String TAG = "LocalMusicFragment";

	public ListView mLocalMusiclist;
	public SimpleCursorAdapter adapter;
	private Cursor mCursor;
	private BackGroundService.PlayAndStopMusic playAndStopMusicBinder;
	
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
		mCursor = MusicManager.getInstance(getActivity()).getCursorForscanSDCardMusic();
		mLocalMusiclist = (ListView) getActivity().findViewById(R.id.local_music_list);
		String[] From = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST};
    	int[] To = {R.id.item_title,R.id.item_artist};
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.music_list_item, mCursor, From, To,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mLocalMusiclist.setAdapter(adapter);
		mLocalMusiclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "position :"+position+" id :"+id);
				String itemUri = MusicManager.getInstance(getActivity()).getmMusicUri().get(position);
				Log.d(TAG, "uri :"+itemUri);
				if(playAndStopMusicBinder!=null){
					playAndStopMusicBinder.playMusic(itemUri);
				}
			}
			
		});
	}
	

}
