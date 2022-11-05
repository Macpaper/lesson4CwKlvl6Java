package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// key event listener. We could use keyAdapter instead (see Java Docs on how to implement adapter. although, this one seems simpler)
public class KeyHandler implements KeyListener {

	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;
	public boolean shooting = false;
	public boolean paused = false;

	public boolean isPaused = false;
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// pause key code
		handleKeys(e.getKeyCode(), true);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(!paused) {
				paused = true;
			} else {
				paused = false;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		handleKeys(e.getKeyCode(), false);
	}

	private void handleKeys(int code, boolean isPressed) {
		if (code == KeyEvent.VK_W) {
			up = isPressed;
		}
		if (code == KeyEvent.VK_S) {
			down = isPressed;
		}
		if (code == KeyEvent.VK_A) {
			left = isPressed;
		}
		if (code == KeyEvent.VK_D) {
			right = isPressed;
		}
		if (code == KeyEvent.VK_SPACE) {
			shooting = isPressed;
		}



	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean b) {
		paused = b;
	}

}
