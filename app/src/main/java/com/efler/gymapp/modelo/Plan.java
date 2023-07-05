package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Plan implements Serializable {
    private int id;
    private String descripcion;
    private Double precio;
    private Integer dias_mes;

    public Plan() {
    }

    public Plan(int id, String descripcion, Double precio,Integer dias_mes) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.dias_mes= dias_mes;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getDias_mes() {
        return dias_mes;
    }

    public void setDias_mes(Integer dias_mes) {
        this.dias_mes = dias_mes;
    }
}
