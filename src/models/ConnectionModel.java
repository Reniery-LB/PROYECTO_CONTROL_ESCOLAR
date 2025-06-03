

package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionModel {

    public static final String URL = "jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR";
    public static final String USER = "Reniery";
    public static final String PASSWORD = "E#uVey8R!e5&zpp";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
            return null;
        }
    }
}
