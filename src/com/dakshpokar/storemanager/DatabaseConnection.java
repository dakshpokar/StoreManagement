package com.dakshpokar.storemanager;

import java.sql.*;

public class DatabaseConnection {
	public static Connection conn;
	public static Statement stmt;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/users";
	static final String USER = "jusername";
	static final String PASS = "jpassword";
	
	DatabaseConnection()
	{}
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}
}
