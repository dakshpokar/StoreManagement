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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

public class BillForm {

	public JFrame frmBill;
	private JTable table;
	private int id;
	private JTextField total;
	private JTextField txtTotal;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public BillForm(int id) {
		this.id = id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBill = new JFrame();
		frmBill.setTitle("Bill");
		
		frmBill.setBounds(100, 100, 717, 398);
		frmBill.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBill.getContentPane().setLayout(null);
		
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
		scroll.setSize(480, 239);
		scroll.setLocation(39, 56);
		
		frmBill.getContentPane().add(scroll);
		
		JButton btnNewButton = new JButton("Add Item");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AdderForm adderForm = new AdderForm();
					frmBill.setVisible(false);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(552, 58, 117, 25);
	
		frmBill.getContentPane().add(btnNewButton);
		
		JLabel lblBillNo = new JLabel("Bill No.: ");
		lblBillNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBillNo.setBounds(39, 27, 78, 20);
		frmBill.getContentPane().add(lblBillNo);
		
		JLabel billid = new JLabel((String) null);
		billid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		billid.setBounds(94, 25, 78, 20);
		
		billid.setText(Integer.toString(id));
		frmBill.getContentPane().add(billid);
		
		total = new JTextField();
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setFont(new Font("Tahoma", Font.PLAIN, 18));
		total.setBackground(Color.WHITE);
		total.setEditable(false);
		total.setColumns(10);
		total.setBounds(129, 294, 390, 40);
		frmBill.getContentPane().add(total);
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTotal.setText("Total");
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBackground(Color.WHITE);
		txtTotal.setBounds(39, 294, 94, 40);
		frmBill.getContentPane().add(txtTotal);
		
		JButton btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rowID = table.getSelectedRow();
				System.out.println(rowID);
				if(rowID == -1)
				{
					JOptionPane.showMessageDialog(null, "Please select item!");
				}
				int getQuantity = Integer.parseInt(table.getValueAt(rowID, 4).toString());
				if(getQuantity == 0)
				{
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(rowID);
					table.setModel(model);
				}
				else
				{
					table.setValueAt(String.valueOf(getQuantity-1), rowID, 4);
				}
			}
			
		});
		btnRemoveItem.setBounds(552, 94, 117, 25);
		frmBill.getContentPane().add(btnRemoveItem);
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.setBounds(552, 130, 117, 25);
		frmBill.getContentPane().add(btnRemoveAll);
		frmBill.setVisible(true);
		
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
		int Amount = 0;
		int i;
		rowCount = table.getRowCount();
		for(i = 0;i<rowCount;i++)
		{
			Amount = Amount + (Integer.parseInt(table.getValueAt(i, 3)+"")*Integer.parseInt(table.getValueAt(i, 4)+""));
		}
		total.setText(String.valueOf(Amount));
	}
}
