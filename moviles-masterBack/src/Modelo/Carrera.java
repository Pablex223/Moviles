package Modelo;

public class Carrera {
    private String nombre;
    private String codigo;
  

    public Carrera(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        
    }
     @Override
    public String toString() {
        return "Carrera{" + "nombre=" + nombre + ", codigo=" + codigo + '}';
    }

    public Carrera() {
        this.nombre = "";
        this.codigo = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    
}
