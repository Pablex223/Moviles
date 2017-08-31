package Modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Estudiante
 */
public class Matriculador extends Persona {

    
    public Matriculador(int telefono, String email, String nombre, String cedula,  String clave) {
        super(telefono, email, nombre, cedula, 2,clave);
    }

    public Matriculador() {
        super(2);
        }

   
    
  
}
