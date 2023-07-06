package com.efler.gymapp.modelo;

import java.io.Serializable;

public class PagoUrl implements Serializable {
    private String url;

    public PagoUrl() {
    }

    public PagoUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
