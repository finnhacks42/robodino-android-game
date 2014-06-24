package com.example.robodinogame2.model;

import com.example.robodinogame2.MainGamePanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class PowerUp {


	private static String TAG = MainGamePanel.class.getSimpleName();
	private int type;
	private int x;
	private int y;
	private Bitmap bitmap;
	private int width;
	private int height;
	Paint yellowPaint = new Paint();
	Paint bluePaint = new Paint();  
	Paint greenPaint = new Paint();  


	public PowerUp(Bitmap bitmap, int x, int y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		yellowPaint.setColor(Color.YELLOW);
		yellowPaint.setStyle(Style.FILL_AND_STROKE);

		bluePaint.setColor(Color.BLUE);
		bluePaint.setStyle(Style.FILL_AND_STROKE);

		greenPaint.setColor(Color.GREEN);
		greenPaint.setStyle(Style.FILL_AND_STROKE);
	}






	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public void create(int x, int y,int type){
		this.x = x;
		this.y =y;
		Log.d(TAG, "powerup =   " + x + " , " + y);
		//0 = bomb 1 = spread  2 = rapid
		this.type = type; 
	}
	public void update(){
		if(x > -300){
			y+=1;
		}


	}

	public void Draw(Canvas canvas) {
		//canvas.drawBitmap(bitmap, x, y , null);

		switch (type) {
		case 0:  canvas.drawCircle(x,y, 50, yellowPaint);
				break;        
		case 1:  canvas.drawCircle(x,y, 50, bluePaint);
				break;        
		case 2:  canvas.drawCircle(x,y, 50, greenPaint);
				break;
		}

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


}
