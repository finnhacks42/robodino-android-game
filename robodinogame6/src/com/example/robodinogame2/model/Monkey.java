package com.example.robodinogame2.model;

import com.example.robodinogame2.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	
	public Monkey(Bitmap bodyBitmap, Bitmap headBitmap, Bitmap rightArmBitmap, Bitmap leftArmBitmap, Bitmap tailBitmap,int assemblyX, int assemblyY) { //
		this.bmpBody = bodyBitmap;
		this.bmpHead = headBitmap;
		this.bmpLeftarm = leftArmBitmap;
		this.bmpRightArm = rightArmBitmap;
		this.bmpTail = tailBitmap;
		this.bodyX = assemblyX;
		this.bodyY = assemblyY;
		this.headX = assemblyX;
		this.headY = assemblyY;
		this.leftArmX = assemblyX;
		this.leftArmY = assemblyY;
		this.rightArmX = assemblyX;
		this.rightArmY = assemblyY;
		this.tailX = assemblyX;
		this.tailY = assemblyY; //*/
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
		
		 canvas.drawBitmap(this.bmpBody,bodyX, bodyY, null);
		canvas.drawBitmap(this.bmpHead, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpLeftarm, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpRightArm, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpTail, bodyX, bodyY, null);
		 //*/
		
	}
}
