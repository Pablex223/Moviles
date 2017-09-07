/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.console.vista;

import java.util.ArrayList;
import java.util.ResourceBundle.Control;

/**
 *
 * @author Pablo
 */
public class Interfaz {
    
    private Control ctrl;
    private Solicitar solicitar;
    private Menu menu;
    private Mensaje mensaje;

    public Interfaz() {
        ctrl = new Control();
        solicitar= new Solicitar();
        menu= new Menu();
        mensaje= new Mensaje();
    }
    
     public void init() {
        System.out.println("Bienvenido al programa de gestion academica");
        System.out.println("---------LOGIN---------");
        String usr= solicitar.solicitaUsr();
        String pass= solicitar.solicitaPass();
        
        int tipo = ctrl.verificaUsuario(usr, pass);// control
        
        switch (tipo) {
            case 1:
                FuncAdministrativas();
                break;
            case 2:
                int opc = 0;
                while ((opc = nuevamatricula()) != 2) {
                    if (opc == 1) {
                        AdminMatricula();
                    }
                }
                break;
            case 3:
                FuncProfesor(usr);
                break;
            case 4:
                FuncAlumno();
                //
                break;
            case 0:
                break;

        }

    }
     
     
     
      public void AreaIngreso(int t) {
        switch (t) {
            case 1:
                System.out.println("Area de adinistrador");
                break;
            case 2:
                System.out.println("Area de Matriculador");
                break;
            case 3:
                System.out.println("Area de Profesor");
                break;
            case 4:
                System.out.println("Area de Alumno");
                break;
        }
    }
     
     
      /*Administrativo
      --------------------------------------------------------------------------*/
        
      
     
      public void FuncAdministrativas() {
        AreaIngreso(1);
        int opc = -1;
        while (opc != 10) {
            menu.MenuAdministrador();
            switch (opc = solicitar.leerD()) {
                case 1:
                    AdminProfes();
                    break;
                case 2:
                    AdminEst();
                    break;
                
                default:
                    mensaje.ERROR();
                    break;
            }
        }
    }
     
     
       private void AdminProfes() {
        int opc = 0;
        while (opc != 5) {
            menu.MenuProfesor();
            switch (opc = solicitar.leerD()) {
                case 1:
                    crearProfe();
                    break;
                case 2:
                    BorrarPersona();
                    break;
                case 3:
                    MostrarProfe();
                    break;
                case 4:
                    ActualizarProfe();
                    break;
                default:
                    mensaje.ERROR();
                    break;
            }
        }
    }
       
       
       
       /*               Profes
       --------------------------------------------------------------------*/
        private void MostrarProfe() {
        int p = 0;
        Profesor per = new Profesor();
        while (p != 3) {
            menu.BusqProf();
            switch (p = solicitar.leerD()) {
                case 1:
                    ctrl.mostrarPCed(per, solicitar.SolicitaCedulas("profesor"));
                    System.out.println(per.toString());
                    break;
                case 2:
                     ArrayList<Profesor> l = new ArrayList<>();
                    ctrl.mostrarProNom(SolicitaNombres(), l);
                    System.out.println(l.toString());
                    break;
                default:
                    mensaje.ERROR();
                   
            }
        }
    }
       
        private void crearProfe() {
            String ced= solicitar.SolicitaCedulas("profesor");
       if (ctrl.existePro(ced)) {//control
            System.out.println("Profesor ya existe, Error");
        } else {//control
            ctrl.agregarProfesor(solicitar.SolicitaNombres(),ced,solicitar.SolicitaTelefono(),solicitar.SolicitaEmail(),
                    solicitar.solicitaPass());
        }
    }
       
     private void BorrarPersona() {
        if (ctrl.borrarP(solicitar.SolicitaCedulas("persona"))) {//control
            mensaje.SucceDel();
        } else {
            mensaje.ErrorDel();
            
        }
    }
        
     private void ActualizarProfe() {
        Profesor pro = new Profesor();
        ctrl.mostrarPCed(pro,solicitar.SolicitaCedulas("profesor"));
        if (!pro.getCedula().isEmpty()) {
            int p = 0;
            while (p != 5) {
                switch (p = menu.ModifProf()) {
                    case 1://nombre
                        String nom= solicitar.SolicitaNombres();
                        if (nom != "") {
                            pro.setNombre(nom);
                        }
                         break;
                    case 2://pass
                        String pass = solicitar.solicitaPass();
                        if (pass != "") {
                            pro.setClave(pass);
                        }
                        break;
                    case 3://correo
                        String correo = solicitar.SolicitaEmail();
                        if (correo != "") {
                            pro.setEmail(correo);
                        }
                        break;
                    case 4://telefono
                        int tel = solicitar.SolicitaTelefono();
                        if (tel != 0) {
                            pro.setTelefono(tel);
                        }
                        break;
                    default:
                        mensaje.ERROR();
                }
            }
        } else {
            mensaje.ERROR();
            return;
        }
        ctrl.actualizaP(pro);
    }
     
    /*          termina metodos admi de profesores
     ---------------------------------------------------------------------------------*/ 
       
     
     
     private void AdminEst() {
        int opc = 0;
        while (opc != 5) {
           menu.MenuAlumno();
            switch (opc = solicitar.leerD()) {
                case 1:
                    crearEst();
                    break;
                case 2:
                    BorrarPersona();
                    break;
                case 3:
                    MostrarEst();
                    break;
                case 4:
                    ActualizarEst();
                    break;
                default: 
                    mensaje.ERROR();
                    break;
            }
        }
    }
      
     /*                Metodos Administrativos de los alumnos
     -------------------------------------------------------------------------------*/
         private void crearEst() {
        String ced = solicitar.SolicitaCedulas("Estudiante");
        if (ctrl.existeEst(ced)) {
            System.out.println("Estudiante ya existe, Error");
        } else {
            ctrl.agregarAlumno(solicitar.SolicitaNombres(), ced,solicitar.SolicitaFec_Nac(),solicitar.SolicitaTelefono(),
                    solicitar.SolicitaEmail(), solicitar.solicitaPass(), solicitar.SolicitaCodCarrera());
        }

    }
     
     private void MostrarEst() {
        int p = 0;
        Alumno per = new Alumno();
        while (p != 4) {
            menu.BusqAlmn();
            switch (p = solicitar.leerD()) {
                case 1:
                    ctrl.mostrarPCed(per,solicitar.SolicitaCedulas("Estudiante"));
                    System.out.println(per.toString());
                    break;
                case 2:
                           ArrayList<Alumno> l = new ArrayList<>();
                    ctrl.mostrarEstNom(solicitar.SolicitaNombres(), l);
                    System.out.println(l.toString());
                    break;
                case 3:
                    // buscar carrera
                           ArrayList<Alumno> w = new ArrayList<>();
                    ctrl.mostrarPCar(solicitar.SolicitaCodCarrera(), w);
                    System.out.println(w.toString());
                    break;
                default:
                    mensaje.ERROR();
            }
        }
    }
     
       private void ActualizarEst() {
        Alumno al = new Alumno();
        ctrl.mostrarPCed(al, solicitar.SolicitaCedulas("Estudiante"));

        if (al.getCedula() != "") {
            System.out.println(al.toString());
            int p = 0;
            while (p != 5) {
                p= menu.ModifAlmn();
                switch (p) {
                    case 1://nombre
                        String nom= solicitar.SolicitaNombres();
                        if (nom != "") {
                            al.setNombre(nom);
                        }
                        break;
                    case 2://pass
                        String pass = solicitar.solicitaPass();
                        if (pass != "") {
                            al.setClave(pass);
                        }
                        break;
                    case 3://correo
                        String correo = solicitar.SolicitaEmail();
                        if (correo != "") {
                            al.setEmail(correo);
                        }
                        break;
                    case 4://telefono
                        int tel = solicitar.SolicitaTelefono();
                        if (tel != 0) {
                            al.setTelefono(tel);
                        }
                        break;
                    default:
                        mensaje.ERROR();
                }
            }
        } else {
            mensaje.ERROR();
            System.out.println("estudianre no existe");
            return;
        }
        ctrl.actualizaP(al);
    }
     
    /*termina los metodos Amdministrativos de los alumnos
     ---------------------------------------------------------------------------*/
    
}
