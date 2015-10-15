package com.example.mymusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SideBar extends View {

	public static String letter[]  = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
		"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
		"W", "X", "Y", "Z", "#"};
	
	private Paint paint = new Paint();
	private Paint rectPaint = new Paint();
	private int rectwidth = 0;
	private boolean showBg = false;
	private int choose = -1;
	private int scrollChoose = -1;
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	
	public SideBar(Context context) {
		super(context);
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init(){
		rectPaint.setColor(Color.parseColor("#CCCCCC"));
		rectwidth = (int) paint.measureText("#");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(showBg){
			canvas.drawColor(Color.parseColor("#CCCCCC"));
		}
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height/letter.length;
		int rectX = (int) (width/2-paint.measureText(letter[i])/2-rectwidth);
		int rectX2 = rectX + 3*rectwidth;
		
		for(int i=0;i<letter.length;i++){
			paint.setColor(Color.parseColor("#c0c0c0"));
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(20);
			if (i == choose) {
				paint.setColor(Color.parseColor("#ff2020"));
				paint.setFakeBoldText(true);
			}
			float x = width/2-paint.measureText(letter[i])/2;
			float y = singleHeight*i+singleHeight;
			canvas.drawText(letter[i], x, y, paint);
			paint.reset();
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int oldChoose = choose;
		final int c = (int) (y/getHeight()*letter.length);
		
		switch(action){
		case MotionEvent.ACTION_UP:
			if(oldChoose!=c){
				choose = c;
			}
			
			break;
		default:
			
			break;
		}
		
		
		return super.dispatchTouchEvent(event);
	}
	
	
	public interface OnTouchingLetterChangedListener{
		public void onTouchingLetterChanged(String s);
	}


	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

}
