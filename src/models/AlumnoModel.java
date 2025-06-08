package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlumnoModel {

	public List<Alumno> alumnos = new ArrayList<>();
	
	public AlumnoModel() {
		
		
	}
	
	
	public List<Alumno> getAll() {
	    List<Alumno> alumnos = new ArrayList<>();
	    String query = "SELECT * FROM Alumno";
	    
	    

	    try (
	        Connection conn = new ConnectionModel().getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery()
	    ) {
	        while (rs.next()) {
	            Integer id = rs.getInt(1);
	            Integer no_control = rs.getInt(2);
	            String nombre = rs.getString(3);
	            String primer_apellido = rs.getString(4);
	            String segundo_apellido = rs.getString(5);
	            Date fecha = rs.getDate(6);
	            String correo = rs.getString(7);
	            String grado = rs.getString(8);
	            Long no_telefono = rs.getLong(9);
	            String carrera = rs.getString(10);

	            alumnos.add(new Alumno(id, no_control, nombre, primer_apellido, segundo_apellido,
	                                   fecha, correo, grado, no_telefono, carrera));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return alumnos;
	}
	
	public static boolean remove(int id) {
	    String query = "DELETE FROM Alumno WHERE idAlumno = ?";
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

					
		public boolean insert(String no_control, String apellido_paterno, String apellido_materno,
			                String nombres, Date fecha_nacimiento, String correo, String carrera,
			                String grado, String no_telefono) {
			
			String query = "INSERT INTO Alumno (no_control, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, correo_electronico, grado_alumno, no_telefono, carrera) "
			           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			Connection conn = null;
			PreparedStatement ps = null;
			
			try {
		    conn = new ConnectionModel().getConnection();

			
			  ps = conn.prepareStatement(query);
			
			  ps.setString(1, no_control);
			  ps.setString(2, apellido_paterno);
			  ps.setString(3, apellido_materno);
			  ps.setString(4, nombres);
			  ps.setDate(5, new java.sql.Date(fecha_nacimiento.getTime()));
			  ps.setString(6, correo);
			  ps.setString(7, grado);
			  ps.setString(8, no_telefono);
			  ps.setString(9, carrera);
			
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
		
		public boolean update(Alumno alumno) {
		    String sql = "UPDATE Alumno SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, fecha_nacimiento = ?, correo_electronico = ?, grado_alumno = ?, no_telefono = ?, carrera = ? WHERE no_control = ?";

		    try (Connection conn = new ConnectionModel().getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, alumno.getNombre());
		        stmt.setString(2, alumno.getPrimer_apellido());
		        stmt.setString(3, alumno.getSegundo_apellido());
		        stmt.setDate(4, new java.sql.Date(alumno.getFecha_nacimiento().getTime()));
		        stmt.setString(5, alumno.getCorreo_electronico());
		        stmt.setString(6, alumno.getGrado_alumno());
		        stmt.setLong(7, alumno.getNo_telefono());
		        stmt.setString(8, alumno.getCarrera());
		        stmt.setInt(9, alumno.getNo_control()); 

		        int rowsUpdated = stmt.executeUpdate();
		        return rowsUpdated > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}

		
//		public boolean update(Alumno alumno) {
//		    String sql = "UPDATE Alumno SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, fecha_nacimiento = ?, correo_electronico = ?, grado_alumno = ?, no_telefono = ?, carrera = ? WHERE idAlumno = ?";
//		    
//			Connection conn = null;
//
//		    try 
//		     {
//		    	conn = new ConnectionModel().getConnection();		
//			   	PreparedStatement stmt = conn.prepareStatement(sql);
//			    	
//		        stmt.setString(1, alumno.getNombre());
//		        stmt.setString(2, alumno.getPrimer_apellido());
//		        stmt.setString(3, alumno.getSegundo_apellido());
//		        stmt.setDate(4, new java.sql.Date(alumno.getFecha_nacimiento().getTime()));
//		        stmt.setString(5, alumno.getCorreo_electronico());
//		        stmt.setString(6, alumno.getGrado_alumno());
//		        stmt.setLong(7, alumno.getNo_telefono());
//		        stmt.setString(8, alumno.getCarrera());
//		        stmt.setInt(9, alumno.getIdAlumno());
//
//		        int rowsUpdated = stmt.executeUpdate();
//		        return rowsUpdated > 0;
//
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		        return false;
//		    }
//		}	
		
		public static Alumno busca_alumno(int noControl) {
		    String query = "SELECT * FROM Alumno WHERE no_control = ?";
		    Alumno alumno = null;

		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
		         PreparedStatement stmt = conn.prepareStatement(query)) {

		        stmt.setInt(1, noControl);
		        System.out.println("Buscando alumno con no_control = " + noControl);

		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		            alumno = new Alumno(
		                rs.getInt("idAlumno"),
		                rs.getInt("no_control"),
		                rs.getString("nombre"),
		                rs.getString("primer_apellido"),
		                rs.getString("segundo_apellido"),
		                rs.getDate("fecha_nacimiento"),
		                rs.getString("correo_electronico"),
		                rs.getString("grado_alumno"),
		                rs.getLong("no_telefono"),
		                rs.getString("carrera")
		            );
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return alumno;
		}

		
		


		

		
		


		
		

		

}
