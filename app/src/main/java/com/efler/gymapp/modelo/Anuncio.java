package com.efler.gymapp.modelo;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Anuncio implements Serializable {
    private int id;
    private String descripcion;
    private int activo;
    private String fecha_anuncio;
    private Usuario profesor;
    private Integer profesorId;

    public Anuncio(){}

    public Anuncio(int id, String descripcion, int activo, Date fecha_anuncio, Usuario profesor,Integer profesorId) {
        this.id = id;
        this.descripcion = descripcion;
        this.activo = activo;
        this.fecha_anuncio = fecha_anuncio.toString();
        this.profesor= profesor;
        this.profesorId= profesorId;
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

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    public Usuario getProfesor() {
        return profesor;
    }
    public String getFecha_anuncio() {
        return convertirFecha(fecha_anuncio);
    }
    public void setFecha_anuncio(String fecha_anuncio) {
        this.fecha_anuncio = fecha_anuncio;
    }

    public String convertirFecha(String fecha){
        String dia="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = dateFormat.parse(fecha);

            dia = formato.format(d);

            Log.d("salida",dia);
        } catch (
                ParseException e) {
            e.printStackTrace();
            Log.d("salida",e.getMessage());
        }
        return dia;
    }
}
