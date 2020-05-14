package com.accenture.spm.chat.collection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat {

	private static CopyOnWriteArrayList<Channel> all =new CopyOnWriteArrayList<Channel>();
	
	public static void main(String[] args) throws IOException {
		System.out.println("-----Server-----");
		// 1、指定端口 使用ServerSocket创建服务器
		ServerSocket server =new ServerSocket(8888);
		// 2、阻塞式等待连接 accept
		while(true) {
			Socket  client =server.accept(); 
			System.out.println("1 client connected!");
			Channel c =new Channel(client);
			all.add(c); //管理所有的成员
			new Thread(c).start();			
		}		
	}
	
	static class Channel implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private Socket  client;			
		private boolean isRunning;
		private String name;
		
		public Channel(Socket  client) {
			this.client = client;
			try {
				dis = new DataInputStream(client.getInputStream());
				dos =new DataOutputStream(client.getOutputStream());
				isRunning =true;
				//获取名称
				this.name =receive();
				//欢迎你的到来
				this.send("Welcome!!!");
				sendOthers(this.name+" Coming to JW chatroom!",true);
			} catch (IOException e) {
				System.out.println("---1------");
				release();					
			}			
		}
		
		private String receive() {
			String msg ="";
			try {
				msg =dis.readUTF();
			} catch (IOException e) {
				System.out.println("---2------");
				release();
			}
			return msg;
		}
		
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("---3------");
				release();
			}
		}
		
		private void sendOthers(String msg,boolean isSys) {
			for(Channel other: all) {
				if(other==this) { //自己
					continue;
				}
				if(!isSys) {
					other.send(this.name +"@ALL:"+msg);//群聊消息
				}else {
					other.send(msg); //系统消息
				}
			}
		}
		
		private void release() {
			this.isRunning = false;
			SxtUtils.close(dis,dos,client);
			//退出
			all.remove(this);
			sendOthers(this.name+"Left Chatroom...",true);
		}
		
		@Override
		public void run() {
			while(isRunning) {
				String msg = receive() ;
				if(!msg.equals("")) {
					//send(msg);
					sendOthers(msg,false);
				}
			}
		}
		
	}

}
