package com.example.javie.proyecto.Entidades;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by javie on 11/25/2017.
 */

public class Pictograma {
    //ATRIBUTOS
    private int id;
    private String nombre;
    private Bitmap imagen;
    private Categoria categoria;
    private String respuesta;

    public Pictograma(int id, String nombre, Bitmap imagen, Categoria categoria, String respuesta) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.categoria = categoria;
        this.respuesta = respuesta;
    }
    public Pictograma() {
        this.id = 0;
        this.nombre = "";
        this.imagen = null;
        this.categoria = null;
        this.respuesta = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }


    @Override
    public String toString() {
        return "Pictograma{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen=" + imagen +
                ", categoria=" + categoria +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }

    public List<String> getRespuestasAlternas (){
        return categoria.getRespuestasAlternas(respuesta);
    }
}
