/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entidades;

/**
 *
 * @author Estudiantes
 */
public class Carrera {
    private String codigo;
    private String nombre;
    public Carrera() {
        codigo = "ESCINF";
        nombre = "Sistemas";
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
