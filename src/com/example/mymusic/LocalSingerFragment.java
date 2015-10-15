package com.example.mymusic;

import com.example.mymusic.view.SideBar;
import com.example.mymusic.view.SideBar.OnTouchingLetterChangedListener;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LocalSingerFragment extends Fragment{
	
	private ListView mSingerList;
	private TextView mLetterText;
	private SideBar mSideBar;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.local_singer_fragment, null);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mSingerList = (ListView) getActivity().findViewById(R.id.local_singer_list);
		mLetterText = (TextView) getActivity().findViewById(R.id.local_singer_letter);
		mSideBar = (SideBar) getActivity().findViewById(R.id.local_singer_sidebar);
		mSideBar.setTextView(mLetterText);
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				
			}
		});
	}

}
