package com.example.mymusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.mymusic.adapter.LocalSingerAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.event.RefreshLocalSingerFragmentEvent;
import com.example.mymusic.model.Music;
import com.example.mymusic.model.Singer;
import com.example.mymusic.utils.PinyinComparator;
import com.example.mymusic.view.SideBar;
import com.example.mymusic.view.SideBar.OnTouchingLetterChangedListener;

import de.greenrobot.event.EventBus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LocalSingerFragment extends Fragment{
	
	private static final String TAG = "LocalSingerFragment";
	
	private ListView mSingerList;
	private TextView mLetterText;
	private SideBar mSideBar;
	private List<Singer> mMusicList = new ArrayList<Singer>();
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
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position!=-1){
					mSingerList.setSelection(position);
				}
			}
		});
		dbHelper = MusicDBHelper.getInstance(getActivity());
		mMusicList = scanDBForList();
		comparator = new PinyinComparator(0);
		Collections.sort(mMusicList, comparator);
		adapter = new LocalSingerAdapter(getActivity(), mMusicList);
		mSingerList.setAdapter(adapter);
		mSingerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "position :"+position+" singer :"+mMusicList.get(position).getSinger());
				Intent intent = new Intent(getActivity(), LocalSingerSingleActivity.class);
				intent.putExtra("singerName", mMusicList.get(position).getSinger());
				startActivity(intent);
			}
			
		});
		EventBus.getDefault().register(this);
	}
	
	public List<Singer> scanDBForList(){
		List<Singer> listRaw = new ArrayList<Singer>();
		listRaw = dbHelper.queryForList(new RowMapper<Singer>() {

			@Override
			public Singer mapRow(Cursor cursor, int count) {
				Singer singer = new Singer();
				singer.setSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_SINGER)));
				singer.setLetterForSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_FIRSTLETTER)).trim().toUpperCase());
				return singer;
			}
			
		}, "select * from "+DBConstant.TABLE_ARTIST, null);
		//获取相同的次数
		Set<String> set = new HashSet<String>();
		List<String> singer = new ArrayList<String>();
		for(Singer singer_t : listRaw){
			singer.add(singer_t.getSinger());
			set.add(singer_t.getSinger());
		}
        for (String temp : set) {  
        	for(Singer singer2 : listRaw){
        		if(singer2.getSinger().equals(temp)){
        			singer2.setCount(Collections.frequency(singer, temp));
        		}
        	}
        } 
        

        //去重
		for (int i = 0; i < listRaw.size() - 1; i++) {
			for (int j = listRaw.size() - 1; j > i; j--) {
				if (listRaw.get(j).getSinger().trim().equals(listRaw.get(i).getSinger().trim())) {
					listRaw.remove(j);
				}
			}
		}
		return listRaw;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(RefreshLocalSingerFragmentEvent event){
		mMusicList = scanDBForList();
		Collections.sort(mMusicList, comparator);
		adapter = new LocalSingerAdapter(getActivity(), mMusicList);
		mSingerList.setAdapter(adapter);
	}
}
