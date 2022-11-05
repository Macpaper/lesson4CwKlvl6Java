package main;

import java.awt.Graphics2D;

import entity.Enemy;

public class WaveManager {

	GamePanel gp;

	private int waveNumber;
	private long waveTimer;
	private boolean waveStart;

	public WaveManager(GamePanel gp) {
		this.gp = gp;
		startWaves();
	}

	public void startWaves() {

		waveNumber = 0;
		waveTimer = System.nanoTime();
		waveStart = true;

	}

	public void draw(Graphics2D g2) {

		g2.drawString("Wave: " + waveNumber, 10, 40);
	}

	public void update() {
		// wave timer. If 60 seconds pass, wave just skips to the next one.
		long waveDifference = (System.nanoTime() - waveTimer) / 1000000;
		if (waveDifference > 2000 && GamePanel.enemies.size() == 0 || waveDifference > 60000) {
			waveStart = true;
			waveTimer = System.nanoTime();
		}
		if (waveStart) {
			createNewEnemies(waveNumber);

			waveNumber++;
			waveStart = false;
		}
	}

	public void createNewEnemies(int waveNum) {
		GamePanel.enemies.clear();
		
		// WAVE DIFFICULTY FORMULA I CAME UP WITH its just one of literally infinite ways of coming up with a wave spawning formula.
		// You can walk through the math if you'd like
		// The gist is this number below gives the total "power" of all the enemies combined using a log function.
		// below this variable, we do binary math to "fill up" the total power generated with enemies at appropriate tiers
		// ie a generated number of 46 will spawn a single(32) power enemy, a single (8) power enemy, a (4) power, and a (2) power.
		// (32) is a tier 5, (8) is a tier 3, (4) is tier 2, (2) is tier 1 
		int enemyNumGenerated = (int) (waveNum * Math.log(waveNum * 10)) + 4;

		// binary math to dictate amount of and tiers of enemies.
		int decNum = enemyNumGenerated;
		String binString = "";
		int tierFives = 0;
		int restTiers = decNum;
		// if more than 1 tier 5 exists, start counting them
		if (decNum >= 64) {
			tierFives = decNum / 32;
			restTiers = decNum % 32;
		}
//		System.out.println("this many (more than 1) tier fives: " + tierFives);
		decNum = restTiers;

		// converts decimal number to binary
		while (decNum > 0) {
			if (decNum % 2 == 0) {
				binString = binString + "0";
			} else {
				binString = binString + "1";
			}
			decNum /= 2;
		}

//		System.out.println(binString);

		// this is a cooler way to spawn enemies. Used binary math and log formula as a difficulty system. Problem with this is that
		// only a few enemies are on screen for the first ~20 waves or so, and it isn't as fun. So I added the above.
		for (int i = 0; i < binString.length(); i++) {
			int tierNum = Character.getNumericValue(binString.charAt(i));
//			System.out.println("There are " + tierNum + " tier " + i + "'s");
			if (tierNum == 1) {
				int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
				GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, i));
			}
		}
		for (int i = 0; i < tierFives; i++) {
			int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
			GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, 5));
		}


		// this is a way easier way to just spawn a bunch of extra enemies. Instead of doing all that complicated math above,
		// you can just do something like this instead. Or come up with your own formula altogether!
		// I'm just spawning different tiers of enemies
		// based off a linear multiple of the wave number. wave 5 gives 5 * 4 tier 1 enemies, 5 * 3 tier 2, 5 * 2 tier 3, 5 tier 4.
		for (int i = 0; i < (waveNum * 4); i++) {
			int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
			GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, 1));
		}
		for (int i = 0; i < (waveNum * 3); i++) {
			int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
			GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, 2));
		}
		for (int i = 0; i < (waveNum * 2); i++) {
			int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
			GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, 3));
		}
		for (int i = 0; i < (waveNum * 1); i++) {
			int x = (int) (Math.random() * (GamePanel.WIDTH - 100) + 50);
			GamePanel.enemies.add(new Enemy(gp, x, GamePanel.HEIGHT / 8, 4));
		}
		
	}
}
