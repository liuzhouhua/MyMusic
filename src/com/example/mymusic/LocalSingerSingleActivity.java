package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.LocalSingerSingleAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.model.Music;

import de.greenrobot.event.EventBus;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LocalSingerSingleActivity extends BaseActivity {
	
	private static final String TAG = "LocalSingerSingleActivity";
	
	private LinearLayout mBackLayout;
	private TextView mSingerName;
	private ListView mSingerMusicListView;
	private List<Music> mDataList;
	private List<String> mMusicUrl = new ArrayList<String>();
	private MusicDBHelper dbHelper;
	private LocalSingerSingleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_singer_single);
		String singer = getIntent().getStringExtra("singerName");
		mBackLayout = (LinearLayout) findViewById(R.id.ry_back_singer_single);
		mSingerName = (TextView) findViewById(R.id.tv_singer_name);
		mSingerMusicListView = (ListView) findViewById(R.id.local_music_list);
		mSingerName.setText(singer);
		
		dbHelper = MusicDBHelper.getInstance(this);
		
		mDataList = getSingleSingerMusic(singer);
		adapter = new LocalSingerSingleAdapter(this, mDataList);
		mSingerMusicListView.setAdapter(adapter);
		
		initEvent();
		initData();
	}

	private void initData() {
		for(Music music : mDataList){
			mMusicUrl.add(music.getmMusicUrl());
		}
	}

	private void initEvent() {
		mBackLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mSingerMusicListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, " url :"+mDataList.get(position).getmMusicUrl());
				if(playAndStopMusicBinder!=null){
					playAndStopMusicBinder.initData(mDataList.get(position).getmMusicUrl(), mDataList, position);
					playAndStopMusicBinder.playMusic();
					mPlayBtn.setImageResource(R.drawable.player_pause);
					setChecked(true);
				}
			}
			
		});
	}

	private List<Music> getSingleSingerMusic(String singer) {
		List<Music> listRaw = new ArrayList<Music>();
		listRaw = dbHelper.queryForList(new RowMapper<Music>() {

			@Override
			public Music mapRow(Cursor cursor, int count) {
				Music music = new Music();
				music.setmMusicName(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_TITLE)));
				music.setmMusicSinger(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_SINGER)));
				music.setmMusicUrl(cursor.getString(cursor.getColumnIndex(DBConstant.ARTIST_LOCAL_PATH)));
				return music;
			}
			
		}, "select * from "+DBConstant.TABLE_ARTIST+" where "+DBConstant.ARTIST_LOCAL_SINGER+"=? ", new String[]{singer});
		
		return listRaw;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

}
