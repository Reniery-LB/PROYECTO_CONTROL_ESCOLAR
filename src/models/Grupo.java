package models;

import java.sql.Date;

public class Grupo {
	
	public int idGrupo;
	public String nombre_grupo;
	public String turno;
	public String periodo;
	
	
	public Grupo(int id, String nombre_grupo, String truno, String periodo) {
		
		this.idGrupo = id;
		this.nombre_grupo = nombre_grupo;
		this.turno = turno;
		this.periodo = periodo;
		
	}

}
