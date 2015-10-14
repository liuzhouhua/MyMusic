package com.example.mymusic;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.adapter.MainPageAdapter;
import com.example.mymusic.constant.ApplicationContext;
import com.example.mymusic.service.BackGroundService;
import com.example.mymusic.service.BackGroundService.PlayAndStopMusic;

import android.R.plurals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
	private PlayAndStopMusic palyAndStopMusicBinder;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			palyAndStopMusicBinder = (PlayAndStopMusic) service;
		}
	};
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //��ʼ��
        initview();
        initData();
    }

    /**
     * ��ʼ��
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
    
    private void initData(){
    	ApplicationContext.getInstance().init(getApplicationContext());
    	Intent service = new Intent(MainActivity.this, BackGroundService.class);
		bindService(service, connection, BIND_AUTO_CREATE);
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
	public void onPageSelected(int arg0) {
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
