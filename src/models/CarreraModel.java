package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraModel {
    private Connection connection;

    public CarreraModel(Connection connection) {
        this.connection = connection;
    }

    public List<Carrera> getAll() throws SQLException {
        List<Carrera> carreras = new ArrayList<>();
        String query = "SELECT * FROM Carrera ORDER BY nombre";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setIdCarrera(rs.getInt("idCarrera"));
                carrera.setNombre(rs.getString("nombre"));
                carreras.add(carrera);
            }
        }
        return carreras;
    }

}