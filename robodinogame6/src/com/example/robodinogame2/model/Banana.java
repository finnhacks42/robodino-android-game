package com.example.robodinogame2.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Banana {
	private Bitmap bitmap;
private int x;
private int y;
private int velocity;
private int angle;

public int getX() {
	return x;
}
public int getVelocity() {
	return velocity;
}
public void setVelocity(int velocity) {
	this.velocity = velocity;
}

public void setDensity(int density){
	this.bitmap.setDensity(density);
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
public Banana(Bitmap bitmap, int x, int y) {
	this.bitmap = bitmap;
	this.x = x;
	this.y = y;
}

public void draw(Canvas canvas) {
	 canvas.drawBitmap(bitmap, x, y, null);
	
}
}
