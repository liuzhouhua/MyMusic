package com.example.mymusic;


import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.event.RefreshLocalMusicFragmentEvent;

import de.greenrobot.event.EventBus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MeFragment extends Fragment implements OnClickListener{

	private ImageView avater;
	private TextView local_music_count,myfavor_music_count,download_music_count;
	private ImageView add_list,edit_list;
	private LinearLayout local_layout,myfavor_layout,download_layout;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_me, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		avater = (ImageView) getActivity().findViewById(R.id.useravater);
		local_music_count = (TextView) getActivity().findViewById(R.id.display_localmusic_count);
		myfavor_music_count = (TextView) getActivity().findViewById(R.id.myfavor_music_count);
		download_music_count = (TextView) getActivity().findViewById(R.id.download_music_count);
		
		local_layout = (LinearLayout) getActivity().findViewById(R.id.localmusic);
		myfavor_layout = (LinearLayout) getActivity().findViewById(R.id.myfavor);
		download_layout = (LinearLayout) getActivity().findViewById(R.id.downloadmusic);
		
		add_list = (ImageView) getActivity().findViewById(R.id.add_list_button);
		edit_list = (ImageView) getActivity().findViewById(R.id.edit_list_button);
		
		local_music_count.setText(""+ MusicDBHelper.getInstance(getActivity()).queryLocalMusicCount()
				+getActivity().getResources().getString(R.string.unit));
		
		EventBus.getDefault().register(this);
		
		avater.setOnClickListener(this);
		local_layout.setOnClickListener(this);
		myfavor_layout.setOnClickListener(this);
		download_layout.setOnClickListener(this);
		add_list.setOnClickListener(this);
		edit_list.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.useravater:
			break;
		case R.id.localmusic:
			Intent intent = new Intent(this.getActivity(),LocalMusicActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			break;
		case R.id.myfavor:
			break;
		case R.id.downloadmusic:
			break;
		case R.id.add_list_button:
			break;
		case R.id.edit_list_button:
			break;
		default:
			break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	public void onEventMainThread(RefreshLocalMusicFragmentEvent event){
		local_music_count.setText(""+ MusicDBHelper.getInstance(getActivity()).queryLocalMusicCount()
				+getActivity().getResources().getString(R.string.unit));
	}
}
