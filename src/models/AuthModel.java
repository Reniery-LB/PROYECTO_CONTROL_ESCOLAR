package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthModel {

	public boolean validarUsuario(Usuario usuario) {
		
		 String sql = "SELECT * FROM Usuario WHERE usuario = ? AND contrasena = ?";

	        try (
	            Connection conn = new ConnectionModel().getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)
	        ) {
	            stmt.setString(1, usuario.getUsuario());
	            stmt.setString(2, usuario.getContraseña());
	            ResultSet rs = stmt.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            
	            return false;
	        }	
	}
	
	public boolean registrarUsuario(String usuario, String contrasena, String correo) {
		
		 String sql = "INSERT INTO Usuario (usuario, correo, contrasena) VALUES (?, ?, ?)";

		 try (
		            Connection conn = new ConnectionModel().getConnection();
		            PreparedStatement stmt = conn.prepareStatement(sql)
		        ) {
		            stmt.setString(1, usuario);
		            stmt.setString(2, correo);
		            stmt.setString(3, contrasena);
		            
		            int rowsInserted = stmt.executeUpdate();  // CORRECTO para INSERT
		            return rowsInserted > 0;

		        } catch (SQLException e) {
		            e.printStackTrace();
		            
		            return false;
		        }	
		
		
		
	}
	
	
	
	
	

}
