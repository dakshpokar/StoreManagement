package com.dakshpokar.storemanager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements Runnable{
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	public ClientConnection() {

	}
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 3160);
			System.out.println("Connected to Client!");
			input = new DataInputStream(System.in);
			output = new DataOutputStream(socket.getOutputStream());
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
		
		String line = "";
		
		while(!line.equals("Over")){
			try{
				line = input.readLine();
				output.writeUTF(line);
			}
			catch(IOException i){
				System.out.println(i);
			}
		}
		try
        {
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }		
	}

}
