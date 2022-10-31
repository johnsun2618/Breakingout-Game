package BreakingOut;

import javax.swing.JFrame;

public class MainGameClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		we need to set a JFrame so create an object
		JFrame f = new JFrame();
		f.setTitle("B R I C K  B R E A K E R");
		f.setSize(700,600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		
		
//		create an Object for gamePlayMode class
		GamePlayMode GamePlay = new GamePlayMode();
		f.add(GamePlay);

	}

}
