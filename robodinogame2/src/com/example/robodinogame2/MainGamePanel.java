package com.example.robodinogame2;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread thread;
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		 getHolder().addCallback(this);
		  // make the GamePanel focusable so it can handle events
		 thread = new GameThread();
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
		
		  return super.onTouchEvent(event);
		
	}
	
	protected void onDraw(Canvas canvas) {
		
	}

 

}
