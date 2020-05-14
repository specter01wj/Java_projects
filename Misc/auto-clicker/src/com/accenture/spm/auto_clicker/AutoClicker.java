package com.accenture.spm.auto_clicker;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AutoClicker {
	
	public static int rate = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while (rate == 0) {
	         try {
	            System.out.println("Speed of the auto-clicker (in miliseconds):");
	            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	            try {
	               rate = Integer.parseInt(in.readLine());
	               if (rate < 500) {
	                  rate = 0;
	                  System.out.println("Must be at least 500 miliseconds.");
	               }
	            } catch (NumberFormatException ex) {
	               System.out.println("Error - please try again.");
	            }
	         } catch (IOException e) {}
	      }
	      try {
	         Robot robot = new Robot();
	         while (true) {
	            try {
	               Thread.sleep(rate);
	               robot.mousePress(InputEvent.BUTTON1_MASK);
	               robot.mouseRelease(InputEvent.BUTTON1_MASK);
	            } catch (InterruptedException ex) {}
	         }
	      } catch (AWTException e) {}
	}

}
