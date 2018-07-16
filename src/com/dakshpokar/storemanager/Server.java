package com.dakshpokar.storemanager;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server implements Runnable{
	ServerSocket ss;
	ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	public Server() throws IOException{
		
	}
	public void run() {
		try {
			ss = new ServerSocket(3160);
			while(true) {
				System.out.println("Server Started");
				System.out.println("Waiting for Clients....");
				Socket s = ss.accept();
				ServerConnection sc = new ServerConnection(s, this);
				new Thread(sc).start();
				System.out.println("Connection established: " + s.getInetAddress());
				connections.add(sc);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
