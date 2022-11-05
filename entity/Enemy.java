package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Enemy extends Entity {

	private int powerLevel;
	private boolean damaged = false;
	private long damagedTimer = System.nanoTime();

	Color color;

	public Enemy(GamePanel gp) {
		this.gp = gp;
	}

	public Enemy(GamePanel gp, int x, int y, int tier) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.powerLevel = tier;
		setDefaultValues();
	}

	@Override
	public void setDefaultValues() {

		color = new Color(50, 20, 30, 100);
		if (powerLevel == 0) {
			speed = 2;
			r = 8;
			health = 50;
			color = new Color(50, 20, 30, 100);
		}
		if (powerLevel == 1) {
			speed = 2;
			r = 12;
			health = 50;
			color = new Color(150, 20, 30, 100);
		}
		if (powerLevel == 2) {
			speed = 3;
			r = 16;
			health = 90;
			color = new Color(50, 120, 30, 100);
		}
		if (powerLevel == 3) {
			speed = 3;
			r = 32;
			health = 120;
			color = new Color(50, 20, 130, 100);
		}
		if (powerLevel == 4) {
			speed = 3;
			r = 64;
			health = 600;
			color = new Color(150, 120, 30, 100);
		}
		if (powerLevel == 5) {
			speed = 2;
			r = 75;
			health = 1200;
			color = new Color(150, 120, 130, 100);
		}

		// get a random number between 0-359 and set it to degrees
		double degrees = (Math.random() * 360);
		// convert degrees to radians because Java uses radians in trigonometry
		// functions
		double angle = Math.toRadians(degrees);

		// set magnitudes of x and y velocity vectors using the same angle
		// this gives us a velocity vector pointing in some random direction with the
		// same length no matter what direction it points
		// NOTE: dx and dy should probably be a double or float for less restrictive movement directions. 
		dx = (int) (Math.cos(angle) * speed);
		dy = (int) (Math.sin(angle) * speed);
	}

	@Override
	public void update() {

		if (health <= 0) {
			markedForDeletion = true;

		}
		// collision so enemy can't escape screen
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

		// damaged timer. all it does is make the enemy flash white for a split second
		// (50 ms)
		if (damaged) {
			g2.setColor(Color.WHITE);

			long diff = (System.nanoTime() - damagedTimer) / 1000000;

			if (diff > 50) {
				damagedTimer = System.nanoTime();
				damaged = false;
			}
		} else {
			g2.setColor(color);
		}
		
		// draw at x - r, y - r. Java draws circles similar to how it draws rectangles: the 0,0 coordinate is
		// the top left of the circle. The "width" and "height" of the full circle are the 3rd and 4th arguments in fillOval.
		// In other programming languages/libraries, 0,0 for a circle is in the middle.
		// We define r as the radius of our circle. We draw the circle at x - r and y - r so that x and y now represent
		// the middle of the circle. This way, circle collision works like circle collision instead of rectangle collision.
		// We also define the "width" and "height" of the circle as just r * 2, so r is still half the "width" and "height" (diameter)
		
		g2.fillOval(x - r, y - r, r * 2, r * 2);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(color.darker());
		g2.drawOval(x - r, y - r, r * 2, r * 2);
		g2.setStroke(new BasicStroke(1));

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
		damagedTimer = System.nanoTime();
		damaged = true;
		health -= damage;
	}

	public int getPower() {
		return powerLevel;
	}

	public void setPower(int p) {
		powerLevel = p;
	}
}
