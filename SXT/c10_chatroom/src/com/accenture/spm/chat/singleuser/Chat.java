package com.accenture.spm.chat.singleuser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {

	public static void main(String[] args) throws IOException {
		System.out.println("-----Server-----");
		// 1、指定端口 使用ServerSocket创建服务器
		ServerSocket server =new ServerSocket(8888);
		// 2、阻塞式等待连接 accept
		Socket  client =server.accept(); 
		System.out.println("1 client connected!");
		
		//3、接收消息
		DataInputStream dis =new DataInputStream(client.getInputStream());
		String msg =dis.readUTF();
		//4、返回消息
		DataOutputStream dos =new DataOutputStream(client.getOutputStream());		
		dos.writeUTF(msg);
		//释放资源
		dos.flush();
		dos.close();
		dis.close();
		client.close();
	}

}
