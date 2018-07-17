package com.dakshpokar.storemanager;

import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements Runnable{
	private Socket socket = null;
	private ObjectOutputStream oos;
	private Query query;
	public ClientConnection() {

	}
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 3160);
			System.out.println("Connected to Server!");
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Connected Output stream");
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
	}
	public void sendQuery(Query query) {
		this.query = query;
		try {
			oos.writeObject(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
