package com.example.robodinogame2;

import java.util.Random;

import com.example.robodinogame2.model.Banana;
import com.example.robodinogame2.model.Monkey;
import com.example.robodinogame2.model.Robot;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static String TAG = MainGamePanel.class.getSimpleName();
	private MainThread thread;
	private Robot[] robot = new Robot[10];
	private Banana[] banana = new Banana[5];
	private Monkey monkey;
	long lastTick = 0;
	int startX = 0;
	int startY = 0;
	 int screenWidth;
	 int screenHeight;
	 int currentBanana = 0;
	 int score = 0;
	 
	 Random r=new Random();
	//	speed= r.nextInt(10);
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		 getHolder().addCallback(this);
		 DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		 screenWidth = metrics.widthPixels;
		 screenHeight = metrics.heightPixels;
		 
		 //initialise robots
		 for(int i=0; i<robot.length; i++){
		       robot[i] = new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1),-300,r.nextInt(screenHeight));
		}
		for(int i=0; i<banana.length; i++){
		  banana[i]  = new Banana(BitmapFactory.decodeResource(getResources(), R.drawable.betterbanana),-300,200,100);
		}
		 thread = new MainThread(getHolder(),this);
		 monkey = new Monkey(BitmapFactory.decodeResource(getResources(), R.drawable.monkeybody),
				 BitmapFactory.decodeResource(getResources(), R.drawable.monkeyhead),
				 BitmapFactory.decodeResource(getResources(), R.drawable.monkeyrightarm),
				 BitmapFactory.decodeResource(getResources(), R.drawable.monkeyleftarm),
				 BitmapFactory.decodeResource(getResources(), R.drawable.monkeytail),
				 screenWidth-500,screenHeight-500);
		// make the GamePanel focusable so it can handle events
		 setFocusable(true);
		 //detect scren size

		
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

			//robot.handleActionDown((int)event.getX(),(int)event.getY());
			if (event.getY() > getHeight() - 100) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG,"Coords:"+event.getX()+","+event.getY());
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
		//	if (robot.isTouched()) {
		//		robot.setX((int)event.getX());
		//		robot.setY((int)event.getY());
		//	}
			//move banana with finger
			banana[currentBanana].setX((int)event.getX());
			banana[currentBanana].setY((int)event.getY());
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			//set end point for the drag event
			banana[currentBanana].setX((int)event.getX());
			banana[currentBanana].setY((int)event.getY());
		//	if (robot.isTouched()) {
		//		robot.setTouched(false);

		//	}
			Log.d(TAG, "end vals = " + banana[currentBanana].getX() + " , " + banana[currentBanana].getY());
			
			//calculate difference between start and end touch points
			int diffX = startX - banana[currentBanana].getX();
			int diffY = startY - banana[currentBanana].getY();
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
			
		    banana[currentBanana].setMoveX(diffX/10);
		    banana[currentBanana].setMoveY(diffY/10);
		    
		    //increment current banana and check to see if it should loop around
		    currentBanana ++;
		    if(currentBanana == banana.length) currentBanana = 0;
		    
		    Log.d(TAG, "angle =" + banana[currentBanana].getAngle());
		    Log.d(TAG, "velocity =" + banana[currentBanana].getVelocity());
		}
		
		return true;
		
	}
	
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		 for(int i=0; i<robot.length; i++){
		robot[i].draw(canvas);
		 }
		for(int i=0; i<banana.length; i++){
		    banana[i].draw(canvas);
		}
		    monkey.draw(canvas);
		
        //I'm using the draw method to update the game at 25fps
		
        if (lastTick < System.currentTimeMillis()  +250) {
        	lastTick = System.currentTimeMillis();
        	if(r.nextInt(50) == 1){
        		 for(int i=0; i<robot.length; i++){
        			 if(robot[i].getX() ==-300){
        				robot[i].setX(-100);
        				robot[i].setY(r.nextInt(screenHeight));
        			 }
        		 }
        	}
        	
        	for(int i=0; i<robot.length; i++){
        	   robot[i].update();  // <--monkey? TODO
        	}
        	
        	for(int i=0; i<banana.length; i++){
        	    banana[i].update();
        	}
        	collisionDetect();
        	monkey.waveArm();
        	R.id.score;
        }
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10,null);
	}

 public void collisionDetect() {
	 for(int i=0; i<robot.length; i++){
		 for(int j=0; j<banana.length; j++){
	 if(banana[j].getX() < robot[i].getX()+robot[i].getWidth() && banana[j].getX()+ banana[j].getWidth() > robot[i].getX()){
		 if(banana[j].getY() < robot[i].getY() + robot[i].getHeight() && banana[j].getY() + banana[j].getHeight() > robot[i].getY()){
			 
			 //collision
			 score++;
		 banana[j].setMoveX(0);
		 robot[i].kill();
		// let teh banana fall away banana.setMoveY(0);
		 }
	 }
	 }
	 }
	 for(int i=0; i<robot.length; i++){
	   if(robot[i].getX() > screenWidth){
 		 robot[i].kill();
	   }
	 }
}
 
 

}
