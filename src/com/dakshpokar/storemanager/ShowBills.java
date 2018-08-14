package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class ShowBills {

	public JFrame frmBills;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ShowBills() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBills = new JFrame();
		frmBills.getContentPane().setLayout(null);
		frmBills.setBounds(100, 100, 550, 400);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(33, 139, 480, 189);
		frmBills.getContentPane().add(scrollPane);
		
		JLabel label = new JLabel("List of items found: ");
		label.setBounds(37, 110, 188, 14);
		frmBills.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField.setBounds(37, 46, 476, 40);
		frmBills.getContentPane().add(textField);
		
		JLabel label_1 = new JLabel("Enter the item to be searched:");
		label_1.setBounds(37, 20, 188, 14);
		frmBills.getContentPane().add(label_1);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
