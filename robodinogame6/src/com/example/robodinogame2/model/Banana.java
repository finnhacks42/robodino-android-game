package com.example.robodinogame2.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Banana {
	private Bitmap bitmap;
private Bitmap scaledBananaBitmap;
private Bitmap rotatableBitmap;
private int x;
private int y;
private int velocity;
private int angle;
private int scale;
private int height = 50;
private int width = 50;

//vars for the amount we should adjust for movement (this was much easier than using angle+velocity
// but we may have to come back and convert it at some stage...
private int moveX;
private int moveY;
private int orientationDegree;


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

public void rotate(){
	this.orientationDegree+=8;
	if(this.orientationDegree>=360)
	{
		this.orientationDegree=0;
	}
	Matrix matrix = new Matrix();
	matrix.postRotate(this.orientationDegree);
	this.rotatableBitmap = Bitmap.createBitmap(this.scaledBananaBitmap, 0, 0, this.scaledBananaBitmap.getWidth(),this.scaledBananaBitmap.getHeight(), matrix, false);

}

public Banana(Bitmap bitmap, int x, int y, int scale) {
	
	this.bitmap = bitmap;
	this.scaledBananaBitmap= Bitmap.createScaledBitmap(this.bitmap, width, height, false);
	this.rotatableBitmap=this.scaledBananaBitmap;
	this.x = x;
	this.y = y;
	this.orientationDegree=0;
	
	
	
}

public void draw(Canvas canvas) {
	
	 canvas.drawBitmap(rotatableBitmap, x, y, null);
	 
	
}
public void update(){
	//update the movement
	x+=moveX;
	y+=moveY;
	//apply gravity
	moveY+=2;
	


	
}
public int getMoveX() {
	return moveX;
}
public void setMoveX(int moveX) {
	this.moveX = moveX;
}
public int getMovey() {
	return moveY;
}
public void setMoveY(int moveY) {
	this.moveY = moveY;
}
public int getWidth() {
	return width;
}
public int getHeight() {
	return height;
}

}
