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
public class Usuario {
     private String cedula;
     private String clave;
     private TipoUsuario tipoUsuario;

    public Usuario(String cedula, String clave, TipoUsuario tipoUsuario) {
        this.cedula = cedula;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
     
     
     
     
    
}
