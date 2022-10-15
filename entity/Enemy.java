package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Enemy extends Entity {

	public Enemy(GamePanel gp) {
		this.gp = gp;
	}

	public Enemy(GamePanel gp, int x, int y) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		setDefaultValues();
	}

	@Override
	public void setDefaultValues() {
		speed = 3;
		r = 20;
		dx = speed;
		dy = speed;
		health = 40;
	}

	@Override
	public void update() {
		if (health <= 0) {
			markedForDeletion = true;
		}
		if (x < 0 || x > GamePanel.WIDTH - r / 2) {
			dx = -dx;
		}
		if (y < 0 || y > GamePanel.HEIGHT - r / 2) {
			dy = -dy;
		}
		x += dx;
		y += dy;

	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.fillOval(x, y, r, r);
	}

	public Color randomColor() {
		float r = (int) Math.random() * 256;
		float g = (int) Math.random() * 256;
		float b = (int) Math.random() * 256;
		return new Color(r, g, b, 0.5f);
	}

	public boolean isDead() {
		return markedForDeletion;
	}

	public int getHealth() {
		return health;
	}

	public void hit(int damage) {
		health -= damage;
	}
}
