package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.LocalMusicPageAdapter;
import com.example.mymusic.constant.Constant;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;
import com.example.mymusic.event.RefreshLocalMusicFragmentEvent;
import com.example.mymusic.event.RefreshLocalSingerFragmentEvent;
import com.example.mymusic.manager.MusicManager;

import de.greenrobot.event.EventBus;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter;

public class LocalMusicActivity extends FragmentActivity implements OnPageChangeListener, OnCheckedChangeListener, OnClickListener{
	
	private final String TAG = "LocalMusicActivity";
	private ViewPager mLocalMusicTab;
	private RadioButton mMusic,mSinger,mAlbum,mFilder;
	private RadioGroup mLocalMusicTopTabRadio;
	private Button mScanBtn;
	private LinearLayout mBackLayout;
	private List<Fragment> fragments;
	private LocalMusicPageAdapter adapter;
	private MusicManager mMusicManager;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localmusicactivity);
		initview();
	}
	
	public void initview(){
		mLocalMusicTab = (ViewPager) findViewById(R.id.pager_local);
		fragments = new ArrayList<Fragment>();
		fragments.add(new LocalMusicFragment());
		fragments.add(new LocalSingerFragment());
		fragments.add(new LocalAlbumFragment());
		fragments.add(new LocalFloderFragment());
		adapter = new LocalMusicPageAdapter(getSupportFragmentManager(),fragments);
		mLocalMusicTab.setAdapter(adapter);
		mLocalMusicTab.setOffscreenPageLimit(fragments.size()-1);
		mLocalMusicTab.setOnPageChangeListener(this);
		
		mLocalMusicTopTabRadio = (RadioGroup) findViewById(R.id.music_taball);
		mMusic = (RadioButton) findViewById(R.id.songs);
		mSinger = (RadioButton) findViewById(R.id.singer);
		mAlbum = (RadioButton) findViewById(R.id.sepcial);
		mFilder = (RadioButton) findViewById(R.id.folder);
		mLocalMusicTopTabRadio.setOnCheckedChangeListener(this);
		
		mBackLayout = (LinearLayout) findViewById(R.id.ly_back);
		mScanBtn = (Button) findViewById(R.id.title_edit);
		mBackLayout.setOnClickListener(this);
		mScanBtn.setOnClickListener(this);//
		
	}
	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkid) {
		Log.i(TAG, "onCheckedChanged");
		switch (checkid) {
		case R.id.songs:
			mLocalMusicTab.setCurrentItem(0);
			break;
		case R.id.singer:
			mLocalMusicTab.setCurrentItem(1);
			break;
		case R.id.sepcial:
			mLocalMusicTab.setCurrentItem(2);
			break;
		case R.id.folder:
			mLocalMusicTab.setCurrentItem(3);
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		Log.i(TAG, "onPageScrollStateChanged");
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.i(TAG, "onPageScrolled");
	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "onPageSelected");
		getTabState(position);
	}
	
	public void getTabState(int position){
		mMusic.setChecked(false);
		mSinger.setChecked(false);
		mAlbum.setChecked(false);
		mFilder.setChecked(false);
		switch (position) {
		case 0:
			mMusic.setChecked(true);
			break;
		case 1:
			mSinger.setChecked(true);
			break;
		case 2:
			mAlbum.setChecked(true);
			break;
		case 3:
			mFilder.setChecked(true);
			break;

		default:
			break;
		}
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.ly_back:
			finish();
			break;
		case R.id.title_edit:
			AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(LocalMusicActivity.this);
					progressDialog.setCancelable(false);
					progressDialog.setMessage(getString(R.string.import_music));
					progressDialog.show();
				}
				
				@Override
				protected Boolean doInBackground(Void... params) {
					int result = MusicManager.getInstance(LocalMusicActivity.this).ScanSDCardMusic();
					return result==0;
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					progressDialog.dismiss();
					if(result==true){
						EventBus.getDefault().post(new RefreshLocalMusicFragmentEvent());
						EventBus.getDefault().post(new RefreshLocalSingerFragmentEvent());
					}
				}
				
			};
			task.execute();
			break;
		}
	}

}
