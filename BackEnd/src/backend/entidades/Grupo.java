/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entidades;

import java.util.ArrayList;

/**
 *
 * @author Estudiantes
 */
public class Grupo {
    private int numGrupo;
    private Curso cursoGrupo;
    private Ciclo cicloGrupo;
    private ArrayList<Usuario> alumnos;
    
    public Grupo(int numGrupo, Curso cursoGrupo, Ciclo cicloGrupo) {
        this.numGrupo = numGrupo;
        this.cursoGrupo = cursoGrupo;
        this.cicloGrupo = cicloGrupo;
    }

    public int getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(int numGrupo) {
        this.numGrupo = numGrupo;
    }

    public Curso getCursoGrupo() {
        return cursoGrupo;
    }

    public void setCursoGrupo(Curso cursoGrupo) {
        this.cursoGrupo = cursoGrupo;
    }

    public Ciclo getCicloGrupo() {
        return cicloGrupo;
    }

    public void setCicloGrupo(Ciclo cicloGrupo) {
        this.cicloGrupo = cicloGrupo;
    }

    public ArrayList<Persona> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Persona> alumnos) {
        this.alumnos = alumnos;
    }
    
    
}
