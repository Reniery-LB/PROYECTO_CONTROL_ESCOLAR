package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionModel {

	public ConnectionModel() {
		String query = "select * from Usuario";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_ProyectoControl", "freedb_DiegoNuñez", "C6*d7eA76At?aFz");
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

}