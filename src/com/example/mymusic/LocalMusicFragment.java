package com.example.mymusic;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class LocalMusicFragment extends Fragment implements OnClickListener{

	public ListView mLocalMusiclist;
	
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
		mLocalMusiclist = (ListView) getActivity().findViewById(R.id.local_music_list);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
