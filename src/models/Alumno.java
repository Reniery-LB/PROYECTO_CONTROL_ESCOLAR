package models;

import java.sql.Date;
import java.util.List;

public class Alumno {

	
		
		public int idAlumno;
		public Long no_control;
		public String nombre;
		public String primer_apellido;
		public String segundo_apellido;
		public Date fecha_nacimiento;
		public String correo_electronico;
		public String grado_alumno;
		public Long no_telefono;
		public String carrera;


		
		public Alumno(int idAlumno, String nombre, String primer_apellido, String segundo_apellido, Date fecha_nacimiento, 
				String correo_electronico, String grado_alumno, Long no_telefono, String carrera) {
			
			this.idAlumno = idAlumno;
			this.no_control = no_control;
			this.nombre = nombre;
			this.primer_apellido = primer_apellido;
			this.segundo_apellido = segundo_apellido;
			this.fecha_nacimiento = fecha_nacimiento;
			this.correo_electronico = correo_electronico;
			this.grado_alumno = grado_alumno;
			this.no_telefono = no_telefono;
			this.carrera = carrera;



			
		}



		



		public String getCarrera() {
			return carrera;
		}







		public void setCarrera(String carrera) {
			this.carrera = carrera;
		}







		public int getIdAlumno() {
			return idAlumno;
		}



		public void setIdAlumno(int idAlumno) {
			this.idAlumno = idAlumno;
		}



		public Long getNo_control() {
			return no_control;
		}



		public void setNo_control(Long no_control) {
			this.no_control = no_control;
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



		public String getGrado_alumno() {
			return grado_alumno;
		}



		public void setGrado_alumno(String grado_alumno) {
			this.grado_alumno = grado_alumno;
		}



		public Long getNo_telefono() {
			return no_telefono;
		}



		public void setNo_telefono(Long no_telefono) {
			this.no_telefono = no_telefono;
		}



		public Object getId() {
			// TODO Auto-generated method stub
			return null;
		}



		public Object getCorreo() {
			// TODO Auto-generated method stub
			return null;
		}



		public List<Alumno> getAll() {
			// TODO Auto-generated method stub
			return null;
		}
}



	

