/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examen.entity;

/**
 *
 * @author michaelcw02
 */
public class Sugerencia {
    private int     id;
    private Usuario usuario;
    private String  texto;
    private int     puntaje;

    public Sugerencia(int id, Usuario usuario, String texto, int puntaje) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
        this.puntaje = puntaje;
    }

    public Sugerencia(Usuario usuario, String texto) {
        this.id = -1;
        this.usuario = usuario;
        this.texto = texto;
        this.puntaje = 0;
    }  

    public Sugerencia() {
        this.id = 0;
        this.usuario = new Usuario();
        this.texto = "";
        this.puntaje = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    
    
}
