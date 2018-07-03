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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class ClientDashboard {

	public JFrame frame;
	public static BillForm billForm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientDashboard window = new ClientDashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientDashboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 332, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New Bill");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				billForm = new BillForm();
				frame.setVisible(false);
				
			}
		});
		btnNewButton.setBounds(35, 93, 117, 40);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnShowBills = new JButton("Show Bills");
		btnShowBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnShowBills.setBounds(150, 93, 117, 40);
		frame.getContentPane().add(btnShowBills);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(35, 31, 78, 20);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblUsername = new JLabel("username!");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(104, 31, 78, 20);
		frame.getContentPane().add(lblUsername);
		lblUsername.setText(ERP.loginForm.getUsername());
		JButton btnAddItems = new JButton("Add Items");
		btnAddItems.setBounds(150, 165, 117, 40);
		frame.getContentPane().add(btnAddItems);
		
		JLabel lblBillRelated = new JLabel("Bill Related:");
		lblBillRelated.setBounds(35, 68, 188, 14);
		frame.getContentPane().add(lblBillRelated);
		
		JLabel lblItemRelated = new JLabel("Item Related:");
		lblItemRelated.setBounds(35, 140, 188, 14);
		frame.getContentPane().add(lblItemRelated);
		
		JButton btnShowItems = new JButton("Show Items");
		btnShowItems.setBounds(35, 165, 117, 40);
		frame.getContentPane().add(btnShowItems);
		
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
				frame.setVisible(false);
				ERP.loginForm.reset();
			}
		});
		lblLogout.setForeground(SystemColor.textHighlight);
		lblLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogout.setBounds(192, 31, 75, 20);
		frame.getContentPane().add(lblLogout);
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
