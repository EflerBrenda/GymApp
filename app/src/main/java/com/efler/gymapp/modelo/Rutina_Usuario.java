package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Rutina_Usuario implements Serializable {
    private int id;
    private int rutinaId;
    private Rutina rutina;
    private int alumnoId;
    private Usuario alumno;
    private String fecha_inicio_rutina;
    private String fecha_fin_rutina;

    public Rutina_Usuario() {
    }

    public Rutina_Usuario(int id, int rutinaId, com.efler.gymapp.modelo.Rutina rutina, int alumnoId, Usuario alumno, String fecha_inicio_rutina, String fecha_fin_rutina) {
        this.id = id;
        this.rutinaId = rutinaId;
        this.rutina = rutina;
        this.alumnoId = alumnoId;
        this.alumno = alumno;
        this.fecha_inicio_rutina = fecha_inicio_rutina;
        this.fecha_fin_rutina = fecha_fin_rutina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(int rutinaId) {
        this.rutinaId = rutinaId;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public String getFecha_inicio_rutina() {
        return fecha_inicio_rutina;
    }

    public void setFecha_inicio_rutina(String fecha_inicio_rutina) {
        this.fecha_inicio_rutina = fecha_inicio_rutina;
    }

    public String getFecha_fin_rutina() {
        return fecha_fin_rutina;
    }

    public void setFecha_fin_rutina(String fecha_fin_rutina) {
        this.fecha_fin_rutina = fecha_fin_rutina;
    }
}
