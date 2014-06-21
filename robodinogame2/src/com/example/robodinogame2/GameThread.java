package com.example.robodinogame2;

public class GameThread extends Thread {
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void run() {
		while (running) {
			//update game
			//render game
		}
	}

}
