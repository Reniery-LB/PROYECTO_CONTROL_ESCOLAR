package models;

public class UsuarioModel {

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
	}
}
