package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlumnoModel {

	public List<Alumno> alumnos = new ArrayList<>();
	
	public AlumnoModel() {
		
	}
	
	public List getAll() {
		
		String query = "select * from Alumno";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
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


				
				//System.out.println("empId:" + id);
				//System.out.println("firstName:" + name);
				 
				System.out.println(""); 
				
				alumnos.add(new Alumno(id,no_control,nombre,primer_apellido,segundo_apellido,fecha,correo,grado,no_telefono,carrera));
			}
			
			rs.close();
			
			return alumnos;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {}
		}
		
		return alumnos;
		
	}
	
		public boolean remove(int id) {
		
		String query = "\"DELETE FROM Alumno WHERE `Alumno`.`idAlumno` = "+ id;
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
			stmt = conn.createStatement();
			
			stmt.executeUpdate(query);
			
			return true; 
				
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {}
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
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
			
			  ps = conn.prepareStatement(query);
			
			  ps.setString(1, no_control);
			  ps.setString(2, nombres);
			  ps.setString(3, apellido_paterno);
			  ps.setString(4, apellido_materno);
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

		public boolean existeNoControl(String no_control) {
		    String query = "SELECT COUNT(*) FROM Alumno WHERE no_control = ?";
		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://pro.freedb.tech:3306/CONTROLESCOLAR", "Reniery", "E#uVey8R!e5&zpp");
		         PreparedStatement stmt = conn.prepareStatement(query)) {

		        stmt.setString(1, no_control);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return rs.getInt(1) > 0;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return false;
		}
		
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
