/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.entidades.*;
import java.util.Date;

/**
 *
 * @author luisf
 */
public class BackEnd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       TipoUsuario tipoUsuarioAlumno = new TipoUsuario("ALUMNO", 1);
       Persona alumno1 = new Alumno("116800850","Javier", "87352001", "a", new Date(1997,05,15));
       System.out.println(alumno1.toString());
       Usuario usarioAlumno1 = new Usuario(alumno1.getCedula(), "1234", tipoUsuarioAlumno, alumno1);
       System.out.println(usarioAlumno1.toString());
    }
    
}
