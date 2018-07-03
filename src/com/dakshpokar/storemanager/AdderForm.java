package com.dakshpokar.storemanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.mysql.jdbc.ResultSetMetaData;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;

public class AdderForm {

	private JFrame frame;
	private JTable table;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/users";
	static final String USER = "jusername";
	static final String PASS = "jpassword";
	ResultSet rs = null;
	ResultSet rs2 = null;
	Statement stmt = null;
	Connection conn = null;
	public static boolean trueness = false;
	public static String array[];
	public JTextField Quantity;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdderForm window = new AdderForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public AdderForm() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();	
		frame.setBounds(100, 100, 636, 455);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
				ClientDashboard.billForm.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(412, 57, 140, 40);
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to Database....");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			String sql;
			sql = "select * from itemlist";
			rs = stmt.executeQuery(sql);
			table = new JTable(buildTableModel(rs)){
				public boolean isCellEditable(int row, int column){
				    return false;
				  }
				public boolean getScrollableTracksViewportWidth() {
					   return getPreferredSize().width < getParent().getWidth();
					 }
			};
			rs.close();
			String catsql;
			catsql = "select distinct item_category from itemlist";
			ResultSet rs3;
			rs3 = stmt.executeQuery(catsql);
			comboBox.addItem("All");
			while(rs3.next())
			{
				rs3.getRow();
				comboBox.addItem(rs3.getString(1));
			}
			final JTextField txtpnEnterAKeyword = new JTextField();
			txtpnEnterAKeyword.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
									
					String text = txtpnEnterAKeyword.getText().toString();
					String s = "select * from itemlist where item_name like '" + text + "%'";
					try {
						rs2 = stmt.executeQuery(s);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						table.setModel(buildTableModel(rs2));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			txtpnEnterAKeyword.addInputMethodListener(new InputMethodListener() {
				public void caretPositionChanged(InputMethodEvent event) {
				}
				public void inputMethodTextChanged(InputMethodEvent event) {
					
				}
			});
			txtpnEnterAKeyword.setFont(new Font("Dialog", Font.PLAIN, 18));
			txtpnEnterAKeyword.setBounds(73, 57, 340, 40);
			frame.getContentPane().add(txtpnEnterAKeyword);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setBounds(73, 127, 480, 189);
			JScrollPane scroll=new JScrollPane(table);
			scroll.setLocation(73, 127);
			scroll.setSize(480, 189);
			scroll.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			
			table.addMouseListener(new MouseAdapter() {
			    public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        	ClientDashboard.billForm.frame.setVisible(true);
						frame.setVisible(false);
						array = new String[5];
						int rowID = table.getSelectedRow();
						for(int i = 0; i<4; i++)
						{
							array[i] = table.getValueAt(rowID, i).toString();
						}
						array[4] =  Quantity.getText().toString();
						ClientDashboard.billForm.AddRow();
			        }
			    }
			});
			frame.getContentPane().add(scroll, BorderLayout.CENTER);
				
			JButton button = new JButton("-");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Integer q = Integer.parseInt(Quantity.getText().toString()) - 1;
					if(q<1)
					{
						JOptionPane.showMessageDialog(null, "You cannot add 0 items!");
					}
					else {
					Quantity.setText(q.toString());
					}
				}
			});
			button.setFont(new Font("Tahoma", Font.PLAIN, 17));
			button.setBounds(73, 346, 52, 40);
			frame.getContentPane().add(button);
			
			JButton button_1 = new JButton("+");
			button_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Integer q = Integer.parseInt(Quantity.getText().toString()) + 1;
					Quantity.setText(q.toString());
				}
			});
			button_1.setBounds(206, 346, 52, 40);
			frame.getContentPane().add(button_1);
			
			JLabel lblEnterTheItem = new JLabel("Enter the item to be searched:");
			lblEnterTheItem.setBounds(73, 33, 188, 14);
			frame.getContentPane().add(lblEnterTheItem);
			
			JLabel lblListOfItems = new JLabel("List of items found: ");
			lblListOfItems.setBounds(73, 108, 188, 14);
			frame.getContentPane().add(lblListOfItems);
			
			JLabel lblSelectQuantityTo = new JLabel("Select Quantity to be added:");
			lblSelectQuantityTo.setBounds(73, 327, 188, 14);
			frame.getContentPane().add(lblSelectQuantityTo);
			
			Quantity = new JTextField();
			Quantity.setFont(new Font("Tahoma", Font.PLAIN, 17));
			Quantity.setText("1");
			Quantity.setHorizontalAlignment(SwingConstants.RIGHT);
			Quantity.setBounds(122, 346, 86, 41);
			frame.getContentPane().add(Quantity);
			Quantity.setColumns(10);
			
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					array = new String[5];
					int rowID = table.getSelectedRow();
					if(rowID == -1)
					{
						JOptionPane.showMessageDialog(null, "You cannot add 0 items!");
					}
					else {
						ClientDashboard.billForm.frame.setVisible(true);
						frame.setVisible(false);
					for(int i = 0; i<4; i++)
					{
						array[i] = table.getValueAt(rowID, i).toString();
					}
					array[4] = Quantity.getText().toString();
					ClientDashboard.billForm.AddRow();
					}
				}
			});
			btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAdd.setBounds(454, 346, 100, 40);
			frame.getContentPane().add(btnAdd);
			
			frame.getContentPane().add(comboBox);
			frame.setVisible(true);
			
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		*/
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    
	    int columnCount = metaData.getColumnCount();
	    
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    

	    return new DefaultTableModel(data, columnNames);

	}
}

