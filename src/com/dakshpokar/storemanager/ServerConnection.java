package com.dakshpokar.storemanager;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServerConnection implements Runnable{
	private Socket socket = null;
	private ObjectInputStream ois =  null;
	private ObjectOutputStream oos = null;
	private Server server;
	public ServerConnection(Socket socket, Server server) throws IOException{
		this.socket = socket;
		this.server = server;
	}
	public void run() {
		while(true) {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Query x = null;
		try {
			x = (Query)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		
		try {
			rs = DatabaseConnection.getBillStatement().executeQuery(x.getQuery());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SerializedVector sv = null;
		try {
			sv = new SerializedVector(ClientDashboard.getVector(rs));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			oos.writeObject(sv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
