package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Ejercicio implements Serializable {
    private int id;
    private String descripcion ;
    private int categoriaId;
    private Categoria categoria;
    private String explicacion ;
    private int activo;

    public Ejercicio() {
    }

    public Ejercicio(int id, String descripcion, int categoriaId, Categoria categoria, String explicacion, int activo) {
        this.id = id;
        this.descripcion = descripcion;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.explicacion = explicacion;
        this.activo = activo;
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

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
