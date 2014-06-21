package com.example.robodinogame2;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private static final String TAG = MainThread.class.getSimpleName();
	private boolean running;
	private MainGamePanel gamePanel;
	private SurfaceHolder surfaceHolder;
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@SuppressLint("WrongCall")
	public void run() {
		Canvas canvas;
		Log.d(TAG,"Starting game loop");
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder) {
					//update game state
					// draw the canvas on the panel
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}			
		}		
	}

}
