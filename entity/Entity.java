package entity;

import java.awt.Graphics2D;

import main.GamePanel;


// class that every other "entity" uses. An entity has a position (x,y), a speed/velocity (dx,dy,speed), a radius (all entities in this game
// are circles), a health (although, it doesn't make much sense for a bullet to have health), a markedForDeletion boolean
// (could replace any ifDead booleans with this), a power level, functions that need to be overriden (update, draw, set default values),
// getters and setters for the position/r, and access to GamePanel.
// NOTE: this could be cleaned up better. Add getters/setters for markedForDeletion, speed, velocity, and maybe add a damage variable
// remove extra getters/setters from classes that already implement this
public abstract class Entity {
	
	protected int x = GamePanel.WIDTH/2;
	protected int y = GamePanel.HEIGHT/2;
	protected int dx = 0;
	protected int dy = 0;
	protected int speed = 5;
	protected int r = 5;
	protected int health = 50;
	protected int powerLevel = 0;
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
