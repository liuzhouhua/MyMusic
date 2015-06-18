package com.example.mymusic.layout;

import com.example.mymusic.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BackTitleLayout extends LinearLayout {

	private final Button backbtn;
	private final TextView titletext;
	private final TextView othertext;
	private Context mContext;

	public BackTitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.globaltitle, this);
		mContext = context;
		backbtn = (Button) findViewById(R.id.titile_back);
		titletext = (TextView) findViewById(R.id.title_text);
		othertext = (TextView) findViewById(R.id.title_edit);
		
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)mContext).finish();
			}
		});
		
		othertext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	

}
