package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Bullet extends Entity {

	private int damage = 10;
	GamePanel gp;

	private double angle = 270;

	// different bullet constructors based on you want to spawn the bullet
	public Bullet(GamePanel gp, int x, int y) {
		this.x = x;
		this.y = y;
		this.gp = gp;
		setDefaultValues();
	}
	public Bullet(GamePanel gp, int x, int y, int damage) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.gp = gp;
		setDefaultValues();
	}
	public Bullet(GamePanel gp, int x, int y, int damage, double angle) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.angle = angle;
		this.gp = gp;
		setDefaultValues();
	}

	public void setDefaultValues() {
		speed = 10;
		r = 5;
	}

	public void update() {
		// give the bullet a velocity using whatever angle is supplied. If using the default constructor, 
		// bullet will just shoot up. If using the constructor that sets the angle (or you set the angle yourself),
		// the bullet will move based on that angle given.
		double rad = Math.toRadians(angle);
		dy = (int) (Math.sin(rad) * speed);
		dx = (int) (Math.cos(rad) * speed);
		y += dy;
		x += dx;
		dx = 0;
		dy = 0;
		
		// check for collision with enemies.
		GamePanel.enemies.forEach((e) -> {
			int ex = e.getX();
			int ey = e.getY();
			int er = e.getR();

			int dx = x - ex;
			int dy = y - ey;
			double dist = (double) Math.sqrt(dx * dx + dy * dy);
			if (dist < r + er) {
				e.hit(damage);
				markedForDeletion = true;
			}

		});
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.YELLOW);
		g2.fillOval(x - r, y - r, r * 2, r * 2);

	}

	public void setDamage(int d) {
		damage = d;
	}

	public void setAngle(double a) {
		angle = a;
	}


	public void setSpeed(int a) {
		speed = a;
	}

}
