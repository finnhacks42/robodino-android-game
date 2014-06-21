package com.example.robodinogame2.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

public class Robot {
	private Bitmap bitmap;
	private int x;
	private int y;
	private boolean touched;
	private int width;
	private int height;
	private int speed;
	
	public Robot(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		Random r=new Random();
		speed= r.nextInt(10);
		

		
	}
	
	public boolean isTouched() {
		return touched;
	}
	
	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	public void draw(Canvas canvas) {
		 canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}

	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
public void update(){
	if(x > -300){
	x+=speed;
	}
}
public void kill(){
	x = -100;
	
		
}
	
	
	
	
	

}
