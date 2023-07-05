package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Pago implements Serializable {
    private int Id;
    private String Descripcion;
    private String Nro_Transaccion;
    private String Fecha_Pago;
    private Usuario Alumno;

    public Pago() {
    }

    public Pago(int id, String descripcion, String nro_Transaccion, String fecha_Pago, Usuario alumno) {
        Id = id;
        Descripcion = descripcion;
        Nro_Transaccion = nro_Transaccion;
        Fecha_Pago = fecha_Pago;
        Alumno = alumno;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNro_Transaccion() {
        return Nro_Transaccion;
    }

    public void setNro_Transaccion(String nro_Transaccion) {
        Nro_Transaccion = nro_Transaccion;
    }

    public String getFecha_Pago() {
        return Fecha_Pago;
    }

    public void setFecha_Pago(String fecha_Pago) {
        Fecha_Pago = fecha_Pago;
    }

    public Usuario getAlumno() {
        return Alumno;
    }

    public void setAlumno(Usuario alumno) {
        Alumno = alumno;
    }
}
