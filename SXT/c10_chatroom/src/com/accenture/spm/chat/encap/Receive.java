package com.accenture.spm.chat.encap;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {

	private DataInputStream dis ;
	private Socket client;
	private boolean isRunning;
	
	public Receive(Socket client) {
		this.client = client;
		this.isRunning = true;
		try {
			dis =new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			System.out.println("====2=====");
			release();
		}
	}
	
	private String receive() {
		String msg ="";
		try {
			msg =dis.readUTF();
		} catch (IOException e) {
			System.out.println("====4====");
			release();
		}
		return msg;
	}
	
	@Override
	public void run() {		
		while(isRunning) {
			String msg =receive();
			if(!msg.equals("")) {
				System.out.println(msg);
			}
		}
	}
	
	private void release() {
		this.isRunning = false;
		SxtUtils.close(dis,client);
	}
	
}
