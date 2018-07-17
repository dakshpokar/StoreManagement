package com.dakshpokar.storemanager;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable{
	private Socket socket = null;
	private ObjectInputStream ois =  null;
	private Server server;
	public ServerConnection(Socket socket, Server server) throws IOException{
		this.socket = socket;
		this.server = server;
	}
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true) {
			try {
				while(ois.available() == 0) {
					try {
						Thread.sleep(1);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
