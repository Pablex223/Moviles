package com.example.javie.proyecto.Entidades;

/**
 * Created by javie on 11/25/2017.
 */

public class Pictograma {
    //ATRIBUTOS
    private int respuesta;
    private int id;
    private String url;
    public Pictograma() {
        respuesta = 0;
        id = 0;
        url = "";
    }
    public Pictograma(int respuesta, int id, String url) {
        this.respuesta = respuesta;
        this.id = id;
        this.url = url;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
