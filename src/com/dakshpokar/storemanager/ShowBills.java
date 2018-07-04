package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ShowBills {

	public JFrame frmBills;

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
		frmBills.setTitle("Bills");
		frmBills.setBounds(100, 100, 720, 450);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
