package com.example.robodinogame2;

import com.example.robodinogame2.model.Robot;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static String TAG = MainGamePanel.class.getSimpleName();
	private MainThread thread;
	private Robot robot;
	private Robot bana;
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		 getHolder().addCallback(this);
		 
		 robot = new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1),50,50);
		 bana = new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.bana),350,550);
		 
		 thread = new MainThread(getHolder(),this);
		 
		// make the GamePanel focusable so it can handle events
		 setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		thread.setRunning(true);
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {// ignore and try again}
		  }
		}
		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			robot.handleActionDown((int)event.getX(),(int)event.getY());
			if (event.getY() > getHeight() - 100) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG,"Coords:"+event.getX()+","+event.getY());
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (robot.isTouched()) {
				robot.setX((int)event.getX());
				robot.setY((int)event.getY());
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (robot.isTouched()) {
				robot.setTouched(false);
			}
		}
		
		return true;
		
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		robot.draw(canvas);
		bana.draw(canvas);
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10,null);
	}

 

}
