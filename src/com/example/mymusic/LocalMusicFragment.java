package com.example.mymusic;

import com.example.mymusic.manager.MusicManager;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class LocalMusicFragment extends Fragment implements OnClickListener{

	public ListView mLocalMusiclist;
	public SimpleCursorAdapter adapter;
	
	public ListView getmLocalMusiclist() {
		return mLocalMusiclist;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.local_music_fragment, null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLocalMusiclist = (ListView) getActivity().findViewById(R.id.local_music_list);
		String[] From = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST};
    	int[] To = {R.id.item_title,R.id.item_artist};
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.music_list_item, MusicManager.getInstance(getActivity()).getCursor(), From, To);
		mLocalMusiclist.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View arg0) {
		
	}

}
