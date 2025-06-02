package models;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionModel {
	
	public Connection conn;

	public ConnectionModel() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp\r\n"
					+ "");
			stmt = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {}
		}
	}
	
	public Connection getConnection() {
		return conn;
		
	}

	

}