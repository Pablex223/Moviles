/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.console.vista;

import java.util.Scanner;

/**
 *
 * @author Pablo
 */
public class Solicitar {
    
    
    
    
    
     public String solicitaUsr() {
        System.out.println("Introdusca Usuario: ");
        String pass = "";
        Scanner entradaEscaner = new Scanner(System.in);
        pass = entradaEscaner.nextLine(); 
        return pass;

    }

    public String solicitaPass() {
        System.out.println("digite la contraseña:");
        String pass = "";
        Scanner entradaEscaner = new Scanner(System.in);
        pass = entradaEscaner.nextLine(); 
        return pass;
    }
    
    
    public int leerD() {
        int datoI = 0;
        Scanner entradaEscaner = new Scanner(System.in);
        datoI = entradaEscaner.nextInt();
        return datoI;
    }
    
    
    public String SolicitaNombres() {
        System.out.println("digite el nombre de la persona:");
        String nombre = "";
        Scanner entradaEscaner = new Scanner(System.in);
        nombre = entradaEscaner.nextLine(); 
        return nombre;
    }

    public String SolicitaCedulas(String persona) {
        System.out.println("digite la cedula de" + persona + ":");
        String cedula = "";
        Scanner entradaEscaner = new Scanner(System.in);
        cedula = entradaEscaner.nextLine(); 
        return cedula;
    }

    public String SolicitaEmail() {
        System.out.println("digite el correo correspondiente:");
        String correo = "";
        Scanner entradaEscaner = new Scanner(System.in);
        correo = entradaEscaner.nextLine(); 
        return correo;
    }

    public int SolicitaTelefono() {
        System.out.println("digite el telefono de la persona:");
        int telefono = 0;
        Scanner entradaEscaner = new Scanner(System.in);
        telefono = entradaEscaner.nextInt(); 
        return telefono;
    }

    public String SolicitaFec_Nac() {
        System.out.println("digite la fecha de nacimiento del estudiante:");
        String fecNac = "";
        Scanner entradaEscaner = new Scanner(System.in);
        fecNac = entradaEscaner.nextLine(); 
        return fecNac;
    }
    
    public String SolicitaCodCarrera() {
        System.out.println("digite el codigo correrspondiente a la carrera:");
        return leerS();
    }
    
    
    public String leerS() {
        String datoS = "";
        Scanner entradaEscaner = new Scanner(System.in);
        datoS = entradaEscaner.nextLine(); //Invocamos un método sobre un objeto Scanner
        return datoS;
    }
    
    
    
}
