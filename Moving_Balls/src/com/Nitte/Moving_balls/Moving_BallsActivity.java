package com.Nitte.Moving_balls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class LoopHandler extends Handler {
	private static String TAG = "Animate";

	boolean mShouldStop;
	int mIterationsRequired;
	int mCurIteration;
	View mView, m1View;

	public LoopHandler(int count, View view) {
		mIterationsRequired = count;
		mCurIteration = 0;
		mView = view;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.d(TAG, "message received");

		if (mCurIteration < mIterationsRequired) {
			++mCurIteration;

			mView.invalidate();

			this.sendEmptyMessage(0);
		}
	}
}

class AnimateView extends View {
	Paint mPaint, m1Paint, mYPaint,linePaint;
	int mX, mY, mX1, mY1, X, Y, X1 = 4, Y1 = 7, X2 = 6, Y2 = 5;
	int xDir=-1,yDir=-1,xDir1=-1,yDir1=1;
	float touchX1,touchY1,touchX2,touchY2;
	double A,B1,C1,B2,C2,Dsq,t;
	boolean int1=false,int2=false;
	int touchState=0;

	public AnimateView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.WHITE);
		mYPaint = new Paint();
		mYPaint.setColor(Color.WHITE);
		mX = 190;
		mY = 190;
		
		m1Paint = new Paint();
		m1Paint.setAntiAlias(true);
		m1Paint.setDither(true);
		m1Paint.setColor(Color.YELLOW);
		mX1 = 90;
		mY1 = 90;

		linePaint=new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setDither(true);
		linePaint.setColor(Color.GRAY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(touchState==0) {
			touchX1=e.getX();
			touchY1=e.getY();
			touchState++;
		}
		else if(touchState==1) {
			touchX2=e.getX();
			touchY2=e.getY();
			touchState++;
		}
		else {
			touchX1=touchX2;
			touchY1=touchY2;
			touchX2=e.getX();
			touchY2=e.getY();
		}
		return super.onTouchEvent(e);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawARGB(255, 10, 10, 10);
		canvas.drawCircle(mX, mY, 30, mPaint);
		canvas.drawCircle(mX1, mY1, 30, m1Paint);
		canvas.drawText("BALLS ARE MOVING", 100, 50, mYPaint);
		if(touchState==2)
			canvas.drawLine(touchX1, touchY1, touchX2, touchY2, linePaint);
		mYPaint.setTextSize(40);
		X = canvas.getWidth();
		Y = canvas.getHeight() - 60;

		if(mX>=X-30) xDir*=-1;
		if(mY>=Y-30) yDir*=-1;
		if(mX<=30) xDir*=-1;
		if(mY<=30) yDir*=-1;
		
		if(xDir>0) mX+=6;
		else mX-=6;
		if(yDir>0) mY+=4;
		else mY-=4;
		
		if(mX1>=X-30) xDir1*=-1;
		if(mY1>=Y-30) yDir1*=-1;
		if(mX1<=30) xDir1*=-1;
		if(mY1<=30) yDir1*=-1;
		
		if(xDir1>0) mX1+=10;
		else mX1-=10;
		if(yDir1>0) mY1+=8;
		else mY1-=8;
		
		if(Math.sqrt((mX-mX1)*(mX-mX1)+(mY-mY1)*(mY-mY1))<60) {
			xDir*=-1; yDir*=-1; xDir1*=-1; yDir1*=-1;
			if(xDir>0) mX+=6;
			else mX-=6;
			if(yDir>0) mY+=4;
			else mY-=4;
			if(xDir1>0) mX1+=10;
			else mX1-=10;
			if(yDir1>0) mY1+=8;
			else mY1-=8;			
		}
		A=(touchX2-touchX1)*(touchX2-touchX1)+(touchY2-touchY1)*(touchY2-touchY1);
		B1=2*(touchX1-mX)*(touchX2-touchX1)+2*(touchY1-mY)*(touchY2-touchY1);
		C1=(touchX1-mX)*(touchX1-mX)+(touchY1-mY)*(touchY1-mY)-30*30;
		B2=2*(touchX1-mX1)*(touchX2-touchX1)+2*(touchY1-mY1)*(touchY2-touchY1);
		C2=(touchX1-mX1)*(touchX1-mX1)+(touchY1-mY1)*(touchY1-mY1)-30*30;
		Dsq=B1*B1-4*A*C1;
		int1=false;
		int2=false;
		if(Dsq>=0) {
			t=(-B1+Math.sqrt(Dsq))/(2*A);
			if(t>=0 && t<=1)
				int1=true;
			t=(-B1-Math.sqrt(Dsq))/(2*A);
			if(t>=0 && t<=1)
				int1=true;
		}
		Dsq=B2*B2-4*A*C2;
		if(Dsq>=0) {
			t=(-B2+Math.sqrt(Dsq))/(2*A);
			if(t>=0 && t<=1)
				int2=true;
			t=(-B2-Math.sqrt(Dsq))/(2*A);
			if(t>=0 && t<=1)
				int2=true;
		}
		if(int1) {
			xDir*=-1; yDir*=-1;
			if(xDir>0) mX+=6;
			else mX-=6;
			if(yDir>0) mY+=4;
			else mY-=4;
		}
		if(int2) {
			xDir1*=-1; yDir1*=-1;
			if(xDir1>0) mX1+=10;
			else mX1-=10;
			if(yDir1>0) mY1+=8;
			else mY1-=8;
		}
	}
}

public class Moving_BallsActivity extends Activity {
	private static String TAG = "Animate";

	Handler mHandler;
	AnimateView mView, m1View, m2View;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		mView = new AnimateView(this);
		setContentView(mView);
		mHandler = new LoopHandler(1000, mView);
	}
		
	@Override
	public void onResume() {
		super.onResume();
		mHandler.sendEmptyMessage(99);
		Log.d(TAG, "onResume");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}