package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.sql.*;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ShowItems {

	public JFrame frame;
	private JTable table;
	private JTextField textField;
	private JComboBox comboBox;
	private JLabel label_1;
	Statement stmt;
	Connection conn;
	/**
	 * Launch the application.
	 */
	protected ResultSet rs2;
	private ResultSet rs3;


	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public ShowItems() throws SQLException, ClassNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException, ClassNotFoundException{
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 391);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String sql;
		ResultSet rs;
		conn = DatabaseConnection.getConnection();
		stmt = conn.createStatement();
		sql = "select * from itemlist";
		rs = stmt.executeQuery(sql);
		
		table = new JTable(AdderForm.buildTableModel(rs)){
			public boolean isCellEditable(int row, int column){
			    return false;
			  }
			public boolean getScrollableTracksViewportWidth() {
				   return getPreferredSize().width < getParent().getWidth();
				 }
		};
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 18));
		comboBox.setEditable(true);
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(422, 48, 140, 40);
		comboBox.addItem("All");
		String catsql = "select * from itemcategory";
		rs3 = stmt.executeQuery(catsql);
		while(rs3.next())
		{
			rs3.getRow();
			comboBox.addItem(rs3.getString(1));
		}
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String text = textField.getText().toString();
				String cat = comboBox.getSelectedItem().toString();
				String s;
				if(cat.equals("All"))
				{
					s= "select * from itemlist where item_name like '" + text + "%'";
				}
				else
				{
					s = "select * from itemlist where item_name like '" + text + "%'" + " and item_category='"+cat+"'";
				}
				try {
					rs2 = stmt.executeQuery(s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					table.setModel(AdderForm.buildTableModel(rs2));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(comboBox);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(73, 127, 480, 189);
		JScrollPane scroll=new JScrollPane(table);
		scroll.setLocation(83, 125);
		scroll.setSize(480, 212);
		scroll.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		frame.getContentPane().add(scroll);
		
		JLabel label = new JLabel("List of items found: ");
		label.setBounds(83, 100, 188, 14);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			
						String cat = comboBox.getSelectedItem().toString();
						String text = textField.getText().toString();
						String s;
						if(cat.equals("All"))
						{
							s= "select * from itemlist where item_name like '" + text + "%'";
						}
						else
						{
							s = "select * from itemlist where item_name like '" + text + "%'" + " and item_category='"+cat+"'";
						}try {
							rs2 = stmt.executeQuery(s);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							table.setModel(AdderForm.buildTableModel(rs2));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
			}
		});
		textField.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField.setBounds(83, 48, 340, 40);
		frame.getContentPane().add(textField);
		
		
		label_1 = new JLabel("Enter the item to be searched:");
		label_1.setBounds(83, 24, 188, 14);
		frame.getContentPane().add(label_1);
	}
}
