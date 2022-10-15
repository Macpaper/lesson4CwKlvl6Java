package main;

import entity.Enemy;

public class WaveManager {

	GamePanel gp;

	public WaveManager(GamePanel gp) {
		this.gp = gp;
	}

	public void startWave() {
		int x = (int) Math.random() * GamePanel.WIDTH - 100 + 50;
		int y = (int) Math.random() * GamePanel.HEIGHT - 100 + 50;
		for (int i = 0;i < 10;i++) {
			GamePanel.enemies.add(new Enemy(gp, 50, 550));
		}
		
	}
}
