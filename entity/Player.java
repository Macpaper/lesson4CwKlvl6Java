package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	
	KeyHandler keys;
	
	private long bulletTimer = System.nanoTime();
	private long bulletDelay = 200;

	public Player(GamePanel gp, KeyHandler keys) {
		this.gp = gp;
		this.keys = keys;
		setDefaultValues();
	}

	public void setDefaultValues() {
		x = GamePanel.WIDTH / 2 - r;
		y = GamePanel.HEIGHT / 2 - r;
		r = 25;
		speed = 5;
	}

	public void update() {

		if (keys.shooting) {
			long elapsed = (System.nanoTime()-bulletTimer)/1000000;
			if (elapsed > bulletDelay) {
				GamePanel.bullets.add(new Bullet(gp, x+r/2, y+r/2));
				bulletTimer = System.nanoTime();
			}
		}

		if (keys.up) {
			dy = -speed;
		}
		if (keys.down) {
			dy = speed;
		}
		if (keys.left) {
			dx = -speed;
		}
		if (keys.right) {
			dx = speed;
		}
		
		if (x < 0) {
			x = 0;
		}
		if (x > GamePanel.WIDTH-r) {
			x = GamePanel.WIDTH-r;
		}
		if (y < 0) {
			y = 0;
		}
		if (y > GamePanel.HEIGHT-r) {
			y = GamePanel.HEIGHT-r;
		}
		
		x += dx;
		y += dy;
		dx = 0;
		dy = 0;
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.GREEN);
		g2.fillOval(x, y, r, r);
		g2.setColor(Color.GREEN.darker());
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x, y, r, r);
	}
}
