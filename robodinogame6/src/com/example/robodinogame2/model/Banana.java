package com.example.robodinogame2.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Banana {
	private Bitmap bitmap;
private Bitmap scaledBananaBitmap;
private int x;
private int y;
private int velocity;
private int angle;
private int scale;


public int getX() {
	return x;
}
public int getVelocity() {
	return velocity;
}
public void setVelocity(int velocity) {
	this.velocity = velocity;
}



public int getAngle() {
	return angle;
}
public void setAngle(int angle) {
	this.angle = angle;
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

private void rotateBanana(){
	Matrix matrix = new Matrix();
	matrix.setRotate(15);
	Bitmap bmpBowRotated = Bitmap.createBitmap(this.scaledBananaBitmap, 0, 0, this.scaledBananaBitmap.getWidth(),this.scaledBananaBitmap.getHeight(), matrix, false);
	this.scaledBananaBitmap=bmpBowRotated;
}

public Banana(Bitmap bitmap, int x, int y, int scale) {
	
	this.bitmap = bitmap;
	this.scaledBananaBitmap= Bitmap.createScaledBitmap(this.bitmap, 120, 120, false);
	this.x = x;
	this.y = y;
	
	
}

public void draw(Canvas canvas) {
	
	 canvas.drawBitmap(scaledBananaBitmap, x, y, null);
	 
	
}
}
