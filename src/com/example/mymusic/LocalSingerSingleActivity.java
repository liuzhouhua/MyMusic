package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.LocalSingerSingleAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.model.Music;
import com.example.mymusic.service.BackGroundService;
import com.example.mymusic.service.BackGroundService.PlayAndStopMusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LocalSingerSingleActivity extends Activity {
	
	private static final String TAG = "LocalSingerSingleActivity";
	
	private LinearLayout mBackLayout;
	private TextView mSingerName;
	private ListView mSingerMusicListView;
	private List<Music> mDataList;
	private MusicDBHelper dbHelper;
	private LocalSingerSingleAdapter adapter;
	private BackGroundService.PlayAndStopMusic playAndStopMusicBinder;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			playAndStopMusicBinder = (PlayAndStopMusic) service;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent service = new Intent(this, BackGroundService.class);
		bindService(service, connection, BIND_AUTO_CREATE);
		setContentView(R.layout.local_singer_single_activity);
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
					playAndStopMusicBinder.playMusic(mDataList.get(position).getmMusicUrl());
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
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
