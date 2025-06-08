package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GruposModel {
    private Connection connection;

    public GruposModel(Connection connection) {
        this.connection = connection;
    }

    Connection conn = new ConnectionModel().getConnection();

    // CREATE
    public boolean create(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO Grupo (nombre_grupo, turno, periodo, Asignatura_idAsignatura, Docente_idDocente) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.setString(2, grupo.getTurno().name());
            stmt.setString(3, grupo.getPeriodo());
            stmt.setInt(4, grupo.getIdAsignatura());
            stmt.setInt(5, grupo.getIdDocente());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        grupo.setIdGrupo(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    // READ ALL
    public List<Grupo> getAll() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM Grupo";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                grupos.add(new Grupo(
                    rs.getInt("idGrupo"),
                    rs.getString("nombre_grupo"),
                    Grupo.Turno.fromString(rs.getString("turno")),
                    rs.getString("periodo"),
                    rs.getInt("Asignatura_idAsignatura"),
                    rs.getInt("Docente_idDocente")
                ));
            }
        }
        return grupos;
    }

    // READ BY ID
    public Grupo getGrupoById(int id) throws SQLException {
        String sql = "SELECT * FROM Grupo WHERE idGrupo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Grupo(
                        rs.getInt("idGrupo"),
                        rs.getString("nombre_grupo"),
                        Grupo.Turno.fromString(rs.getString("turno")),
                        rs.getString("periodo"),
                        rs.getInt("Asignatura_idAsignatura"),
                        rs.getInt("Docente_idDocente")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public boolean update(Grupo grupo) throws SQLException {
        String sql = "UPDATE Grupo SET nombre_grupo = ?, turno = ?, periodo = ?, " +
                    "Asignatura_idAsignatura = ?, Docente_idDocente = ? " +
                    "WHERE idGrupo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.setString(2, grupo.getTurno().name());
            stmt.setString(3, grupo.getPeriodo());
            stmt.setInt(4, grupo.getIdAsignatura());
            stmt.setInt(5, grupo.getIdDocente());
            stmt.setInt(6, grupo.getIdGrupo());

            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean delete(int idGrupo) throws SQLException {
        // Primero eliminar relaciones con alumnos
        String deleteRelaciones = "DELETE FROM Alumno_has_Grupo WHERE Grupo_idGrupo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteRelaciones)) {
            stmt.setInt(1, idGrupo);
            stmt.executeUpdate();
        }

        // Luego eliminar el grupo
        String sql = "DELETE FROM Grupo WHERE idGrupo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            return stmt.executeUpdate() > 0;
        }
    }

    // Verificar si existe una asignatura
    public boolean existeAsignatura(int idAsignatura) throws SQLException {
        String sql = "SELECT 1 FROM Asignatura WHERE idAsignatura = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAsignatura);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Verificar si existe un docente
    public boolean existeDocente(int idDocente) throws SQLException {
        String sql = "SELECT 1 FROM Docente WHERE idDocente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDocente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Obtener alumnos en un grupo
    public List<Integer> getAlumnosEnGrupo(int idGrupo) throws SQLException {
        List<Integer> alumnos = new ArrayList<>();
        String sql = "SELECT Alumno_idAlumno FROM Alumno_has_Grupo WHERE Grupo_idGrupo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alumnos.add(rs.getInt("Alumno_idAlumno"));
                }
            }
        }
        return alumnos;
    }

    // Agregar alumno a grupo
    public boolean agregarAlumnoAGrupo(int idAlumno, int idGrupo) throws SQLException {
        String sql = "INSERT INTO Alumno_has_Grupo (Alumno_idAlumno, Grupo_idGrupo) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idGrupo);
            return stmt.executeUpdate() > 0;
        }
    }

    // Remover alumno de grupo
    public boolean removerAlumnoDeGrupo(int idAlumno, int idGrupo) throws SQLException {
        String sql = "DELETE FROM Alumno_has_Grupo WHERE Alumno_idAlumno = ? AND Grupo_idGrupo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idGrupo);
            return stmt.executeUpdate() > 0;
        }
    }
}
