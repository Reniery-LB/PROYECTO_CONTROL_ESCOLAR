package models;

import java.sql.Date;

public class Grupo {
	
	public int idGrupo;
	public String nombre_grupo;
	public String turno;
	public String periodo;
	public String idAsignatura;
	public String idDocente;

	
	
	public Grupo(int id, String nombre_grupo, String turno, String periodo,String idDocente, String idAsignatura ) {
		
		this.idGrupo = id;
		this.nombre_grupo = nombre_grupo;
		this.turno = turno;
		this.periodo = periodo;
		this.idAsignatura = idAsignatura;
		this.idDocente = idDocente;

		
	}

}
