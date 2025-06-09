package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class alumno_has_grupoModel {
    private Connection connection;

    public alumno_has_grupoModel(Connection connection) {
        this.connection = connection;
    }

    public List<Alumno> obtenerAlumnosPorGrupo(int idGrupo) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Conexión cerrada antes de consultar alumnos.");
        }

        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT a.* FROM Alumno a " +
                     "JOIN Alumno_has_Grupo ag ON a.idAlumno = ag.Alumno_idAlumno " +
                     "WHERE ag.Grupo_idGrupo = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idGrupo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                alumno.setNo_control(resultSet.getInt("no_control"));
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setPrimer_apellido(resultSet.getString("primer_apellido"));
                alumno.setSegundo_apellido(resultSet.getString("segundo_apellido"));
                alumno.setFecha_nacimiento(resultSet.getDate("fecha_nacimiento"));
                alumno.setCorreo_electronico(resultSet.getString("correo_electronico"));
                alumno.setGrado_alumno(resultSet.getString("grado_alumno"));
                alumno.setNo_telefono(resultSet.getLong("no_telefono"));
                alumno.setCarrera(resultSet.getString("carrera"));
                
                alumnos.add(alumno);
            }
        }

        return alumnos;
    }

    public boolean agregarAlumnoAGrupo(int idAlumno, int idGrupo) throws SQLException {
        String sql = "INSERT INTO Alumno_has_Grupo (Alumno_idAlumno, Grupo_idGrupo) VALUES (?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAlumno);
            statement.setInt(2, idGrupo);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminarAlumnoDeGrupo(int idAlumno, int idGrupo) throws SQLException {
        String sql = "DELETE FROM Alumno_has_Grupo WHERE Alumno_idAlumno = ? AND Grupo_idGrupo = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAlumno);
            statement.setInt(2, idGrupo);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean existeRelacion(int idAlumno, int idGrupo) throws SQLException {
        String sql = "SELECT 1 FROM Alumno_has_Grupo WHERE Alumno_idAlumno = ? AND Grupo_idGrupo = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAlumno);
            statement.setInt(2, idGrupo);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

}
