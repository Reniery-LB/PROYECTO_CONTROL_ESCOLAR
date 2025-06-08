package models;

public class Grupo {
    private int idGrupo;
    private String nombreGrupo;
    private Turno turno;
    private String periodo;
    private int idAsignatura;
    private int idDocente;

    public enum Turno {
        TM("Matutino"), 
        TV("Vespertino");

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
    public Grupo() {
    }

    // Getters y Setters
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