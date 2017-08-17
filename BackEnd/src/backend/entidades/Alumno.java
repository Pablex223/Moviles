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
    
    public Alumno(String cedula, String nombre, String telefono, String email, Date nacimiento) {
        super(cedula, nombre, telefono, email, nacimiento);
        this.carrera = null;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void asignarCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
         return "Alumno{" + "cedula=" + cedula + ", nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + ", nacimiento=" + nacimiento + '}';
    }
    
    
    
}
