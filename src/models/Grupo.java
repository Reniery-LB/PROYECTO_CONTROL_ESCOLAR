package models;

import java.sql.Date;

	public class Grupo {
		public int idGrupo;
	    String nombreGrupo;
	    Turno turno;
	    public String periodo;
	    public int idAsignatura;
	    public int idDocente;
	
	    public enum Turno {
	        TM("Matutino"), TV("Vespertino");
	
	        private String descripcion;
	
	        Turno(String descripcion) {
	            this.descripcion = descripcion;
	        }
	
	        public String getDescripcion() {
	            return descripcion;
	        }
	
	        public static Turno fromString(String value) {
	            for (Turno t : Turno.values()) {
	                if (t.name().equalsIgnoreCase(value)) {
	                    return t;
	                }
	            }
	            throw new IllegalArgumentException("Turno no válido: " + value);
	        }
	    }
	    
		public Grupo(int idGrupo, String nombreGrupo, Turno turno, String periodo, 
	            int idAsignatura, int idDocente) {
	    this.idGrupo = idGrupo;
	    this.nombreGrupo = nombreGrupo;
	    this.turno = turno;
	    this.periodo = periodo;
	    this.idAsignatura = idAsignatura;
	    this.idDocente = idDocente;
	}
	
	    public int getIdGrupo() {
			return idGrupo;
		}
	
		public void setIdGrupo(int idGrupo) {
			this.idGrupo = idGrupo;
		}
	
		public String getNombreGrupo() {
			return nombreGrupo;
		}
	
		public void setNombreGrupo(String nombreGrupo) {
			this.nombreGrupo = nombreGrupo;
		}
	
		public Turno getTurno() {
			return turno;
		}
	
		public void setTurno(Turno turno) {
			this.turno = turno;
		}
	
		public String getPeriodo() {
			return periodo;
		}
	
		public void setPeriodo(String periodo) {
			this.periodo = periodo;
		}
	
		public int getIdAsignatura() {
			return idAsignatura;
		}
	
		public void setIdAsignatura(int idAsignatura) {
			this.idAsignatura = idAsignatura;
		}
	
		public int getIdDocente() {
			return idDocente;
		}
	
		public void setIdDocente(int idDocente) {
			this.idDocente = idDocente;
		}
	
		@Override
	    public String toString() {
	        return nombreGrupo + " (" + turno.getDescripcion() + ") - " + periodo;
	    }
	}
