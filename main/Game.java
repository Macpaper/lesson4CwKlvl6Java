package main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame f = new JFrame("completely new game");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new GamePanel());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
