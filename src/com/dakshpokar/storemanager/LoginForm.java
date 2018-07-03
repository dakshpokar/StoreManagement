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
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginForm {

	private JFrame frmLogin;
	private JTextField textField;
	private JPasswordField passwordField;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/users";
	static final String USER = "jusername";
	static final String PASS = "jpassword";
	private String username;
	private String password;
	private int priv;
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
				check();
			}
		});
		btnLogin.setBounds(204, 132, 101, 40);
		frmLogin.getContentPane().add(btnLogin);
	}
	private void check()
	{
		username = "dakshpokar"; //textField.getText();
		password = "password"; //passwordField.getText();
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
	private void connect()
	{
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to Database....");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
				clientWindow = new ClientDashboard();
				clientWindow.frame.setVisible(true);
				frmLogin.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Authorization Failure!");
			}
			
			/*while(rs.next()){
		         //Retrieve by column name
		         int id  = rs.getInt("id");
		         String first = rs.getString("username");
		         String last = rs.getString("password");

		         //Display values
		         System.out.print("ID: " + id);
		         System.out.print(", First: " + first);
		         System.out.println(", Last: " + last);
		      }*/
			
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

}
