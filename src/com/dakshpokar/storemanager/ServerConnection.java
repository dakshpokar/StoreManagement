package com.dakshpokar.storemanager;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
		while(!socket.isClosed()) {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			try {
				socket.close();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Query x = null;
		try {
			x = (Query)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(x.getType() == 0) {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ResultSet rs = null;
			
			try {
				if(x.getDb() == 0) {
					rs = DatabaseConnection.getUsersStatement().executeQuery(x.getQuery());
				}
				else if(x.getDb() == 1) {
					rs = DatabaseConnection.getBillStatement().executeQuery(x.getQuery());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			SerializedVector sv = null;
			try {
				sv = new SerializedVector(ClientDashboard.getVector(rs));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				oos.writeObject(sv);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				if(x.getDb() == 0) {
					DatabaseConnection.getUsersStatement().execute(x.getQuery());
				}
				else if(x.getDb() == 1) {
					DatabaseConnection.getBillStatement().execute(x.getQuery());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
	}
}
