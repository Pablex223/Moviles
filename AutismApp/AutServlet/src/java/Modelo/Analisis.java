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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "analisis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Analisis.findAll", query = "SELECT a FROM Analisis a")
    , @NamedQuery(name = "Analisis.findById", query = "SELECT a FROM Analisis a WHERE a.id = :id")
    , @NamedQuery(name = "Analisis.findByAreaSocial", query = "SELECT a FROM Analisis a WHERE a.areaSocial = :areaSocial")
    , @NamedQuery(name = "Analisis.findByAreaLinguistica", query = "SELECT a FROM Analisis a WHERE a.areaLinguistica = :areaLinguistica")
    , @NamedQuery(name = "Analisis.findByAreaConductas", query = "SELECT a FROM Analisis a WHERE a.areaConductas = :areaConductas")})
public class Analisis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "areaSocial")
    private Integer areaSocial;
    @Column(name = "areaLinguistica")
    private Integer areaLinguistica;
    @Column(name = "areaConductas")
    private Integer areaConductas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analisisid")
    private List<Persona> personaList;

    public Analisis() {
    }
    
    public Analisis(int id, int areaS,int areaL,int areaC){
        this.id= id;
        this.areaSocial=areaS;
        this.areaLinguistica=areaL;
        this.areaConductas= areaC;
    }
    

    public Analisis(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaSocial() {
        return areaSocial;
    }

    public void setAreaSocial(Integer areaSocial) {
        this.areaSocial = areaSocial;
    }

    public Integer getAreaLinguistica() {
        return areaLinguistica;
    }

    public void setAreaLinguistica(Integer areaLinguistica) {
        this.areaLinguistica = areaLinguistica;
    }

    public Integer getAreaConductas() {
        return areaConductas;
    }

    public void setAreaConductas(Integer areaConductas) {
        this.areaConductas = areaConductas;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
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
        if (!(object instanceof Analisis)) {
            return false;
        }
        Analisis other = (Analisis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Analisis[ id=" + id + " ]";
    }
    
    
    
    
    public org.json.JSONObject toJson(){
        return new org.json.JSONObject()
                .put("id", this.id)
                .put("areaS", this.areaSocial)
                .put("areaL",this.areaLinguistica)
                .put("areaC", this.areaConductas);
    } 
    
    public static Analisis fromJson(org.json.JSONObject jo)
            throws org.json.JSONException
    {
        return new Analisis(
                jo.getInt("id"),
                jo.getInt("areaS"),
                jo.getInt("areaL"),
                jo.getInt("areaC")
                               
            );  
}
    
    
}
