package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConecctionModel {

	public ConecctionModel() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_ProyectoControl", "freedb_nunez", "v6HvxE44y8f8?Ba");
			stmt = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e){}
		}
	}
	

}
