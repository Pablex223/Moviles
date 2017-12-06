/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luisf
 */
@Entity
@Table(name = "pictograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pictograma.findAll", query = "SELECT p FROM Pictograma p")
    , @NamedQuery(name = "Pictograma.findById", query = "SELECT p FROM Pictograma p WHERE p.id = :id")
    , @NamedQuery(name = "Pictograma.findByNombre", query = "SELECT p FROM Pictograma p WHERE p.nombre = :nombre")})
public class Pictograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "imagen")
    private byte[] imagen;
    @JoinColumn(name = "Categoria_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria categoriaid;
    @JoinColumn(name = "Cuenta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cuenta cuentaid;

    public Pictograma() {
    }

    public Pictograma(Integer id) {
        this.id = id;
    }

    public Pictograma(Integer id, String nombre, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Categoria categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Cuenta getCuentaid() {
        return cuentaid;
    }

    public void setCuentaid(Cuenta cuentaid) {
        this.cuentaid = cuentaid;
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
        if (!(object instanceof Pictograma)) {
            return false;
        }
        Pictograma other = (Pictograma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Pictograma[ id=" + id + " ]";
    }
    
}
