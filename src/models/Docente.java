package models;

import java.sql.Date;

public class Docente {

	public int idDocente;
	public String nombre;
	public String primer_apellido;
	public String segundo_apellido;
	public Date fecha_nacimiento;
	public String correo_electronico;
	public String materia;
	public Long no_telefono;
	
	public Docente( int idDocente, String nombre, String primer_apellido, String segundo_apellido, Date fecha_nacimiento, 
			String correo_electronico, String materia, Long no_telefono) {
		
		this.idDocente = idDocente;
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.correo_electronico = correo_electronico;
		this.materia = materia;
		this.no_telefono = no_telefono;		
	}

	public int getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimer_apellido() {
		return primer_apellido;
	}

	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}

	public String getSegundo_apellido() {
		return segundo_apellido;
	}

	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Long getNo_telefono() {
		return no_telefono;
	}

	public void setNo_telefono(Long no_telefono) {
		this.no_telefono = no_telefono;
	}

	
}
