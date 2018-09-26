package com.dakshpokar.storemanager;

import java.awt.EventQueue;
import java.awt.Image;

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
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		frmLogin.getContentPane().setBackground(Color.DARK_GRAY);
		frmLogin.setUndecorated(true);
		frmLogin.setTitle("Login");
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setBounds(100, 100, 690, 450);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmLogin.dispose();
			}
		});
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 22));
		lblX.setForeground(Color.WHITE);
		lblX.setBounds(650, 0, 40, 34);
		frmLogin.getContentPane().add(lblX);
		
		JPanel draggablePanel = new DraggablePanel(frmLogin);
		draggablePanel.setBounds(0, 0, 691, 30);
		frmLogin.getContentPane().add(draggablePanel);
		draggablePanel.setBackground(new Color(0,0,0,0));
		
		draggablePanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(365, 166, 88, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(365, 227, 88, 14);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(SystemColor.textHighlight);
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
		btnLogin.setBounds(365, 290, 261, 40);
		frmLogin.getContentPane().add(btnLogin);
		
		JPanel panel = new DraggablePanel(frmLogin);
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 298, 450);
		frmLogin.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblImage = new JLabel("");
		lblImage.setBounds(0, 0, 298, 237);
		Image img = new ImageIcon(this.getClass().getResource("/store.jpg")).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_DEFAULT);
		lblImage.setIcon(new ImageIcon(img));
		panel.add(lblImage);
		
		JLabel lblNewLabel_2 = new JLabel("Store Management");
		lblNewLabel_2.setForeground(SystemColor.window);
		lblNewLabel_2.setFont(new Font("Haettenschweiler", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(72, 311, 155, 57);
		panel.add(lblNewLabel_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(365, 277, 261, 2);
		frmLogin.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(365, 214, 261, 2);
		frmLogin.getContentPane().add(separator_1);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setBorder(null);
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBounds(365, 241, 261, 34);
		frmLogin.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBackground(Color.DARK_GRAY);
		textField.setCaretColor(Color.WHITE);
		textField.setBorder(null);
		textField.setForeground(Color.WHITE);
		textField.setBounds(365, 180, 261, 34);
		frmLogin.getContentPane().add(textField);
		textField.setColumns(10);
		
	}
	private void check() throws SQLException, ClassNotFoundException
	{
		username = textField.getText();
		password = passwordField.getText();
		if(username.equals("")) {
			username = "dakshpokar";
		}
		if(username.equals("a")) {
			username = "anikotalwar";
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
