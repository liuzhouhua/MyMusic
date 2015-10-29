package com.example.mymusic.adapter;

import java.util.List;

import com.example.mymusic.R;
import com.example.mymusic.model.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalSingerSingleAdapter extends BaseAdapter {
	
	private List<Music> mDatalist;
	private Context context;

	public LocalSingerSingleAdapter(Context context ,List<Music> mDatalist) {
		this.context = context;
		this.mDatalist = mDatalist;
	}

	@Override
	public int getCount() {
		if(mDatalist!=null){
			return mDatalist.size();
		}
		return 0;
	}

	@Override
	public Music getItem(int position) {
		return mDatalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
			viewHolder.mMusicName = (TextView) convertView.findViewById(R.id.item_title);
			viewHolder.mSingerName = (TextView) convertView.findViewById(R.id.item_artist);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.mMusicName.setText(mDatalist.get(position).getmMusicName());
		viewHolder.mSingerName.setText(mDatalist.get(position).getmMusicSinger());
		
		return convertView;
	}
	
	public class ViewHolder{
		TextView mSingerName;
		TextView mMusicName;
	}

}
