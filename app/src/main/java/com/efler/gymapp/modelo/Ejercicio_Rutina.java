package com.efler.gymapp.modelo;

import java.io.Serializable;

public class Ejercicio_Rutina implements Serializable {
    private int id ;
    private int rutinaId;
    private Rutina rutina;
    private int ejercicioId;
    private Ejercicio ejercicio;
    private int cantidad;
    private int repeticiones;
    private int dia;

    public Ejercicio_Rutina() {
    }

    public Ejercicio_Rutina(int id, int rutinaId, Rutina rutina, int ejercicioId, Ejercicio ejercicio, int cantidad, int repeticiones, int dia) {
        this.id = id;
        this.rutinaId = rutinaId;
        this.rutina = rutina;
        this.ejercicioId = ejercicioId;
        this.ejercicio = ejercicio;
        this.cantidad = cantidad;
        this.repeticiones = repeticiones;
        this.dia = dia;
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

    public int getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(int ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
