package com.example.robodinogame2;

import java.util.Random;

import com.example.robodinogame2.model.Banana;
import com.example.robodinogame2.model.Monkey;
import com.example.robodinogame2.model.Robot;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static String TAG = MainGamePanel.class.getSimpleName();
	private MainThread thread;
	private Robot[] robot = new Robot[20];
	private Banana[] banana = new Banana[5];
	private Monkey monkey;
	long lastTick = 0;
	int startX = 0;
	int startY = 0;
	 int screenWidth;
	 int screenHeight;
	 int currentBanana = 0;
	 int score = 0;
int explosionSize = 4; //smaller is bigger
	 int life = 10;
	 float timer=150;

	 String strScoreString="Score:";

	    Paint paint = new Paint();                          //define paint and paint color


	 

     Bitmap backgroundBmp;

	 
	 Random r=new Random();
	//	speed= r.nextInt(10);
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		 getHolder().addCallback(this);
		 DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		 screenWidth = metrics.widthPixels;
		 screenHeight = metrics.heightPixels;
		 strScoreString.format("Score: %d", score);
		 
		 //set background bitmap
		 backgroundBmp=BitmapFactory.decodeResource(getResources(), R.drawable.unending_plain3);
		 backgroundBmp= Bitmap.createScaledBitmap(backgroundBmp, screenWidth,  screenHeight, false);

		 //initialise robots
		 for(int i=0; i<robot.length; i++){
		       robot[i] = new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.dino),-300,r.nextInt(screenHeight));
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
		//	banana[currentBanana].setX((int)event.getX());
		//	banana[currentBanana].setY((int)event.getY());
		      banana[currentBanana].setVelocity(((int)Math.sqrt(Math.pow(startX - banana[currentBanana].getX(), 2) + Math.pow(startY- banana[currentBanana].getY(), 2)))); 

		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			//set end point for the drag event
			
			if((int)event.getX()> monkey.getAssemblyX() && event.getY() > monkey.getAssemblyY()){
				
				banana[currentBanana].setX((int)event.getX());
				banana[currentBanana].setY((int)event.getY());
		      banana[currentBanana].setVelocity(((int)Math.sqrt(Math.pow(startX - banana[currentBanana].getX(), 2) + Math.pow(startY- banana[currentBanana].getY(), 2)))); 

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
		}
		return true;
		
	}
	
	private void drawScore(Canvas canvas)
	{
		strScoreString = String.format("Score: %d", score);
    	
    	Resources resources = this.getResources();
    	float scale = resources.getDisplayMetrics().density;
    	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	  // text color - #3D3D3D
    	  paint.setColor(Color.rgb(120, 120, 120));
    	  // text size in pixels
    	  paint.setTextSize((int) (40 * scale));
    	  // text shadow
    	  paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
    	canvas.drawText(strScoreString, ((screenWidth/100)*60),((screenHeight/100)*10),paint );
	}
	private void drawLives(Canvas canvas)
	{
		String strlifeString = String.format("Life: %d", life);
    	
    	Resources resources = this.getResources();
    	float scale = resources.getDisplayMetrics().density;
    	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	  // text color - #3D3D3D
    	  paint.setColor(Color.rgb(120, 120, 120));
    	  // text size in pixels
    	  paint.setTextSize((int) (40 * scale));
    	  // text shadow
    	  paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
    	canvas.drawText(strlifeString, ((screenWidth/100)*10),((screenHeight/100)*10),paint );
	}
	
	
	
	protected void onDraw(Canvas canvas) {

		
	    paint.setColor(Color.RED);
	    paint.setStyle(Style.FILL_AND_STROKE);
	    //paint.setAntiAlias(true);

	  
	    
	    
	

		//canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(backgroundBmp,0,0,null);

		  
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
        if(timer >5) timer -= 0.25;
        
       // Log.d(TAG, "score =" + score);
        	
        	if(r.nextInt((int) timer) == 1){
       
        		 for(int i=0; i<robot.length; i++){
        			 if(robot[i].getX() ==-300){
        				robot[i].setX(-100);
        				robot[i].setY(r.nextInt(screenHeight));
        				break;
        			 }
        		 }
        	}
        	
        	for(int i=0; i<robot.length; i++){
        	   robot[i].update();  // <--monkey? TODO
        	}
        	
        	for(int i=0; i<banana.length; i++){
        	    banana[i].update();
        	}
        	collisionDetect(canvas);
        	monkey.wiggleTail();
        	monkey.waveArm();
        	drawScore(canvas);
        	drawLives(canvas);
        	
        	
        }
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10,null);
	}

 public void collisionDetect(Canvas canvas) {
	 
	 for(int i=0; i<robot.length; i++){
		 if(robot[i].getX() > -300){
		   for(int j=0; j<banana.length; j++){
			 if(banana[j].getX() > -300){
	            if(banana[j].getX() < robot[i].getX()+robot[i].getWidth() && banana[j].getX()+ banana[j].getWidth() > robot[i].getX()){
		          if(banana[j].getY() < robot[i].getY() + robot[i].getHeight() && banana[j].getY() + banana[j].getHeight() > robot[i].getY()){
			 
		        	  //collision
		        	  score++;
		        	  Log.d(TAG, "moveX =" + banana[j].getMoveX());
		        	  if (banana[j].getVelocity() != 0) {
		        		  Log.d(TAG, "angleangleangle");
		        		  robot[i].kill();
		        		    canvas.drawCircle(banana[j].getX(), banana[j].getY(), Math.abs(banana[j].getVelocity())/explosionSize, paint);
		        		    for(int k=0; k<robot.length; k++){
		        		    	
		        		    	if((int)Math.sqrt(Math.pow(robot[k].getX() - banana[j].getX(), 2) + Math.pow( robot[k].getY()- banana[j].getY(), 2)) < Math.abs(banana[j].getVelocity())/explosionSize) {
		        		    		
		        		    		robot[k].kill();
		        		    	    score++;	
		        		    	
		        		    	}
		        		    	
		        		    }
		        		    }
		        		    
		        		    banana[j].setMoveX(0);
		        		    banana[j].setVelocity(0);
		        	  }
		 
		// let teh banana fall away banana.setMoveY(0);
		          }
	            }
			 }
		   }
		 }
	 
	 
	 for(int i=0; i<robot.length; i++){
	   if(robot[i].getX() > screenWidth){
 		 robot[i].kill();
 		 life--;
 		 if(life <= 0){
 			 life = 10;
 			 score = 0;
 		 }
	   }
	 }
}
 
 

}
