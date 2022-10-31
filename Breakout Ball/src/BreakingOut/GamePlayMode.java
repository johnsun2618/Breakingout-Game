package BreakingOut;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;
//for quick import ctrl+shift+o

public class GamePlayMode extends JPanel implements ActionListener, KeyListener{

	private boolean play = false;
//	because i don't want the game to be run automatically, user should start it.
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private int playerX = 350;
	private MapGenerator map;
	
	
	public GamePlayMode() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay,this);
		timer.start();
		
		map = new MapGenerator(3,7);
		
	}
	
	public void paint(Graphics g) {
//		we use black canvas
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		
//		border
		g.setColor(Color.red);
		g.fillRect(0, 0, 692, 3); //top
		g.fillRect(0, 3, 3, 592); //left
		g.fillRect(691, 3, 3, 592); //right
		
		
//		movablePaddle
		g.setColor(Color.YELLOW);
		g.fillRect(playerX, 550, 100, 8);
		
		
		//bricks
		map.draw((Graphics2D)g);
		
		
//		ball
		g.setColor(Color.green);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		
//		score
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("serif", Font.BOLD,20));
		g.drawString("POINTS :" + score, 550, 30);
		
		
//		gameOver
		if(ballposY >= 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("BETTER LUCK NEXT TIME !!" + score, 200,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("PRESS ENTER TO RESTART" , 230,300);
			
		}
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("WIN WIN !!" + score, 200,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("PRESS ENTER TO RESTART" , 230,300);
		}
	}

	
	private void moveLeft() {
		play = true;
		playerX -= 20;
	
	}
	
	private void moveRight() {
		play = true;
		playerX += 20;
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 0)
				playerX = 0;
			else
				moveLeft();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600)
				playerX = 600;
			else
				moveRight();
		}
		
		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
			if(!play) {
				score=0;
				totalBricks=21;
				ballposX = 120;
				ballposX = 350;
				ballXdir =-1;
				ballYdir =-2;
				playerX = 320;
				map = new MapGenerator(3,7);
			}
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(play) {
			
			if(ballposX <= 0) {
				ballXdir =- ballXdir;
			}
			if(ballposX <= 670) {
				ballXdir =- ballXdir;
			}
			if(ballposY <= 0) {
				ballYdir =- ballYdir;
			}
			
			Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
			Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);
			
			
			A:for(int i=0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					
					if(map.map[i][j] > 0) {
						
						int width = map.brickWidth;
						int heigth = map.brickHeight;
						int brickXpos = 80 + j*width;
						int brickYpos = 50+i*heigth;
						
						Rectangle brickRect = new Rectangle(brickXpos, brickYpos, width, heigth);
						
						if(ballRect.intersects(brickRect)) {
							
							map.setBrick(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickXpos || ballposX+1 >= brickXpos + width) {
								ballXdir =- ballXdir;
								
							} else {
								ballYdir =- ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			
			if(ballRect.intersects(paddleRect)) {
				ballYdir =- ballYdir;
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
		}
		repaint();
	}
	
}
