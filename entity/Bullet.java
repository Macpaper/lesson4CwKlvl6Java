package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Bullet extends Entity {

	private int damage = 10;
	GamePanel gp;

	public Bullet(GamePanel gp, int x, int y) {
		this.x = x;
		this.y = y;
		this.gp = gp;
		setDefaultValues();
	}

	public void setDefaultValues() {
		speed = 10;
		r = 5;
	}

	public void update() {
		dy = -speed;
		y += dy;
		dy = 0;
		GamePanel.enemies.forEach((e) -> {
			int ex = e.getX();
			int ey = e.getY();
			int er = e.getR();
			
			int dx = x-ex;
			int dy = y-ey;
			double dist = (double)Math.sqrt(dx*dx+dy*dy);
			if (dist < r+er) {
				e.hit(damage);
				markedForDeletion = true;
			}
			
		});
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.YELLOW);
		g2.fillOval(x, y, r, r);

	}

}
