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
	
	private int powerLevel = 1;

	private boolean dead = false;
	
	// ssj = Super Saiyan. Silly named variables used for cool shooting effects
	int ssjAngle = 0;
	long ssjTimer = System.nanoTime();
	
	long ssj2Timer = System.nanoTime();
	
	private long hitTimer = System.nanoTime();
	
	
	public Player(GamePanel gp, KeyHandler keys) {
		this.gp = gp;
		this.keys = keys;
		setDefaultValues();
	}

	public void setDefaultValues() {
		// set player to the middle of the screen at start
		x = GamePanel.WIDTH / 2 - r;
		y = GamePanel.HEIGHT / 2 - r;
		r = 10;
		speed = 5;
	}

	public void update() {
		if(health <= 0) {
			dead = true;
		}
		if (keys.shooting) {
			long elapsed = (System.nanoTime()-bulletTimer)/1000000;
			if (elapsed > bulletDelay) { 
				if (powerLevel >= 1 && powerLevel < 20) {
					GamePanel.bullets.add(new Bullet(gp,x,y));
				} else if (powerLevel >= 20 && powerLevel < 100) {
					Bullet b1 = new Bullet(gp,x-10,y);
					Bullet b2 = new Bullet(gp,x+10,y);
					b1.setDamage(30);
					b2.setDamage(30);
					GamePanel.bullets.add(b1);
					GamePanel.bullets.add(b2);
				} else if (powerLevel >= 100 && powerLevel < 500) {
					Bullet b1 = new Bullet(gp,x-10,y);
					Bullet b2 = new Bullet(gp,x,y);
					Bullet b3 = new Bullet(gp,x+10,y);
					b1.setAngle(260);
					b2.setAngle(270);
					b3.setAngle(280);
					b1.setDamage(50);
					b2.setDamage(50);
					b3.setDamage(50);
					GamePanel.bullets.add(b1);
					GamePanel.bullets.add(b2);
					GamePanel.bullets.add(b3);
				} else if (powerLevel >= 500 && powerLevel < 1000) {
					// it may have been better to just make another bullet constructor and do the rest of this in a for loop.
					// because this looks kind of silly
					Bullet b1 = new Bullet(gp,x-10,y);
					Bullet b2 = new Bullet(gp,x,y);
					Bullet b3 = new Bullet(gp,x+10,y);
					Bullet b4 = new Bullet(gp,x-15,y);
					Bullet b5 = new Bullet(gp,x+15,y);
					b1.setAngle(260);
					b2.setAngle(270);
					b3.setAngle(280);
					b4.setAngle(255);
					b5.setAngle(285);
					b1.setDamage(50);
					b2.setDamage(50);
					b3.setDamage(50);
					b4.setDamage(50);
					b5.setDamage(50);
					GamePanel.bullets.add(b1);
					GamePanel.bullets.add(b2);
					GamePanel.bullets.add(b3);
					GamePanel.bullets.add(b4);
					GamePanel.bullets.add(b5);
				} else if (powerLevel >= 1000) {
					Bullet b1 = new Bullet(gp,x-10,y);
					Bullet b2 = new Bullet(gp,x,y);
					Bullet b3 = new Bullet(gp,x+10,y);
					Bullet b4 = new Bullet(gp,x-15,y);
					Bullet b5 = new Bullet(gp,x+15,y);
					b1.setAngle(260);
					b2.setAngle(270);
					b3.setAngle(280);
					b4.setAngle(255);
					b5.setAngle(285);
					b1.setDamage(50);
					b2.setDamage(50);
					b3.setDamage(50);
					b4.setDamage(50);
					b5.setDamage(50);
					GamePanel.bullets.add(b1);
					GamePanel.bullets.add(b2);
					GamePanel.bullets.add(b3);
					GamePanel.bullets.add(b4);
					GamePanel.bullets.add(b5);
					bulletDelay = 100;
					if(powerLevel > 2000) {
						Bullet b6 = new Bullet(gp,x-10,y, 50);
						Bullet b7 = new Bullet(gp,x,y, 50);
						Bullet b8 = new Bullet(gp,x+10,y, 50);
						Bullet b9 = new Bullet(gp,x-15,y, 50);
						Bullet b10 = new Bullet(gp,x+15,y, 50);
						b6.setAngle(260+180);
						b7.setAngle(270+180);
						b8.setAngle(280+180);
						b9.setAngle(255+180);
						b10.setAngle(285+180);
						GamePanel.bullets.add(b6);
						GamePanel.bullets.add(b7);
						GamePanel.bullets.add(b8);
						GamePanel.bullets.add(b9);
						GamePanel.bullets.add(b10);
						
					}
				}
				bulletTimer = System.nanoTime();
			}
			if (powerLevel > 3000) {
				long diff = (System.nanoTime()-ssjTimer)/1000000;
				if (diff > 25) {
					Bullet b1 = new Bullet(gp,x,y);
					b1.setAngle(ssjAngle);
					b1.setDamage(50);
					GamePanel.bullets.add(b1);
					ssjAngle += 10;
					ssjTimer = System.nanoTime();
				}
			}
			if (powerLevel > 5000) {
				long diff = (System.nanoTime()-ssj2Timer)/1000000;
				if (diff > 250) {
					int n = 12;
					for(int i = 0; i < n; i++) {
						Bullet b = new Bullet(gp, x, y, 50, i * 360 / n);
						b.setSpeed(5);
						GamePanel.bullets.add(b);
					}
					ssj2Timer = System.nanoTime();
				}
			}
		}
		
		// check for collision with enemies
		for(Enemy e : GamePanel.enemies) {
			int distX = e.getX() - x;
			int distY = e.getY() - y;
			double distanceTotal = Math.sqrt(distX * distX + distY * distY);
			if (distanceTotal <= e.getR() + r) {
				long elapsed = (System.nanoTime() - hitTimer) / 1000000;
				if(elapsed > 2000) {
					health -= 10;
					hitTimer = System.nanoTime();
				}
			}
		}

		// typical movement set up.
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
		// collision checking so player doesn't go out of bounds
		if (x > GamePanel.WIDTH-r) {
			x = GamePanel.WIDTH-r;
		}
		if (y < 0) {
			y = 0;
		}
		if (y > GamePanel.HEIGHT-r) {
			y = GamePanel.HEIGHT-r;
		}
		// add dx to x and dy to y. We set dx and dy back to 0 so you don't keep moving after you let go of the keys.
		// If we didn't do this, we'd have to instead add a bunch of else statements to the if (keys.something) above 
		x += dx;
		y += dy;
		dx = 0;
		dy = 0;
	}

	public void draw(Graphics2D g2) {

		// draw at x - r, y - r. Java draws circles similar to how it draws rectangles: the 0,0 coordinate is
		// the top left of the circle. The "width" and "height" of the full circle are the 3rd and 4th arguments in fillOval.
		// In other programming languages/libraries, 0,0 for a circle is in the middle.
		// We define r as the radius of our circle. We draw the circle at x - r and y - r so that x and y now represent
		// the middle of the circle. This way, circle collision works like circle collision instead of rectangle collision.
		// We also define the "width" and "height" of the circle as just r * 2, so r is still half the "width" and "height" (diameter)
		g2.setColor(Color.GREEN);
		g2.fillOval(x-r, y-r, r*2, r*2);
		g2.setColor(Color.GREEN.darker());
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x-r, y-r, r*2, r*2);

		g2.drawString("Power: " + powerLevel, 20, 120);
		g2.drawString("Health: " + health, 20, 140);
	}
	//getters and setters
	public int getPower() {
		return powerLevel;
	}
	public void setPower(int p) {
		powerLevel = p;
	}
	public int getHealth() {
		return health;
	}
	public void setDead(boolean b) {
		dead = b;
	}
	public boolean isDead() {
		return dead;
	}
}
