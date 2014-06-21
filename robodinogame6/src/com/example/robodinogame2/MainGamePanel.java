package com.example.robodinogame2;

import com.example.robodinogame2.model.Banana;
import com.example.robodinogame2.model.Monkey;
import com.example.robodinogame2.model.Robot;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static String TAG = MainGamePanel.class.getSimpleName();
	private MainThread thread;
	private Robot robot;
	private Banana banana;
	private Monkey monkey;
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
		 monkey = new Monkey(BitmapFactory.decodeResource(getResources(), R.drawable.bettermonkey),50,50);
		// make the GamePanel focusable so it can handle events
		 setFocusable(true);
		 
		 //detect screen size
		 
	//	 Display display = getWindowManager().getDefaultDisplay();
	//	 Point size = new Point();
	//	 display.getSize(size);
	//	 int width = size.x;
	//	 int height = size.y;
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
			// record start values for the touch+ drag gesture
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
			//move banana with finger
			banana.setX((int)event.getX());
			banana.setY((int)event.getY());
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			//set end point for the drag event
			banana.setX((int)event.getX());
			banana.setY((int)event.getY());
			if (robot.isTouched()) {
				robot.setTouched(false);

			}
			Log.d(TAG, "end vals = " + banana.getX() + " , " + banana.getY());
			
			//calculate difference between start and end touch points
			int diffX = startX - banana.getX();
			int diffY = startY - banana.getY();
			Log.d(TAG, "diff = " + diffX + " , " + diffY);

			/*
			 * this code converts the 2 points into velocity+angle values.
			 * Really, this is the best way to manage movement of the banana 
			 * but it was harder, and this cheat method works well
			 
			double theta = Math.atan2(diffY, diffX);
		    double angle = Math.toDegrees(theta);
		    if (angle < 0) {
		        angle += 360;
		   
		   } 
			banana.setVelocity((int)Math.sqrt(Math.pow(startX - banana.getX(), 2) + Math.pow(startY- banana.getY(), 2))); 
			banana.setAngle((int)angle);
		    //*/
		    
			
			//cheat code for banana movement: simply send 2 values that are added to the X and
			// y co-ords every tick, incrementing the y value every tick to add gravity.
			
		    banana.setMoveX(diffX/10);
		    banana.setMoveY(diffY/10);
		    
		    
		    
		    Log.d(TAG, "angle =" + banana.getAngle());
		    Log.d(TAG, "velocity =" + banana.getVelocity());
		}
		
		return true;
		
	}
	
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		robot.draw(canvas);
		banana.draw(canvas);
		
//I'm using the draw method to update the game at 25fps
		
        if (lastTick < System.currentTimeMillis()  +250) {
        	lastTick = System.currentTimeMillis();

        	//robot.update();   <--monkey? TODO
        	banana.update();
        	collisionDetect();
        }
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10,null);
	}

 public void collisionDetect() {
	 
	 if(banana.getX() < robot.getX()+robot.getWidth() && banana.getX()+ banana.getWidth() > robot.getX()){
		 if(banana.getY() < robot.getY() + robot.getHeight() && banana.getY() + banana.getHeight() > robot.getY()){
			 
			 //collision
		 banana.setMoveX(0);
		 banana.setMoveY(0);
		 }
	 }
}
 
 

}
