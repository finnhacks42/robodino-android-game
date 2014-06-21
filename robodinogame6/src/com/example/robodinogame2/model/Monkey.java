package com.example.robodinogame2.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;



public class Monkey {
	private Bitmap bmpBody;
	private int bodyX;
	private int bodyY;
	private Bitmap bmpHead;
	private int headX;
	private int headY;
	private Bitmap bmpLeftarm;
	private int leftArmX;
	private int leftArmY;
	private Bitmap bmpRightArm;
	private int rightArmX;
	private int rightArmY;
	private Bitmap bmpTail;
	private int tailX;
	private int tailY;
	
	public Monkey(Bitmap bodyBitmap, int bodyX, int bodyY,
					Bitmap headBitmap, int headX, int headY,
					Bitmap rightArmBitmap, int rightArmX, int rightArmY,
					Bitmap leftArmBitmap, int leftArmX, int leftArmY,
					Bitmap tailBitmap, int tailX, int tailY	) {
		this.bmpBody = bodyBitmap;
		this.bmpHead = headBitmap;
		this.bmpLeftarm = leftArmBitmap;
		this.bmpRightArm = rightArmBitmap;
		this.bmpTail = tailBitmap;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		this.headX = headX;
		this.headY = headY;
		this.leftArmX = leftArmX;
		this.leftArmY = leftArmY;
		this.rightArmX = rightArmX;
		this.rightArmY = rightArmY;
		this.tailX = tailX;
		this.tailY = tailY; 
	}
		
/*	public int getX() {
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
	*/
	public void draw(Canvas canvas) {
		
		 canvas.drawBitmap(this.bmpBody, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpHead, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpLeftarm, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpRightArm, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpTail, bodyX, bodyY, null);
		 
		
	}
}
