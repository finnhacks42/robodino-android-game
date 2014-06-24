package com.example.robodinogame2.model;

import com.example.robodinogame2.R;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;



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
	private int assemblyX;
	private int assemblyY;
	private Bitmap rotatableRightArmBitmap;
	private int rightArmRotationAngle;
	private direction rightArmRotationDirection;
	private Bitmap rotatableTailBitmap;
	private int tailRotationAngle;
	private direction tailRotationDirection;
	private enum direction {backwards,
			forwards};
	private int ArmLinearToAngle[]={320, 321, 322, 323, 324, 325, 326, 327, 328, 329,
									330, 331, 332, 333, 334, 335, 336, 337, 338, 339,
									340, 341, 342, 343, 344, 345, 346, 347, 348, 349,
									350, 351, 352, 353, 354, 355, 356, 357, 358, 359,
									0,   1,   2,   3,   4,   5,   6,   7,   8,   9,
									10,  11,  12,  13,  14,  15,  16,  17,  18,  19,
									20,  21,  22,  23,  24,  25,  26,  27,  28,  29,
									30,  31,  32,  33,  34,  35,  36,  37,  38,  39,
									40,  41,  42,  43,  44,  45,  46,  47,  48,  49};		
	
	
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
		this.assemblyX = assemblyX;
		this.assemblyY = assemblyY;
		rotatableRightArmBitmap = rightArmBitmap;
		rightArmRotationAngle=0;
		rightArmRotationDirection=direction.backwards;
		rotatableTailBitmap=tailBitmap;
		tailRotationAngle=0;
		tailRotationDirection=direction.backwards;
	} 
	
	private Bitmap rotateBitmap(Bitmap bitmap, float angle) {
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		//        R.drawable.original);
		    Bitmap rotateBitmap = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(rotateBitmap);
		    Matrix matrix = new Matrix();
		    matrix.postRotate(angle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		    canvas.drawBitmap(bitmap, matrix, null);
		    return rotateBitmap;
		}
	
	public void waveArm()
	{
		if(rightArmRotationDirection==direction.forwards)
		{
			rightArmRotationAngle+=5;
			if(rightArmRotationAngle>=360)
			{rightArmRotationAngle=0;}
			if(rightArmRotationAngle==40)
			{
				rightArmRotationDirection=direction.backwards;
			}
		}
		else
		{
			rightArmRotationAngle-=5;
			if(rightArmRotationAngle<=0)
			{rightArmRotationAngle=360;}
			if(rightArmRotationAngle==320)
			{
				rightArmRotationDirection=direction.forwards;
			}
		}
		
		rotatableRightArmBitmap=rotateBitmap(bmpRightArm, rightArmRotationAngle);
		
	}
	
	
	
	public void setThrowArmPos(int linear)
	{
		if(linear<0) return;
		if(linear>80) return;
		
		rotatableRightArmBitmap=rotateBitmap(bmpRightArm, ArmLinearToAngle[linear]);
	}
	
	public void wiggleTail()
	{
		if(tailRotationDirection==direction.forwards)
		{
			tailRotationAngle+=1;
			if(tailRotationAngle>=360)
			{tailRotationAngle=0;}
			if(tailRotationAngle==20)
			{
				tailRotationDirection=direction.backwards;
			}
		}
		else
		{
			tailRotationAngle-=1;
			if(tailRotationAngle<=0 || (tailRotationAngle==360))
			{tailRotationAngle=0;}
			if(tailRotationAngle==0)
			{
				tailRotationDirection=direction.forwards;
			}
		}
		
		rotatableTailBitmap=rotateBitmap(bmpTail, tailRotationAngle);
		
		
	}

	
	
	
	public int getAssemblyX(){
		return assemblyX;
	}
	
	public int getAssemblyY()
	{
		return assemblyY;
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
		 canvas.drawBitmap(this.rotatableTailBitmap, bodyX, bodyY, null);
		 canvas.drawBitmap(this.bmpBody,bodyX, bodyY, null);
		 canvas.drawBitmap(this.rotatableRightArmBitmap, bodyX, bodyY, null);
		 
		 //*/
		
	}
}
