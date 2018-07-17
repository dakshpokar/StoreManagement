package com.dakshpokar.storemanager;

import java.sql.*;

public class DatabaseConnection {
	public static Connection conn;
	public static Statement stmt;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/users";
	private static final String USER = "jusername";
	private static final String PASS = "jpassword";
	private static final String DB_URL_BILLS = "jdbc:mysql://localhost/bills";
	public static Connection billconn;
	public static Statement billstmt;
	DatabaseConnection()
	{}
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}
	public static Statement getBillStatement() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		billconn = DriverManager.getConnection(DB_URL_BILLS, USER, PASS);
		billstmt = billconn.createStatement();
		return billstmt;
	}
}
