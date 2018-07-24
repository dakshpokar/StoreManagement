package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginForm {

	private JFrame frmLogin;
	private JTextField textField;
	private JPasswordField passwordField;
	private String username;
	private String password;
	private int priv;
	public static DatabaseConnection databaseConn;
	private static Integer user_id;
	 
	/**
	 * Launch the application.
	 */
	public static ClientDashboard clientWindow;
	/**
	 * Create the application.
	 */
	public JFrame returnFrame()
	{
		return frmLogin;
	}
	public void reset()
	{
		textField.setText("");
		passwordField.setText("");
	}
	public String getUsername()
	{
		return username;
	}
	public LoginForm() {
		initialize();
	}
	public int getpriv()
	{
		return priv;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 400, 252);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(62, 64, 88, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(62, 101, 88, 14);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(138, 54, 167, 34);
		frmLogin.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(138, 91, 167, 34);
		frmLogin.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					check();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(204, 132, 101, 40);
		frmLogin.getContentPane().add(btnLogin);
		
	}
	private void check() throws SQLException, ClassNotFoundException
	{
		username = textField.getText();
		password = passwordField.getText();
		if(username.equals("")) {
			username = "dakshpokar";
		}
		if(username.equals("a")) {
			username = "aniketkotalwar";
		}
		if(password.equals("")) {
			password = "password";
		}
		if(username.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Authorization Failure!");
		}
		else if(password.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Authorization Failure!");
		}
		else
		{
			connect();
		}
	}
	private void connect() throws SQLException, ClassNotFoundException
	{
		Connection conn = DatabaseConnection.getConnection();
		Statement stmt = conn.createStatement();
		try {
			
			System.out.println("Connecting to Database....");
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM users WHERE username = '"+username+"';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.getRow();
			rs.next();
			if(password.equals(rs.getString(3)))
			{
				priv = Integer.parseInt(rs.getString(4));
				user_id = Integer.parseInt(rs.getString(1));
				clientWindow = new ClientDashboard();
				clientWindow.frmDashboard.setVisible(true);
				frmLogin.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Authorization Failure!");
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	}
	public static Integer getUserID() {
		return user_id;
	}
}
