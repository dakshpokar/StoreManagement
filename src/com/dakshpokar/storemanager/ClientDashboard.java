package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class ClientDashboard {

	public JFrame frmDashboard;
	public static BillForm billForm;
	public static ShowBills sBForm;
	public static AddItems addItemsForm;
	public static ShowItems showItemsForm;
	Connection conn=null;
	Statement stmt=null;
	public int id = 0;
	public ClientConnection clientConnection;
	Statement billstmt;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public ClientDashboard() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	public int getID()
	{
		return id;
	}
	private void initialize() throws SQLException {
		frmDashboard = new JFrame();
		frmDashboard.setTitle("Dashboard");
		if(ERP.loginForm.getpriv()>=2) {
			frmDashboard.setBounds(100, 100, 360, 380);
		}
		else {
			joinServer();
			frmDashboard.setBounds(100, 100, 360, 300);
		}
		//frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.getContentPane().setLayout(null);
		conn = DatabaseConnection.conn;
		stmt = DatabaseConnection.stmt;
		
		JButton btnNewButton = new JButton("New Bill");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

			}
		});
		btnNewButton.setBounds(58, 90, 117, 40);
		frmDashboard.getContentPane().add(btnNewButton);
		
		JButton btnShowBills = new JButton("Show Bills");
		btnShowBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sBForm = new ShowBills();
				sBForm.frmBills.setVisible(true);
				
			}
		});
		btnShowBills.setBounds(173, 90, 117, 40);
		frmDashboard.getContentPane().add(btnShowBills);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(35, 31, 78, 20);
		frmDashboard.getContentPane().add(lblWelcome);
		
		JLabel lblUsername = new JLabel("username!");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(114, 31, 107, 20);
		frmDashboard.getContentPane().add(lblUsername);
		lblUsername.setText(ERP.loginForm.getUsername());
		JButton btnAddItems = new JButton("Add Items");
		btnAddItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addItemsForm = new AddItems();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				addItemsForm.frame.setVisible(true);
			}
		});
		btnAddItems.setBounds(173, 184, 117, 40);
		
		
		JLabel lblBillRelated = new JLabel("Bill Related:");
		lblBillRelated.setBounds(58, 65, 188, 14);
		frmDashboard.getContentPane().add(lblBillRelated);
		
		JLabel lblItemRelated = new JLabel("Item Related:");
		lblItemRelated.setBounds(58, 159, 188, 14);
		frmDashboard.getContentPane().add(lblItemRelated);
		
		JButton btnShowItems = new JButton("Show Items");
		btnShowItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showItemsForm = new ShowItems();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				showItemsForm.frame.setVisible(true);
			}
		});
		btnShowItems.setBounds(58, 184, 117, 40);
		frmDashboard.getContentPane().add(btnShowItems);
		
		final JLabel lblLogout = new JLabel("Logout");
		lblLogout.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblLogout.setFont(new Font("Tahoma", Font.BOLD, 15));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				ERP.loginForm.returnFrame().setVisible(true);
				frmDashboard.setVisible(false);
				ERP.loginForm.reset();
			}
		});
		lblLogout.setForeground(SystemColor.textHighlight);
		lblLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogout.setBounds(242, 31, 75, 20);
		frmDashboard.getContentPane().add(lblLogout);
		if(ERP.loginForm.getpriv() >=2) {
		JLabel lblServerRelated = new JLabel("Server Related:");
		lblServerRelated.setBounds(58, 245, 188, 14);
		frmDashboard.getContentPane().add(lblServerRelated);
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Thread(new Server()).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStartServer.setBounds(58, 270, 117, 40);
		
		frmDashboard.getContentPane().add(btnStartServer);
		frmDashboard.getContentPane().add(btnAddItems);
		}		
	}
	private void joinServer() {
		clientConnection = new ClientConnection();
		new Thread(clientConnection).start();
	}
}

