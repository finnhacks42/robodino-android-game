package com.example.robodinogame2;

import com.example.robodinogame2.model.Banana;
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
	private Banana banana;
	long lastTick = 0;
	int startX = 0;
	int startY = 0;
	
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		 getHolder().addCallback(this);
		 
		 robot = new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1),50,50);
		 banana  = new Banana(BitmapFactory.decodeResource(getResources(), R.drawable.betterbanana),200,200,100);
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
			startX = (int)event.getX();
			startY = (int)event.getY();

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
			banana.setX((int)event.getX());
			banana.setY((int)event.getY());
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			banana.setX((int)event.getX());
			banana.setY((int)event.getY());
			if (robot.isTouched()) {
				robot.setTouched(false);

			}
			Log.d(TAG, "end vals = " + banana.getX() + " , " + banana.getY());
			
			int diffX = startX - banana.getX();
			int diffY = startY - banana.getY();
			Log.d(TAG, "diff = " + diffX + " , " + diffY);

			double theta = Math.atan2(diffY, diffX);
	


		
		    double angle = Math.toDegrees(theta);

		    if (angle < 0) {
		        angle += 360;
		    }
		    
		    //if(diffY<0) diffY = diffY *-1;
		   // if(diffX<0) diffX = diffX *-1;
		    banana.setMoveX(diffX/10);
		    banana.setMoveY(diffY/10);
		    banana.setVelocity((int)Math.sqrt(Math.pow(startX - banana.getX(), 2) + Math.pow(startY- banana.getY(), 2)));
		    banana.setAngle((int)angle);
		    Log.d(TAG, "angle =" + banana.getAngle());
		    Log.d(TAG, "velocity =" + banana.getVelocity());
		}
		
		return true;
		
	}
	
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		robot.draw(canvas);
		banana.draw(canvas);
		

        if (lastTick < System.currentTimeMillis()  +250) {
        	lastTick = System.currentTimeMillis();
        	//banana.setY(banana.getY()+ 10);
        	//banana.rotateBanana();
        	//Log.d(TAG, "tick...");
        	banana.update();
        }
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10,null);
	}

 

}
