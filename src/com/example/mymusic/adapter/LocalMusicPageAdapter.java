package com.example.mymusic.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class LocalMusicPageAdapter extends FragmentPagerAdapter{

	private List<Fragment> fragments;
	
	public LocalMusicPageAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public LocalMusicPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
