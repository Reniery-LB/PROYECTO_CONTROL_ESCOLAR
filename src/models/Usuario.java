package models;

import java.sql.Date;

public class Usuario {
	
	public int id;
	public String usuario;
	public String correo;
	public String contraseña;
	
	public Usuario(int id, String usuario, String correo, String contraseña) {
		
		this.id = id;
		this.usuario = usuario;
		this.correo = correo;
		this.contraseña = contraseña;
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

}
