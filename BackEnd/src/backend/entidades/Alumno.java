/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entidades;

import java.util.Date;

/**
 *
 * @author Estudiantes
 */
public class Alumno extends Persona{
    private Carrera carrera;
    
    public Alumno(String cedula, String nombre, String telefono, String email, Date nacimiento, Carrera carrera) {
        super(cedula, nombre, telefono, email, nacimiento);
        this.carrera = carrera;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
    
    
    
}
