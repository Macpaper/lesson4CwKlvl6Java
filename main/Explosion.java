package main;

import java.awt.Color;
import java.awt.Graphics2D;


// cool explosion effect when an enemy dies.
public class Explosion {

	GamePanel gp;
	private int x, y, r;
	
	private long explosionTimer;
	private boolean markedForDeletion = false;
	private int explodeSize;
	public Explosion(GamePanel gp, int x, int y, int r) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.r = r;
		// maximum size of explosion is 5 times the radius of the enemy 
		explodeSize = r * 5;
		explosionTimer = System.nanoTime();
	}

	public void update() {
		// grow the circle (explosion) radius
		r += 3;
	}
	
	public void draw(Graphics2D g2) {
		// draw expanding circle/explosion
		g2.setColor(Color.WHITE);
		g2.drawOval(x-r, y-r, r*2, r*2);
		long diff = (System.nanoTime() - explosionTimer) / 1000000;
		// once circle reaches a certain radius (explodeSize), then delete the circle
		if(r > explodeSize) {
			explosionTimer = System.nanoTime();
			markedForDeletion = true;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isDead() {
		return markedForDeletion;
	}
	public void setDead(boolean d) {
		markedForDeletion = d;
	}
}
