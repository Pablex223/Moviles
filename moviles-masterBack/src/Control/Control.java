package Control;

import AccesoDatos.AccesoDB;
import Modelo.*;
import java.util.ArrayList;

public class Control {

    public Control() {
        this.user = new Persona(0);
        this.accesoD = new AccesoDB();

    }

    public int verificaUsuario(String usr, String pass) {
        accesoD.validaUser(user, usr, pass);
        return user.getTipo();
    }

    


//<Metodos de Curso">
    public void agregarCurso(String cod, String nom, int cre, int hsm, String cca, int C) {
        Curso c = new Curso(cod, nom, cre, hsm, cca, C);
        accesoD.agrega(c);
    }

//    public boolean BorrarCurso(String cod) {
//        return accesoD.eliminarCurso(cod);
//    }
//
//    public void MostrarCurso(Curso C, String cod, int t) {
//        if (t == 1) {
//            accesoD.BuscarCursoNom(C, cod);
//        }
//        if (t == 2) {
//            accesoD.BuscarCursoCod(C, cod);
//        }
//    }
//    public void MostrarCursos(ArrayList<Curso> l, String cod, String clc) {
//        accesoD.BuscarCursoCar(l, cod, clc);
//    }
//
//    public void MostrarCursos(ArrayList<Curso> l, String cod) {
//        accesoD.BuscarCursoCar(l, cod);
//    }

    public void ActualizarCurso(Curso c) {
        accesoD.Actualiza(c);
    }
    
    public boolean existeCur(String cog)
    {
        Curso c= new Curso();
        accesoD.BuscarCursoCod(c, cog);
        return true ? c.getCodigo()!="": false;
    }
    
    
//"Metodos de Profesores">
    public void agregarProfesor(String nombre, String cedula, int telefono, String email, String clave) {
        Profesor p = new Profesor(telefono, email, nombre, cedula, clave);
        agregarP(p);
    }
    
    public boolean existePro(String cod)
    {
        Profesor p= new Profesor();
        accesoD.BuscarPrfId(p, cod);
        return true ? p.getCedula()!="":false;
    }


    
    
//"Metodos de Persona">
    public void agregarAlumno(String nombre, String cedula, String Fec_Nac, int telefono, String email, String clave,String codCarr) {
        Alumno p = new Alumno(telefono, email, nombre, cedula, Fec_Nac, clave,codCarr);
        accesoD.agrega(p);
    }

    public void agregarMatriculador(int telefono, String email, String nombre, String cedula, String clave) {
        Matriculador p = new Matriculador(telefono, email, nombre, cedula, clave);
        accesoD.agrega(p);
    }

    public void agregarAdministrador(String nombre, String cedula, int telefono, String email, String clave) {
        Administrador p = new Administrador(telefono, email, nombre, cedula, clave);
        accesoD.agrega(p);
    }

    public void agregarP(Persona a) {
        accesoD.agrega(a);
    }

    public boolean borrarP(String id) {
        return accesoD.eliminar(id);

    }

    public void mostrarProNom(String nom, ArrayList<Profesor> l) {
        accesoD.BuscarPrfNombre(nom, l);
    }

    public void mostrarEstNom(String nom, ArrayList<Alumno> l) {
        accesoD.BuscarEstNom(nom, l);
    }

    public void mostrarPCed(Persona a, String ced) {
        if (a instanceof Profesor) {
            accesoD.BuscarPrfId(a, ced);
        }
        if (a instanceof Alumno) {
            accesoD.BuscarEStId(a, ced);
        }
        if (a instanceof Administrador) {
            accesoD.BuscarAdmId(a, ced);
        }
        if (a instanceof Matriculador) {
            accesoD.BuscarMtr(a, ced);
        }
    }

    public void buscarPer(Persona a, String ced) {
        accesoD.Buscar(a, ced);
    }

    public void mostrarPCar(String carr, ArrayList<Alumno> l) {
        accesoD.BuscarEstCarr(carr, l);
    }

    public void actualizaP(Persona p) {
        accesoD.Actualiza(p);
    }
    
    public boolean existeEst(String cod)
    {
        Persona e= new Persona(4);
        accesoD.BuscarEStId(e, cod);
        return true?e.getCedula()!="":false;
    }
    
      public boolean existeAdmi(String cod)
    {
        Persona e= new Persona(1);
        accesoD.BuscarAdmId(e, cod);
        return true?e.getCedula()!="":false;
    }
      
        public boolean existeMat(String cod)
    {
        Persona e= new Persona(2);
        accesoD.BuscarMtr(e, cod);
        return true?e.getCedula()!="":false;
    }
    
    
   public void mostrarEstCar(ArrayList<Alumno> l,String car)
   {
       accesoD.BuscarEstCarr(car, l);
   }

//</editor-fold>
   
   
    public void ofertaAcd(String Carrera, int ciclo, ArrayList<Curso> cursos) {
        accesoD.ofertaAcd(Carrera, ciclo, cursos);
    }

    public void BuscarGrpCrs(String curso, ArrayList<Grupo> grupos) {
        accesoD.BuscarGrpCrs(curso, grupos);
    }


//    public void ConsultaHistorial(String ced, ArrayList<Nota> l) {
//        accesoD.Historial(ced, l);
//    }
//
//    public void matriculados(String ced, ArrayList<Nota> l) {
//        accesoD.Matriculados(ced, l);
//    }

   
    
    
//<Metodos de grupo">
    public void agregarGrupo( int numero, String horario, String profesor, String curso) {
        String id = curso + '-' + String.valueOf(numero);

        Grupo p = new Grupo(id, numero, horario, profesor, curso);

        accesoD.agrega(p);
    }

    public void Bcgrupo(String id_g, int ngrp, Grupo p) {
        accesoD.Buscar(p, id_g, ngrp);
    }
    
    public void Buscar(Grupo g,String cod)
    {
        accesoD.Buscar(g, cod);
    }
    
    public void cursoNCarrera(Curso a, String CArrare, String codcur){
        accesoD.BuscarCursoCar2(a, CArrare, codcur);
    }
    
    public void actualizar(Grupo g)
    {
        accesoD.Actualiza(g);
    }
    
    public boolean existeGrupo(String cod)
    {
        Grupo g= new Grupo();
        accesoD.Buscar(g, cod);
        return true? g.getId()!="":false;
    }
    
   
    

//     
//    public boolean DesMAtricula(String cod, Alumno a){
//        if(!accesoD.pasoCurso(a.getCedula(), cod).isEmpty()){
//                accesoD.Desmatricula(a.getCedula(),cod);          
//                return true;
//           }       
//        return false;
//    }
//    
//    public void desmatricula(String ced, String cur)
//    {
//        accesoD.Desmatricula(ced ,cur); 
//    }
    
    
    public void notasPgrupo(String cdogrp, ArrayList<Nota> l){
           accesoD.notasDEgrupo(cdogrp, l);
    }
    
    public void gruposProfe(String ced, ArrayList<Grupo> l){
                accesoD.BuscarGrpPrf(ced, l);
    }
    public void notaEst(String a, String est, Nota n){
        accesoD.notaESt(a, est, n);
    }
   
//    public void Matricular(Nota n)
//    {
//        accesoD.matricula(n);
//    }
    
    public void cursado(String est,String cur,Nota n)
    {
        accesoD.condicion(est, cur, n);
    }
    public void encurso(String est,String cur,Nota n)
    {
        accesoD.Cursoencurso(est, cur, n);
    }
    
    public void EstGrupo(String cdo_Cur,int num_Gru, ArrayList<Nota> l)
    {
        accesoD.EstDeGrupo(cdo_Cur, num_Gru, l);
    }
    
    private Persona user;
    private AccesoDB accesoD;

    public void actualiza(Nota n) {
        accesoD.Actualiza(n);
    }
    
    
}
