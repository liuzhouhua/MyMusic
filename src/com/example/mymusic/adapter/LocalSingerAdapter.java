package com.example.mymusic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.mymusic.R;
import com.example.mymusic.model.Music;
import com.example.mymusic.model.Singer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class LocalSingerAdapter extends BaseAdapter implements SectionIndexer{
	
	private List<Singer> mDataList;
	private Context context;
	
	public LocalSingerAdapter(Context context,List<Singer> datas){
		this.mDataList = datas;
		this.context = context;
	}

	@Override
	public int getCount() {
		if(mDataList==null){
			mDataList = new ArrayList<Singer>();
		}
		return mDataList.size();
	}

	@Override
	public Singer getItem(int position) {
		return mDataList.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.local_singer_item, parent, false);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			viewHolder.tvCount = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//根据position获取分类的首字母的char ascii值
		int selection = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现  
		if(position==getPositionForSection(selection)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			if(mDataList.get(position).getLetterForSinger().matches("^[a-zA-Z]+$")){
				viewHolder.tvLetter.setText(mDataList.get(position).getLetterForSinger());
			}else{
				viewHolder.tvLetter.setText("#");
			}
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(mDataList.get(position).getSinger());
		viewHolder.tvCount.setText(mDataList.get(position).getCount()+"首");
		return convertView;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//根据首字母的ascii值来获取在该ListView中第一次出现该首字母的位置
	@Override
	public int getPositionForSection(int sectionIndex) {
		for(int i=0;i<getCount();i++){
			String storstr = mDataList.get(i).getLetterForSinger();
			char firstchar = storstr.charAt(0);
			if(firstchar==sectionIndex){
				return i;
			}
		}
		return -1;
	}

	//根据ListView的position来获取该位置上面的索引值，这里使用name的首字母char的ascii值代替，
	//例如： 如果该position上面的name是阿妹，首字母就是A，那么此方法返回的就是'A'字母的ascii值，也就是65
	@Override
	public int getSectionForPosition(int position) {
		return mDataList.get(position).getLetterForSinger().charAt(0);
	}

	final static class ViewHolder{
		TextView tvLetter;
		TextView tvTitle;
		TextView tvCount;
	}
}
