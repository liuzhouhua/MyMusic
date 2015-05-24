package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.MainPageAdapter;
import com.example.mymusic.constant.DBConstant;
import com.example.mymusic.db.MusicDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements OnPageChangeListener, OnCheckedChangeListener {

	private String TAG = "MainActivity";
	private ViewPager topTab;
	private MainPageAdapter adapter;
	private List<Fragment> fragments;
	private RadioGroup radioGroupForTopTab;
	private RadioButton newest,muzikland,search,me;
	private MusicDBHelper dbHelper;
	private SQLiteDatabase sb;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化
        initview();
        initDataBase();
    }

    /**
     * 初始化
     */
    private void initview(){
    	topTab = (ViewPager) findViewById(R.id.pager);
    	fragments = new ArrayList<Fragment>();
    	fragments.add(new NewEstFragment());
    	fragments.add(new MuziklandFragment());
    	fragments.add(new SearchFragment());
    	fragments.add(new MeFragment());    	
    	adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
    	topTab.setAdapter(adapter);
    	topTab.setOffscreenPageLimit(fragments.size()-1);
    	topTab.setOnPageChangeListener(this);
    		
    	radioGroupForTopTab = (RadioGroup) findViewById(R.id.music_taball);
    	newest = (RadioButton) findViewById(R.id.newest);
    	muzikland = (RadioButton) findViewById(R.id.muzikland);
    	search = (RadioButton) findViewById(R.id.search);
    	me = (RadioButton) findViewById(R.id.me);
    	radioGroupForTopTab.setOnCheckedChangeListener(this);
    }
    
    public void initDataBase(){
    	dbHelper = new MusicDBHelper(this,DBConstant.DB_NAME , null, DBConstant.DB_VERSION);
    	try{
    		sb = dbHelper.getWritableDatabase();
    	}catch(Exception e){
    		Log.e(TAG, "DataBase create err");
    	}
    	Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    			null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    	Log.i(TAG, "cursor :"+cursor);
    	cursor.moveToFirst();
    	ContentValues contentValues = new ContentValues();
    	for(int i = 0 ;i < cursor.getCount() ; i++){
    		contentValues.put(DBConstant.LOCAL_TITLE, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
    		contentValues.put(DBConstant.LOCAL_ALBUM, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
    		contentValues.put(DBConstant.LOCAL_ARTIST, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
    		contentValues.put(DBConstant.LOCAL_PATH, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
    		contentValues.put(DBConstant.LOCAL_DURATION, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
    		contentValues.put(DBConstant.LOCAL_FILE_SIZE, cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
//    		sb.insert(DBConstant.TABLE_LOCALMUSIC,null, contentValues);
    		contentValues.clear();
    		cursor.moveToNext();
    	}
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPageScrollStateChanged");
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPageScrolled");
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPageSelected");
		getTagState(arg0);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkid) {
		Log.i(TAG, "onCheckedChanged");
		switch(checkid){
		case R.id.newest:
			topTab.setCurrentItem(0);
			break;
		case R.id.muzikland:
			topTab.setCurrentItem(1);
			break;
		case R.id.search:
			topTab.setCurrentItem(2);
			break;
		case R.id.me:
			topTab.setCurrentItem(3);
			break;
		}
	}
	
	private void getTagState(int position){
		newest.setChecked(false);
		muzikland.setChecked(false);
		search.setChecked(false);
		me.setChecked(false);
		switch(position){
		case 0:
			newest.setChecked(true);
			break;
		case 1:
			muzikland.setChecked(true);
			break;
		case 2:
			search.setChecked(true);
			break;
		case 3:
			me.setChecked(true);
			break;
		}
	}
	
	
    
}
