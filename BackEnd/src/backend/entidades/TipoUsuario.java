/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entidades;

/**
 *
 * @author Pablo
 */
public class TipoUsuario {
    private String usuario;
    private int acceso;

    public TipoUsuario(String usuario, int acceso) {
        this.usuario = usuario;
        this.acceso = acceso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getAcceso() {
        return acceso;
    }

    public void setAcceso(int acceso) {
        this.acceso = acceso;
    }
    
   
    
    
    
    
    
}
