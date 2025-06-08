package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignaturasModel {
	
    
	Connection conn = new ConnectionModel().getConnection();


    
    
    public boolean insertarAsignatura(Asignatura a) throws SQLException {
        String sql = "INSERT INTO Asignatura (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getDescripcion());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Asignatura> gettAll() throws SQLException {
        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT * FROM Asignatura";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Asignatura a = new Asignatura(
                    rs.getInt("idAsignatura"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                lista.add(a);
            }
        }
        return lista;
    }

    public Asignatura obtenerAsignaturaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Asignatura WHERE idAsignatura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Asignatura(
                        rs.getInt("idAsignatura"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }

    public boolean update(Asignatura a) throws SQLException {
        String sql = "UPDATE Asignatura SET nombre = ?, descripcion = ? WHERE idAsignatura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getDescripcion());
            stmt.setInt(3, a.getIdAsignatura());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Asignatura WHERE idAsignatura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean asignarDocente(int idDocente, int idAsignatura) throws SQLException {
        String sql = "INSERT INTO Docente_has_Asignatura (Docente_idDocente, Asignatura_idAsignatura) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDocente);
            stmt.setInt(2, idAsignatura);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Integer> obtenerDocentesPorAsignatura(int idAsignatura) throws SQLException {
        List<Integer> docentes = new ArrayList<>();
        String sql = "SELECT Docente_idDocente FROM Docente_has_Asignatura WHERE Asignatura_idAsignatura = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAsignatura);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    docentes.add(rs.getInt("Docente_idDocente"));
                }
            }
        }
        return docentes;
    }
    
    public int obtenerUltimoId() throws SQLException {
        String sql = "SELECT MAX(idAsignatura) AS id FROM Asignatura";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }


}
