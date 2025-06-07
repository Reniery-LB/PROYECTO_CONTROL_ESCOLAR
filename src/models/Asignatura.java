
package models;

public class Asignatura {

		
		public int idAsignatura;
		public String nombre;
		public String descripcion;
		
		
		public Asignatura(int id, String nombre, String descripcion) {
			
			this.idAsignatura = id;
			this.nombre = nombre;
			this.descripcion = descripcion;
			
		}
		
		 public Asignatura(String nombre, String descripcion) {
		        this.nombre = nombre;
		        this.descripcion = descripcion;
		    }
		 
		    public int getIdAsignatura() {
		        return idAsignatura;
		    }

		    public void setIdAsignatura(int idAsignatura) {
		        this.idAsignatura = idAsignatura;
		    }

		    public String getNombre() {
		        return nombre;
		    }

		    public void setNombre(String nombre) {
		        this.nombre = nombre;
		    }

		    public String getDescripcion() {
		        return descripcion;
		    }

		    public void setDescripcion(String descripcion) {
		        this.descripcion = descripcion;
		    }


}
