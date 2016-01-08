package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.LocalSingerSingleAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.db.MusicDBHelper.RowMapper;
import com.example.mymusic.event.RefreshLocalMusicFragmentEvent;
import com.example.mymusic.event.RefreshPlayerEvent;
import com.example.mymusic.model.Music;
import com.example.mymusic.service.BackGroundService;
import de.greenrobot.event.EventBus;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

@SuppressLint("NewApi")
public class LocalMusicFragment extends Fragment{
	
	private static final String TAG = "LocalMusicFragment";

	public ListView mLocalMusiclist;
	private ImageView mImageView;
	private LocalSingerSingleAdapter adapter;
	private List<Music> mMusics = new ArrayList<Music>();
	private MusicDBHelper dbHelper;
	
	public ListView getmLocalMusiclist() {
		return mLocalMusiclist;
	}
	
	public LocalMusicFragment(){
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_local_music, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dbHelper = MusicDBHelper.getInstance(getActivity());
		mLocalMusiclist = (ListView) getActivity().findViewById(R.id.local_music_list);
		mImageView = (ImageView) getActivity().findViewById(R.id.no_music_image);
		if(MusicDBHelper.getInstance(getActivity()).queryLocalMusicCount()<=0){
			mLocalMusiclist.setVisibility(View.GONE);
			mImageView.setVisibility(View.VISIBLE);
		}else{
			mImageView.setVisibility(View.GONE);
			mLocalMusiclist.setVisibility(View.VISIBLE);
		}

		mMusics = scanDBForMusicList();
		
		adapter = new LocalSingerSingleAdapter(getActivity(), mMusics);
		
		mLocalMusiclist.setAdapter(adapter);
		
		mLocalMusiclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "position :" + position + " id :" + id);
				String itemUri = mMusics.get(position).getmMusicUrl();
				Log.d(TAG, "itemUri :" + itemUri);
				RefreshPlayerEvent event = new RefreshPlayerEvent(itemUri,
						mMusics, position);
				EventBus.getDefault().post(event);
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
	}
	
	public List<Music> scanDBForMusicList(){
		List<Music> listRaw = new ArrayList<Music>();
		listRaw = dbHelper.queryForList(new RowMapper<Music>() {

			@Override
			public Music mapRow(Cursor cursor, int count) {
				Music Music = new Music();
				Music.setmMusicSinger(cursor.getString(cursor.getColumnIndex(DBConstant.LOCAL_ARTIST)));
				Music.setmMusicName(cursor.getString(cursor.getColumnIndex(DBConstant.LOCAL_NAME)));
				Music.setmMusicUrl(cursor.getString(cursor.getColumnIndex(DBConstant.LOCAL_PATH)));
				return Music;
			}
			
		}, "select * from "+DBConstant.TABLE_LOCALMUSIC, null);
		return listRaw;
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
