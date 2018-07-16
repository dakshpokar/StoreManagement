package com.dakshpokar.storemanager;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable{
	private Socket socket = null;
	private DataInputStream in =  null;
	private Server server;
	public ServerConnection(Socket socket, Server server) throws IOException{
		this.socket = socket;
		this.server = server;
	}
	public void run() {
				
	}
}
