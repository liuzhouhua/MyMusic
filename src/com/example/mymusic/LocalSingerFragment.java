package com.example.mymusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.mymusic.adapter.LocalSingerAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.model.Music;
import com.example.mymusic.utils.PinyinComparator;
import com.example.mymusic.view.SideBar;
import com.example.mymusic.view.SideBar.OnTouchingLetterChangedListener;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LocalSingerFragment extends Fragment{
	
	private static final String TAG = "LocalSingerFragment";
	
	private ListView mSingerList;
	private TextView mLetterText;
	private SideBar mSideBar;
	private List<Music> mMusicList = new ArrayList<Music>();
	private MusicDBHelper dbHelper;
	private LocalSingerAdapter adapter;
	private PinyinComparator comparator;

	
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
		dbHelper = MusicDBHelper.getInstance(getActivity());
		mMusicList = dbHelper.queryForList(new RowMapper<Music>() {

			@Override
			public Music mapRow(Cursor cursor, int count) {
				Music music = new Music();
				music.setmMusicName(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_TITLE)));
				music.setmMusicSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_SINGER)));
				music.setmMusicUrl(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_PATH)));
				music.setmPinYinofSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_PINYIN)));
				music.setmLetterofSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_FIRSTLETTER)));
				return music;
			}
			
		}, "select * from "+DBConstant.TABLE_ARTIST, null);
		for(Music music:mMusicList){
			Log.d(TAG,"music title:"+music.getmMusicSinger()+
					" music singer:"+music.getmMusicSinger()+
					" music uri:"+music.getmMusicUrl()+
					" music pinyin:"+music.getmPinYinofSinger()+
					" music letter:"+music.getmLetterofSinger());
		}
		comparator = new PinyinComparator(0);
		
		Collections.sort(mMusicList, comparator);
		
		adapter = new LocalSingerAdapter(getActivity(), mMusicList);
		mSingerList.setAdapter(adapter);
		
	}

}
