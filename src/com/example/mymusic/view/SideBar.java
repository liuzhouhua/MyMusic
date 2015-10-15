package com.example.mymusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
	
	private static final String TAG = "SideBar";

	public static String letter[]  = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
		"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
		"W", "X", "Y", "Z", "#"};
	
	//画字母的画笔
	private Paint paint = new Paint();
	//画选中时的矩形框
	private Paint rectPaint = new Paint();
	private float rectwidth = 0;
	private int choose = -1;
	private int scrollChoose = -1;
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	//屏幕中间显示的大的选中的字母
	private TextView textView;
	
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
		rectwidth = paint.measureText("#");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height/letter.length;
		float rectX = width/2-paint.measureText("#")/2-rectwidth;
		float rectX2 = rectX + 3*rectwidth;
		
		for(int i=0;i<letter.length;i++){
			paint.setColor(Color.parseColor("#c0c0c0"));
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(20);
			float x = width/2-paint.measureText(letter[i])/2;
			float y = singleHeight*i+singleHeight;
			if (i == choose) {
				paint.setColor(Color.parseColor("#ff2020"));
				paint.setFakeBoldText(true);
				canvas.drawRect(rectX,y-singleHeight/2 , rectX2, y+rectwidth, rectPaint);
			}
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
		case MotionEvent.ACTION_DOWN:
			if(oldChoose!=c && listener!=null){
				textView.setVisibility(View.VISIBLE);
				textView.setText(letter[c]);
				doOnActionDown(c);
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			if(oldChoose!=c && listener!=null){
				textView.setVisibility(View.VISIBLE);
				textView.setText(letter[c]);
				doOnActionDown(c);
			}
			break;
		case MotionEvent.ACTION_UP:
			textView.setVisibility(View.GONE);
			invalidate();
			break;
		default:
			
			break;
		}
		
		
		return true;
	}
	
	
	public interface OnTouchingLetterChangedListener{
		public void onTouchingLetterChanged(String s);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
	
	/* 
    * 当侧边栏被按下的动作 
    * @param c 
    */  
   private void doOnActionDown(int c) {  
       if (c > 0 && c < letter.length) {
    	   onTouchingLetterChangedListener.onTouchingLetterChanged(letter[c]);
               choose = c;  
               invalidate();
       } 
   }

   public void setTextView(TextView textView) {
	   this.textView = textView;
   }

   //设置listview滚动时候调用它 
   public void setColorWhenListViewScrolling(int c){
	   if (scrollChoose != c) {
		   scrollChoose = c;
           choose = c;  
           invalidate();  
       } 
   }

}
