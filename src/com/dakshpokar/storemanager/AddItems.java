package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AddItems {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	Connection conn = null;
	Statement stmt = null;
	public boolean add_modify = false;
	Integer id;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public AddItems() throws SQLException, ClassNotFoundException {
		initialize();
	}
	public AddItems(boolean val, Integer id) throws ClassNotFoundException, SQLException {
		add_modify = val;
		this.id = id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws SQLException, ClassNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 354);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblItemName = new JLabel("Item Name:");
		lblItemName.setBounds(87, 100, 222, 14);
		frame.getContentPane().add(lblItemName);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField.setBounds(204, 86, 351, 40);
		frame.getContentPane().add(textField);
		
		JLabel lblItemCategory = new JLabel("Item Category:");
		lblItemCategory.setBounds(87, 140, 222, 14);
		frame.getContentPane().add(lblItemCategory);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 18));
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(204, 126, 351, 40);
		
		conn = DatabaseConnection.getConnection();
		stmt = conn.createStatement();
		final ResultSet rs;
		String catsql;
		catsql = "select distinct item_category from itemlist";
		rs = stmt.executeQuery(catsql);
		while(rs.next())
		{
			rs.getRow();
			comboBox.addItem(rs.getString(1));
		}
		rs.close();
		frame.getContentPane().add(comboBox);
		
		JLabel lblItemPrice = new JLabel("Item Price:");
		lblItemPrice.setBounds(87, 178, 222, 14);
		frame.getContentPane().add(lblItemPrice);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField_1.setBounds(204, 164, 351, 40);
		frame.getContentPane().add(textField_1);
		
		JLabel lblItemStock = new JLabel("Item Stock:");
		lblItemStock.setBounds(87, 218, 222, 14);
		frame.getContentPane().add(lblItemStock);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 18));
		textField_2.setBounds(204, 204, 351, 40);
		frame.getContentPane().add(textField_2);
		
		JLabel lblAddItemsTo = new JLabel("Add Items to Shop:");
		lblAddItemsTo.setBounds(30, 27, 238, 14);
		frame.getContentPane().add(lblAddItemsTo);
		JButton button;
		if(add_modify == false) {
		button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item_name = textField.getText().toString();
				String item_category = comboBox.getSelectedItem().toString();
				Integer item_price = Integer.parseInt(textField_1.getText().toString());
				Integer item_stock = Integer.parseInt(textField_2.getText().toString());
				String cat = "insert into itemlist values("+"NULL,"+"\""+item_name+"\","+"\""+item_category+"\","+item_price+","+item_stock+")";
				try {
					stmt.execute(cat);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Added Successfully!");
				frame.setVisible(false);
				
			}
		});
		}
		else
		{
			button = new JButton("Modify");
			String query = "select * from itemlist where item_id = " + id;
			ResultSet rs2 = stmt.executeQuery(query);
			rs2.getRow();
			rs2.next();
			textField.setText(rs2.getString(2));
			comboBox.setSelectedItem(rs2.getString(3));
			textField_1.setText(rs2.getString(4));
			textField_2.setText(rs2.getString(5));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String item_name = textField.getText().toString();
					String item_category = comboBox.getSelectedItem().toString();
					Integer price = Integer.parseInt(textField_1.getText().toString());
					Integer stock = Integer.parseInt(textField_2.getText().toString());
				}
			});
		}
		button.setFont(new Font("Dialog", Font.PLAIN, 12));
		button.setBounds(455, 250, 100, 40);
		frame.getContentPane().add(button);
	}
}
