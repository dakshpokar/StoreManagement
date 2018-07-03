package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.javafx.collections.MappingChange.Map;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
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
	Connection conn=null;
	Statement stmt=null;
	public int id = 0;

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
		frmDashboard.setBounds(100, 100, 332, 282);
		//frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.getContentPane().setLayout(null);
		try {
			Class.forName(LoginForm.JDBC_DRIVER);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(LoginForm.DB_URL, LoginForm.USER, LoginForm.PASS);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnNewButton = new JButton("New Bill");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//Create bill in Database
				/**
				
				String rowno;
				rowno = "select * from bills";
				ResultSet rs1 = null;
				try {
					rs1 = stmt.executeQuery(rowno);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					if(rs1.last())
					{
						try {
							id = Integer.parseInt(rs1.getString(1)) + 1;
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					{
						id = 0;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					rs1.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String create;
				create = "create table bill"+id+"(item_id int, item_name varchar(255), item_category varchar(255), item_price bigint, item_quantity int)";
				try {
					stmt.execute(create);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String insertTable;
				insertTable = "insert into bills values("+id+", 'bill"+id+"', 0)";
				try {
					stmt.execute(insertTable);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				**/
				
				billForm = new BillForm(id);

			}
		});
		btnNewButton.setBounds(35, 93, 117, 40);
		frmDashboard.getContentPane().add(btnNewButton);
		
		JButton btnShowBills = new JButton("Show Bills");
		btnShowBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnShowBills.setBounds(150, 93, 117, 40);
		frmDashboard.getContentPane().add(btnShowBills);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(35, 31, 78, 20);
		frmDashboard.getContentPane().add(lblWelcome);
		
		JLabel lblUsername = new JLabel("username!");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(104, 31, 78, 20);
		frmDashboard.getContentPane().add(lblUsername);
		lblUsername.setText(ERP.loginForm.getUsername());
		JButton btnAddItems = new JButton("Add Items");
		btnAddItems.setBounds(150, 165, 117, 40);
		frmDashboard.getContentPane().add(btnAddItems);
		
		JLabel lblBillRelated = new JLabel("Bill Related:");
		lblBillRelated.setBounds(35, 68, 188, 14);
		frmDashboard.getContentPane().add(lblBillRelated);
		
		JLabel lblItemRelated = new JLabel("Item Related:");
		lblItemRelated.setBounds(35, 140, 188, 14);
		frmDashboard.getContentPane().add(lblItemRelated);
		
		JButton btnShowItems = new JButton("Show Items");
		btnShowItems.setBounds(35, 165, 117, 40);
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
		lblLogout.setBounds(192, 31, 75, 20);
		frmDashboard.getContentPane().add(lblLogout);
		if(ERP.loginForm.getpriv() < 2)
		{
			btnAddItems.setVisible(false);
		}
		else
		{
			btnAddItems.setVisible(true);
		}
		
	}
}
