package com.efler.gymapp.modelo;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Asistencia implements Serializable {
    private int id;
    private String fecha_asistencia;
    private String hora_asistencia;
    private Usuario usuario;
    private Integer usuarioId;

    public Asistencia() {
    }

    public Asistencia(int id, Date fecha_asistencia, String hora_asistencia, Usuario usuario, Integer usuarioId) {
        this.id = id;
        this.fecha_asistencia = fecha_asistencia.toString();
        this.hora_asistencia = hora_asistencia;
        this.usuario = usuario;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_asistencia() {
        return convertirFecha(fecha_asistencia);
    }

    public void setFecha_asistencia(String fecha_asistencia) {
        this.fecha_asistencia = fecha_asistencia;
    }

    public String getHora_asistencia() {
        return hora_asistencia;
    }

    public void setHora_asistencia(String hora_asistencia) {
        this.hora_asistencia = hora_asistencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
