package Modelo;

public class Curso 
{    private String codigo;
    private String nombre;
    private int creditos;
    private int hsemanales;
    private String carrera;
    private int num_ciclo;

    public Curso(String codigo, String nombre, int creditos, int hsemanales, String carrera, int num_ciclo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.hsemanales = hsemanales;
        this.carrera = carrera;
        this.num_ciclo = num_ciclo;
    }

     @Override
    public String toString() {
        return "nombre=" + nombre + ", codigo=" + codigo +", creditos=" + creditos +", hsemanales=" + hsemanales +
                ", carrera=" + carrera +", num_ciclo=" + num_ciclo +'}';
    }

    public Curso() {
        this.codigo = "";
        this.nombre = "";
        this.creditos = 0;
        this.hsemanales = 0;
        this.carrera = "";
        this.num_ciclo = 0;
    }

    public int getNum_ciclo() {
        return num_ciclo;
    }

    public void setNum_ciclo(int num_ciclo) {
        this.num_ciclo = num_ciclo;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHsemanales() {
        return hsemanales;
    }

    public void setHsemanales(int hsemanales) {
        this.hsemanales = hsemanales;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
 

    
}
