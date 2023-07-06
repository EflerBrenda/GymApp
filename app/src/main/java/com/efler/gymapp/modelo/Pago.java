package com.efler.gymapp.modelo;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pago implements Serializable {
    private int id;
    private String descripcion;
    private String nro_Transaccion;
    private String fecha_Pago;
    private Integer usuarioId;
    private Usuario usuario;

    public Pago() {
    }

    public Pago(int id, String descripcion, String nro_Transaccion, String fecha_Pago, Integer usuarioId, Usuario usuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.nro_Transaccion = nro_Transaccion;
        this.fecha_Pago = fecha_Pago;
        this.usuarioId = usuarioId;
        this.usuario = usuario;
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

    public String getNro_Transaccion() {
        return nro_Transaccion;
    }

    public void setNro_Transaccion(String nro_Transaccion) {
        this.nro_Transaccion = nro_Transaccion;
    }

    public String getFecha_Pago() {
        return convertirFecha(fecha_Pago);
    }

    public void setFecha_Pago(String fecha_Pago) {
        this.fecha_Pago = fecha_Pago;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
