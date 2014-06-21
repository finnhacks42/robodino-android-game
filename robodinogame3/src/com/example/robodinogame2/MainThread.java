package com.example.robodinogame2;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private static final String TAG = MainThread.class.getSimpleName();
	private static final int MAX_FPS = 50;
	private final static int MAX_FRAME_SKIPS = 5;
	private final static int FRAME_PERIOD = 1000/MAX_FPS;
	
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
		long beginTime;
		long timeDiff;
		int sleepTime = 0;
		int framesSkipped;
		
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;
					this.gamePanel.update(); // update game state
					this.gamePanel.onDraw(canvas);
					
					timeDiff = System.currentTimeMillis() - beginTime; //time for a cycle
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					if (sleepTime > 0) {
						try {Thread.sleep(sleepTime);
						} catch (InterruptedException e){}
					}
					
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
						this.gamePanel.update();
						sleepTime +=FRAME_PERIOD;
						framesSkipped++;
					}
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}			
		}		
	}

}
