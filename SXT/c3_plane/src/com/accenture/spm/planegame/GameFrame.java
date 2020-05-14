package com.accenture.spm.planegame;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

public class GameFrame extends JFrame {
	
	//Image ball = GameUtil.getImage("images/ball.png");
	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");
	
	//int planeX = 250, planeY = 250;
	Plane plane = new Plane(planeImg, 250, 250);
	//Shell shell = new Shell();
	Shell[] shells = new Shell[50];
	Explode explode;
	Date startTime = new Date();
	Date endTime;
	double period;
	
	int hitTime = 0;
	
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyPressed(e);
//			System.out.println("Press: " + e.getKeyCode());
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyReleased(e);
//			System.out.println("Lift: " + e.getKeyCode());
			plane.minusDirection(e);
		}
		
	}

	public void launchFrame() {
		this.setTitle("PlaneGame by James");
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 300);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		
		new PaintThread().start();
		addKeyListener(new KeyMonitor());
		
		for(int i = 0;i < shells.length;i++) {
			shells[i] = new Shell();
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		Font f = g.getFont();
//		g.setColor(Color.CYAN);
//		
//		g.drawLine(100, 100, 300, 300);
//		g.drawRect(100, 100, 300, 300);
//		g.drawOval(100, 100, 300, 300);
//		g.fillRect(100,100, 20, 20);
//		g.setColor(Color.red);
//		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
//		g.drawString("James", 200, 200);
		
		g.drawImage(bg, 0, 0, null);
//		g.drawImage(planeImg, planeX, planeY, null);
//		planeX++;
		plane.drawSelf(g);
		
		//shell.draw(g);
		
		for(int i = 0;i < shells.length;i++) {
			shells[i].draw(g);
			
			boolean hit = shells[i].getRect().intersects(plane.getRect());
			
			if(hit) {
				System.out.println("Hit!!!!" + hitTime);
				plane.live = false;
				
				if(explode == null) {
					explode = new Explode(plane.x, plane.y);
					endTime = new Date();
					period = (double)((endTime.getTime() - startTime.getTime())/1000);
				}
				
				explode.draw(g);
				hitTime++;
			}
			if(!plane.live) {
				g.setFont(new Font("Times New Roman", Font.BOLD, 20));
				g.setColor(Color.red);
				g.drawString("Time:" + period + "s", (int)plane.x, (int)plane.y);
			}
			
		}
		
		g.setColor(c);
		g.setFont(f);
	}
	
	class PaintThread extends Thread {
		
		@Override
		public void run() {
			while(true) {
				//System.out.println("Window Run!");
				repaint();
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		
		GameFrame gf = new GameFrame();
		gf.launchFrame();
		
	}
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
	
}
