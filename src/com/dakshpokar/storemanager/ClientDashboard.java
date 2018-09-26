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
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class ClientDashboard {

	public JFrame frmDashboard;
	public static BillForm billForm;
	public static ShowBills sBForm;
	public static AddItems addItemsForm;
	public static ShowItems showItemsForm;
	Connection conn=null;
	Statement stmt=null;
	public int id = 0;
	public static ClientConnection clientConnection;
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
		frmDashboard.setForeground(Color.WHITE);
		frmDashboard.getContentPane().setBackground(Color.DARK_GRAY);
		frmDashboard.setBackground(Color.DARK_GRAY);
		frmDashboard.setTitle("Dashboard");
		frmDashboard.setUndecorated(true);
		if(ERP.loginForm.getpriv()>=2) {
			frmDashboard.setBounds(100, 100, 690, 380);
			try {
				new Thread(new Server()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			joinServer();
			frmDashboard.setBounds(100, 100, 690, 300);
		}
		//frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.getContentPane().setLayout(null);
		conn = DatabaseConnection.conn;
		stmt = DatabaseConnection.stmt;
		
		JButton btnNewButton = new JButton("New Bill");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ERP.loginForm.getpriv() < 2) {
					Vector<Vector<Object>> data = clientConnection.sendQuery(new Query("select max(bill_id) from bills", 0, 1)).getData();
					Integer id = (Integer) data.get(0).get(0);
					if(id == null) {
						id = 1;
					}
					else{
						id = (Integer)(data.get(0)).get(0) + 1;
					}
					//String create;
					//create = "create table bill"+id+"(item_id int, item_name varchar(255), item_category varchar(255), item_price bigint, item_quantity int)";
					//clientConnection.sendQuery(new Query(create, 1, 1));
					String insertTable;
					insertTable = "insert into bills values("+id+","+"'bill"+id+"', 0, 'None', "+LoginForm.getUserID()+")";
					clientConnection.sendQuery(new Query(insertTable, 1, 1));
					billForm = new BillForm(id);
					billForm.frmBill.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(58, 90, 117, 40);
		frmDashboard.getContentPane().add(btnNewButton);
		
		JButton btnShowBills = new JButton("Show Bills");
		btnShowBills.setForeground(Color.WHITE);
		btnShowBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sBForm = new ShowBills();
				sBForm.frmBills.setVisible(true);
				
			}
		});
		btnShowBills.setBounds(173, 90, 117, 40);
		frmDashboard.getContentPane().add(btnShowBills);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(35, 31, 78, 20);
		frmDashboard.getContentPane().add(lblWelcome);
		
		JLabel lblUsername = new JLabel("username!");
		lblUsername.setForeground(Color.WHITE);
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
		lblBillRelated.setForeground(Color.WHITE);
		lblBillRelated.setBounds(58, 65, 188, 14);
		frmDashboard.getContentPane().add(lblBillRelated);
		
		JLabel lblItemRelated = new JLabel("Item Related:");
		lblItemRelated.setForeground(Color.WHITE);
		lblItemRelated.setBounds(58, 159, 188, 14);
		frmDashboard.getContentPane().add(lblItemRelated);
		
		JButton btnShowItems = new JButton("Show Items");
		btnShowItems.setForeground(Color.WHITE);
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
				if(ERP.loginForm.getpriv() < 2) {
					clientConnection.close();
				}
				frmDashboard.setVisible(false);
				ERP.loginForm.reset();
			}
		});
		lblLogout.setForeground(Color.WHITE);
		lblLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogout.setBounds(242, 31, 75, 20);
		frmDashboard.getContentPane().add(lblLogout);
		
		JLabel label = new JLabel("X");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				frmDashboard.dispose();
			}
		});
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 22));
		label.setBounds(650, 0, 40, 34);
		frmDashboard.getContentPane().add(label);
		
		DraggablePanel draggablePanel = new DraggablePanel(frmDashboard);
		draggablePanel.setLayout(null);
		draggablePanel.setBackground(new Color(0, 0, 0, 0));
		draggablePanel.setBounds(0, 0, 691, 30);
		frmDashboard.getContentPane().add(draggablePanel);
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
	
	public static VectorWrapper getVector(ResultSet rs)
	        throws SQLException {
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();	    
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new VectorWrapper(data, columnNames);
	}
}

