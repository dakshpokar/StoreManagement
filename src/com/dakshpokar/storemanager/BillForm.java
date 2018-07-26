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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
	private JTextField finaltotal;
	private JTextField txtTotal;
	private JTextField txtSubtotal;
	private JTextField total;
	private JTextField tax;
	private JTextField txtCgst;

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
		
		frmBill.setBounds(100, 100, 720, 450);
		frmBill.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmBill.getContentPane().setLayout(null);
		frmBill.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
				int dialogButton = 0;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Discard this Bill?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					System.out.println("Bill Deleted!");
					ClientDashboard.clientConnection.sendQuery(new Query("delete from bills where bill_id="+id, 1,1));
					ClientDashboard.clientConnection.sendQuery(new Query("drop table bill"+id,1,1));
					frmBill.dispose();
				}
				else
				{
					frmBill.setVisible(true);
				}
			}
		});
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
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTotal.setText("Total");
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBackground(Color.WHITE);
		txtTotal.setBounds(39, 346, 94, 40);
		frmBill.getContentPane().add(txtTotal);
		
		txtCgst = new JTextField();
		txtCgst.setText("Tax @ 5%");
		txtCgst.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtCgst.setEditable(false);
		txtCgst.setColumns(10);
		txtCgst.setBackground(Color.WHITE);
		txtCgst.setBounds(39, 321, 94, 25);
		frmBill.getContentPane().add(txtCgst);
		btnNewButton.setBounds(552, 58, 137, 25);
	
		frmBill.getContentPane().add(btnNewButton);
		
		JLabel lblBillNo = new JLabel("Bill No.: ");
		lblBillNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBillNo.setBounds(39, 27, 78, 20);
		frmBill.getContentPane().add(lblBillNo);
		
		JLabel billid = new JLabel((String) null);
		billid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		billid.setBounds(94, 27, 78, 20);
		
		billid.setText(Integer.toString(id));
		frmBill.getContentPane().add(billid);
		
		finaltotal = new JTextField();
		finaltotal.setText("0");
		finaltotal.setHorizontalAlignment(SwingConstants.RIGHT);
		finaltotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		finaltotal.setBackground(Color.WHITE);
		finaltotal.setEditable(false);
		finaltotal.setColumns(10);
		finaltotal.setBounds(129, 346, 390, 40);
		frmBill.getContentPane().add(finaltotal);
		
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
				if(getQuantity <= 1)
				{
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(rowID);
					table.setModel(model);
				}
				else
				{
					table.setValueAt(String.valueOf(getQuantity-1), rowID, 4);
				}
				setTotal();
			}
			
		});
		frmBill.getContentPane().add(scroll);
		btnRemoveItem.setBounds(552, 94, 137, 25);
		frmBill.getContentPane().add(btnRemoveItem);
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowID = table.getSelectedRow();
				System.out.println(rowID);
				if(rowID == -1)
				{
					JOptionPane.showMessageDialog(null, "Please select item!");
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(rowID);
				table.setModel(model);
				setTotal();
			}
		});
		btnRemoveAll.setBounds(552, 130, 137, 25);
		frmBill.getContentPane().add(btnRemoveAll);
		
		txtSubtotal = new JTextField();
		txtSubtotal.setText("Sub-Total");
		txtSubtotal.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtSubtotal.setEditable(false);
		txtSubtotal.setColumns(10);
		txtSubtotal.setBackground(Color.WHITE);
		txtSubtotal.setBounds(39, 296, 94, 25);
		frmBill.getContentPane().add(txtSubtotal);
		
		total = new JTextField();
		total.setText("0");
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setFont(new Font("Dialog", Font.PLAIN, 12));
		total.setEditable(false);
		total.setColumns(10);
		total.setBackground(Color.WHITE);
		total.setBounds(129, 296, 390, 25);
		frmBill.getContentPane().add(total);
		
		tax = new JTextField();
		tax.setText("0");
		tax.setHorizontalAlignment(SwingConstants.RIGHT);
		tax.setFont(new Font("Dialog", Font.PLAIN, 12));
		tax.setEditable(false);
		tax.setColumns(10);
		tax.setBackground(Color.WHITE);
		tax.setBounds(129, 321, 390, 25);
		frmBill.getContentPane().add(tax);
		frmBill.setVisible(true);
		
	}
	public void setTotal()
	{
		double Amount = 0;
		int i;
		int rowCount = table.getRowCount();
		for(i = 0;i<rowCount;i++)
		{
			Amount = Amount + (Integer.parseInt(table.getValueAt(i, 3)+"")*Integer.parseInt(table.getValueAt(i, 4)+""));
		}
		total.setText(String.valueOf(Amount));
		tax.setText(new DecimalFormat("##.##").format(Amount*0.05));
		Amount = (Amount + (Amount*0.05));
		finaltotal.setText(new DecimalFormat("##.##").format(Amount));
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
		table.setModel(model);
		setTotal();
	}
}
