package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Bullet;
import entity.Enemy;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	Dimension dimension;

	Thread gameThread;
	boolean running = false;

	KeyHandler keysH = new KeyHandler();
	Player player1 = new Player(this, keysH);

	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	WaveManager waves = new WaveManager(this);

	private long waveStartTimer;
	private long waveStartElapsed;
	private int waveNumber;
	private long waveDelay = 2000;
	private boolean waveStart;

	public GamePanel() {
		setFocusable(true);
		dimension = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(dimension);
		addKeyListener(keysH);
		setDoubleBuffered(true);
		startGame();
	}

	public void startGame() {

		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		waveStartTimer = 0;
		waveStartElapsed = 0;
		waveNumber = 0;
		waveStart = true;

		while (running) {
			update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

//		double drawInterval = 1000000000/60;
//		double nextDrawTime = drawInterval + System.nanoTime();
//		while(running) {
//			
//			update();
//			repaint();
//			
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime /= 1000000;
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long)remainingTime);
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}
	}

	public void update() {
		if (waveStartTimer == 0 && enemies.size() == 0) {
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		} else {
			waveStartElapsed = (System.nanoTime() - waveStartTimer) / 1000000;
			if (waveStartElapsed > waveDelay) {
				waveStart = true;
				waveStartTimer = 0;
				waveStartElapsed = 0;
			}
		}
		if (waveStart && enemies.size() == 0) {
			createNewEnemies();
		}

		bullets.forEach((b) -> {
			b.update();
		});
		enemies.forEach((e) -> {
			e.update();
		});
		enemies.removeIf((e) -> e.markedForDeletion);
		bullets.removeIf((b) -> b.markedForDeletion);
		player1.update();
	}

	public void createNewEnemies() {
		enemies.clear();
		Enemy e;
		if (waveNumber == 1) {
			for (int i = 0; i < 4; i++) {
				int x = (int) (Math.random() * GamePanel.WIDTH - 100 + 50);
				enemies.add(new Enemy(this, x, 250));
			}
		}
		if (waveNumber == 2) {
			for (int i = 0; i < 8; i++) {
				int x = (int) (Math.random() * GamePanel.WIDTH - 100 + 50);
				enemies.add(new Enemy(this, x, 250));
			}

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(50, 50, 50));
		g2.fillRect(0, 0, WIDTH, HEIGHT);

		bullets.forEach((b) -> {
			b.draw(g2);
		});
		enemies.forEach((e) -> {
			e.draw(g2);
		});
		player1.draw(g2);

		if (waveStartTimer != 0) {
			g2.setFont(new Font("Century Gothic", Font.PLAIN,18));
			String s = "- W A V E  " + waveNumber + "  -";
			// gets length of string in pixels
			int length = (int)g.getFontMetrics().getStringBounds(s,g2).getWidth();
			int alpha = (int)(255*Math.sin(3.14*waveStartElapsed/waveDelay));
			if (alpha > 255) alpha = 255;
			g2.setColor(new Color(255,255,255,alpha));
			g2.drawString(s, WIDTH/2-length/2,HEIGHT/2);
		}
		
		g2.dispose();
	}
}
