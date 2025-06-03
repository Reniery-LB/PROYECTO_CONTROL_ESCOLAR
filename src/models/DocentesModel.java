package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocentesModel {

	public List<Docente> docentes = new ArrayList<>();


	public List<Docente> getAll() {
	    List<Docente> docentes = new ArrayList<>();
	    String query = "SELECT * FROM Docente";

	    try (
	        Connection conn = new ConnectionModel().getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery()
	    ) {
	        while (rs.next()) {
	            Integer id = rs.getInt(1);
	            String nombre = rs.getString(2);
	            String primer_apellido = rs.getString(3);
	            String segundo_apellido = rs.getString(4);
	            Date fecha = rs.getDate(5);
	            String correo = rs.getString(6);
	            String materia = rs.getString(7);
	            Long no_telefono = rs.getLong(8);

	            docentes.add(new Docente(id, nombre, primer_apellido, segundo_apellido,
	                                   fecha, correo, materia, no_telefono));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return docentes;
	}
	
	
	public static boolean remove(int id) {
	    String query = "DELETE FROM Docente WHERE idDocente = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	    	 conn = new ConnectionModel().getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	}
	
	
	public boolean insert(String apellido_paterno, String apellido_materno,
			            String nombres, Date fecha_nacimiento, String correo, String materia,
			             String no_telefono) {
			
			String query = "INSERT INTO Docente (nombres, primer_apellido, segundo_apellido, fecha_nacimiento, correo_electronico, materia, no_telefono) "
			       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			Connection conn = null;
			PreparedStatement ps = null;
			
			try {
			conn = new ConnectionModel().getConnection();
			
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, apellido_paterno);
			ps.setString(2, apellido_materno);
			ps.setString(3, nombres);
			ps.setDate(4,new java.sql.Date(fecha_nacimiento.getTime()));
			ps.setString(5, correo);
			ps.setString(6, materia);
			ps.setString(7, no_telefono);
			
			ps.executeUpdate();
			return true;
			
			} catch (Exception e) {
			e.printStackTrace();
			} finally {
			try {
			  if (ps != null) ps.close();
			  if (conn != null) conn.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}
			}
			
			return false;
			}
	
	
	public boolean update(Docente docente) {
	    String sql = "UPDATE Docente SET nombres = ?, primer_apellido = ?, segundo_apellido = ?, fecha_nacimiento = ?, correo_electronico = ?,  no_telefono = ?, materia = ? WHERE idDocente = ?";
	    
		Connection conn = null;

	    try 
	     {
	    	conn = new ConnectionModel().getConnection();		
		   	PreparedStatement stmt = conn.prepareStatement(sql);
		    	
	        stmt.setString(1, docente.getNombre());
	        stmt.setString(2, docente.getPrimer_apellido());
	        stmt.setString(3, docente.getSegundo_apellido());
	        stmt.setDate(4, new java.sql.Date(docente.getFecha_nacimiento().getTime()));
	        stmt.setString(5, docente.getCorreo_electronico());
	        stmt.setLong(6, docente.getNo_telefono());
	        stmt.setString(7, docente.getMateria());

	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	public boolean existeIdDocente(String idDocente) {
	    String query = "SELECT COUNT(*) FROM Docente WHERE idDocente = ?";
	    
		Connection conn = null;

	    try  {
	    	conn = new ConnectionModel().getConnection();
	    	PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, idDocente);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static Docente busca_docente(int idDocente) {
	    String query = "SELECT * FROM Alumno WHERE no_control = ?";
	    Docente docente = null;

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, idDocente);
	        System.out.println("Buscando alumno con no_control = " + idDocente);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            docente = new Docente(
	                rs.getInt("idDocente"),
	                rs.getString("nombres"),
	                rs.getString("primer_apellido"),
	                rs.getString("segundo_apellido"),
	                rs.getDate("fecha_nacimiento"),
	                rs.getString("correo_electronico"),
	                rs.getString("materia"),
	                rs.getLong("no_telefono")
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return docente;
	}
	



}
