/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.console.vista;

/**
 *
 * @author Pablo
 */
public class Menu {
    
    private Solicitar solicitar;
    
    public Menu(){
        solicitar= new Solicitar();
    }
    
    
     public void MenuAdministrador() {
        System.out.println("-----Menu Adminstrador-----");
        System.out.println("1--> Admisntrar profesores");
        System.out.println("2--> Admisntrar Alumnos");
        System.out.println("");
        System.out.println("3-> SALIR");
    }
     
     
       public void MenuProfesor() {
        System.out.println("-----Menu Profesor-----");
        System.out.println("1--> agregar Profesor");
        System.out.println("2--> Borrar Profesor");
        System.out.println("3--> mostrar Profesor");
        System.out.println("4--> actualizar Profesor");
        System.out.println("5-->SALIR");
    }
       
       
       public void MenuAlumno() {
        System.out.println("-----Menu Alumno-----");
        System.out.println("1--> agregar Alumno");
        System.out.println("2--> Borrar Alumno");
        System.out.println("3--> mostrar Alumno");
        System.out.println("4--> actualizar Alumno");
        System.out.println("5-->SALIR");
    }
       
       
       
       
        public int ModifAlmn() {
        System.out.println("1--Nombre");
        System.out.println("2--Contraseña");
        System.out.println("3--correo");
        System.out.println("4--telefono");
        System.out.println("5--salir");
        return solicitar.leerD();
    }
       
        public void BusqProf() {
        System.out.println("1--Cedula");
        System.out.println("2--Nombre");
        System.out.println("3--salir");
    }

    public void BusqAlmn() {
        System.out.println("1--Cedula");
        System.out.println("2--Nombre");
        System.out.println("3--Carrera");
        System.out.println("4--salir");
    }
     
     public int ModifProf() {
        System.out.println("1--Nombre");
        System.out.println("2--Contraseña");
        System.out.println("3--correo");
        System.out.println("4--telefono");
        System.out.println("5--salir");
        return solicitar.leerD();
    }
    
}
