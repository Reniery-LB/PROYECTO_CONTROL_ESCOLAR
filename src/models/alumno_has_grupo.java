package models;

public class alumno_has_grupo {
    private int alumno_idAlumno;
    private int grupo_idGrupo;

    public alumno_has_grupo(int alumno_idAlumno, int grupo_idGrupo) {
        this.alumno_idAlumno = alumno_idAlumno;
        this.grupo_idGrupo = grupo_idGrupo;
    }

    public int getAlumno_idAlumno() {
        return alumno_idAlumno;
    }

    public void setAlumno_idAlumno(int alumno_idAlumno) {
        this.alumno_idAlumno = alumno_idAlumno;
    }

    public int getGrupo_idGrupo() {
        return grupo_idGrupo;
    }

    public void setGrupo_idGrupo(int grupo_idGrupo) {
        this.grupo_idGrupo = grupo_idGrupo;
    }
}
