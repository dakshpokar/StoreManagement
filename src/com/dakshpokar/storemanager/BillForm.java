package com.dakshpokar.storemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;

public class BillForm {

	public JFrame frame;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillForm window = new BillForm();
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
	public BillForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 717, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable(){
			public boolean isCellEditable(int row, int column){
			    return false;
			  }
		};
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ItemID", "ItemName", "ItemCategory", "ItemPrice", "ItemQuantity"
			}
		));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(39, 25, 480, 300);
		
		JScrollPane scroll=new JScrollPane(table);
		scroll.setSize(480, 300);
		scroll.setLocation(39, 25);
		
		frame.getContentPane().add(scroll);
		
		JButton btnNewButton = new JButton("Add Item");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AdderForm adderForm = new AdderForm();
					frame.setVisible(false);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(552, 20, 117, 25);
	
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		
	}
	public void AddRow()
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = table.getRowCount();
		boolean RowAdd = true;
		String[] array = new String[5];
		array = AdderForm.array;
		for (int i = 0; i<rowCount; i++)
		{
			if(table.getValueAt(i, 0).equals(array[0]))
			{
				model.setValueAt(Integer.parseInt(table.getValueAt(i, 4).toString()) + Integer.parseInt(array[4]), i, 4);
				RowAdd = false;
				break;
			}
		}
		if(RowAdd == true)
		{
			model.addRow(array);
			RowAdd = false;
		}
		table = new JTable(model);
		
	}
}
