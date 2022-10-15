package entity;

import java.awt.Graphics2D;

import main.GamePanel;

public abstract class Entity {

	protected int x = GamePanel.WIDTH/2;
	protected int y = GamePanel.HEIGHT/2;
	protected int dx = 0;
	protected int dy = 0;
	protected int speed = 5;
	protected int r = 5;
	protected int health = 100;
	public boolean markedForDeletion = false;
	
	GamePanel gp;

	
	public Entity() {
	}
	public abstract void setDefaultValues();
	public abstract void update();
	public abstract void draw(Graphics2D g2);
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getR() {
		return r;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
}
