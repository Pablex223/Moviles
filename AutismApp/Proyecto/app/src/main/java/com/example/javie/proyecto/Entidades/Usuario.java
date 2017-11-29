package com.example.javie.proyecto.Entidades;

/**
 * Created by javie on 11/18/2017.
 */

public class Usuario {
    //ATRIBUTOS
    String nombre;
    String email;
    String contrasena;

    public Usuario(String nom, String ema, String cont) {
        nombre = nom;
        email = ema;
        contrasena = cont;
    }

    public Usuario() {
        nombre = "";
        email = "";
        contrasena = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
