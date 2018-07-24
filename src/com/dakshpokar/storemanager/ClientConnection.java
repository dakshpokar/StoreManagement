package com.dakshpokar.storemanager;

import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.Vector;

public class ClientConnection implements Runnable{
	private Socket socket = null;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Query query;
	public ClientConnection() {

	}
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 3160);
			System.out.println("Connected to Server!");
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
	}
	public Vector<Vector<Object>> sendQuery(Query query) {
		this.query = query;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(query.getType() == 0) {
			try {
				return receiveVector();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public Vector<Vector<Object>> receiveVector() throws IOException, ClassNotFoundException {
		ois = new ObjectInputStream(socket.getInputStream());
		SerializedVector x = (SerializedVector)ois.readObject();
		return x.getVector();
	}
	public void close() {
		try {
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
