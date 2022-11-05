package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Bullet;
import entity.Enemy;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 666;
	public static final int HEIGHT = 666;
	Dimension dimension;

	Thread gameThread;
	boolean running = false;

	Graphics2D g2;

	KeyHandler keysH = new KeyHandler();
	Player player1 = new Player(this, keysH);
	int score;
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Explosion> explosions = new ArrayList<Explosion>();

	WaveManager waveM = new WaveManager(this);

	public GamePanel() {
		setFocusable(true);
		dimension = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(dimension);
		addKeyListener(keysH);
		setDoubleBuffered(true);
		startGame();
	}

	ArrayList<Font> fonts;
	String[] fontArray;

	public void startGame() {

		waveM.startWaves();
		player1.setPower(1);
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// easy game loop. This one we are using

//		while (running) {
//			update();
//			repaint();
//			try {
//				Thread.sleep(17);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}

		// hard to understand game loop. I don't even understand this one yet
		double drawInterval = 1000000000 / 60;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		// fps variables
		long timer = 0;
		int drawCount = 0;
		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;

			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				if (!keysH.isPaused()) {
					update();
					repaint();
				} else {
					repaint();
				}
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}

		// original game loop that I can teach probably. Very similar to all our other
		// timer stuff
//		double drawInterval = 1000000000 / 60;
//		double nextDrawTime = drawInterval + System.nanoTime();
//		while (running) {
//			
//			if(!keysH.isPaused()) {
//				update();
//				repaint();
//			} else {
//				repaint();
//			}
//
//
//				try {
//					double remainingTime = nextDrawTime - System.nanoTime();
//					remainingTime /= 1000000;
//					if (remainingTime < 0) {
//						remainingTime = 0;
//					}
//
//					Thread.sleep((long) remainingTime);
//					nextDrawTime += drawInterval;
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			
//		}
	}

	public void update() {

		bullets.forEach((b) -> {
			b.update();
		});
		enemies.forEach((e) -> {
			e.update();
		});
		explosions.forEach((e) -> {
			e.update();
		});
		// update the enemies. We use a traditional for loop because it is easier to do
		// stuff when enemies die.
		// There might be another way using for each/for : loops, but this one is a lot
		// more clear anyway.
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);

			// NOTE: we should probably use a getter instead. Set markedForDeletion to
			// private and use a getter instead.
			if (e.markedForDeletion) {
				// add power of enemy + 1 to player's power.
				player1.setPower(player1.getPower() + e.getPower() + 1);
				// remove enemy and add explosion
				enemies.remove(i);
				explosions.add(new Explosion(this, e.getX(), e.getY(), e.getR()));
				i--;
				// if the power level of the enemy is not the smallest possible, split enemy
				// into 2 new enemies of a power level 1 below it.
				if (e.getPower() > 0) {
					GamePanel.enemies.add(new Enemy(this, e.getX(), e.getY(), e.getPower() - 1));
					GamePanel.enemies.add(new Enemy(this, e.getX(), e.getY(), e.getPower() - 1));
				}

			}
		}
		bullets.removeIf((b) -> b.markedForDeletion);
		explosions.removeIf((b) -> b.isDead());

		// update wave manager
		waveM.update();
		// update player if alive
		if (!player1.isDead()) {
			player1.update();
			score = player1.getPower();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		Color backgroundC = new Color(50, 50, 50);

		if (!keysH.isPaused()) {
			g2.setColor(backgroundC);
			g2.fillRect(0, 0, WIDTH, HEIGHT);
		}

		// draw bullets, enemies, explosions, and player if theyre alive/not marked for
		// deletion
		bullets.forEach((b) -> {
			if (!b.markedForDeletion) {
				b.draw(g2);
			}
		});
		enemies.forEach((e) -> {
			if (!e.isDead()) {
				e.draw(g2);
			}
		});
		explosions.forEach((e) -> {
			if (!e.isDead()) {
				e.draw(g2);
			}
		});
		if (!player1.isDead()) {
			player1.draw(g2);
		} else {
			// when player dies, draw GAME OVER screen
			String s = "- G A M E   O V E R -";
			g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 28)); // <-- sets the font
			int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth(); // gets total width of text
			g2.setColor(Color.BLACK);
			g2.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2); // draw text in the middle of screen (or at least
																	// close to the middle)
			String s1 = "Highest power level: " + score;
			g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 24));
			int length1 = (int) g.getFontMetrics().getStringBounds(s1, g).getWidth(); // similar to above
			g2.drawString(s1, WIDTH / 2 - length1 / 2, HEIGHT / 2 + 40);
		}
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Malgun Gothic", Font.PLAIN, 28));

		waveM.draw(g2);

		g2.drawString("Enemies: " + enemies.size(), 10, 80);

		// if paused, draw white screen
		if (keysH.isPaused()) {
			g2.setColor(new Color(50, 50, 50, 50));
			g2.fillRect(0, 0, WIDTH, HEIGHT);
		}

		g2.dispose();
	}
}
