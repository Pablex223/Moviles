/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c")
    , @NamedQuery(name = "Cuenta.findById", query = "SELECT c FROM Cuenta c WHERE c.id = :id")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaid")
    private List<Pictograma> pictogramaList;
    @JoinColumn(name = "Usuario_nombre", referencedColumnName = "nombre")
    @ManyToOne(optional = false)
    private Persona usuarionombre;

    public Cuenta() {
    }

    public Cuenta(Integer id, Persona usuarionombre) {
        this.id = id;
        this.usuarionombre = usuarionombre;
    }

    
    public Cuenta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Pictograma> getPictogramaList() {
        return pictogramaList;
    }

    public void setPictogramaList(List<Pictograma> pictogramaList) {
        this.pictogramaList = pictogramaList;
    }

    public Persona getUsuarionombre() {
        return usuarionombre;
    }

    public void setUsuarionombre(Persona usuarionombre) {
        this.usuarionombre = usuarionombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cuenta[ id=" + id + " ]";
    }
    
    
    /*
    public org.json.JSONObject toJson(){
        return new org.json.JSONObject()
                .put("id", this.id)
                .put("usuarioN",this.usuarionombre.toJson());
    } 
    
    public static Cuenta fromJson(org.json.JSONObject jo)
            throws org.json.JSONException
    {
        return new Cuenta(
                jo.getInt("id"),
                Persona.fromJson(jo.getJSONObject("usuarioN"))
                
            );  
  
}
    */  
    
    
}
