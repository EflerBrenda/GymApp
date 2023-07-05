package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Rutina implements Serializable {
    private int id;
    private String descripcion;
    private int cant_dias;

    public Rutina() {
    }

    public Rutina(int id, String descripcion, int cant_dias) {
        this.id = id;
        this.descripcion = descripcion;
        this.cant_dias = cant_dias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCant_dias() {
        return cant_dias;
    }

    public void setCant_dias(int cant_dias) {
        this.cant_dias = cant_dias;
    }
}
