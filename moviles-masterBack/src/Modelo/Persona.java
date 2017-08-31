package Modelo;

public class Persona{
    
    private int telefono;
    private String email;
    private String nombre;
    private String cedula;
    private int tipo;
    private String clave;

    public Persona(int telefono, String email, String nombre, String cedula, int tipo, String clave) {
        this.telefono = telefono;
        this.email = email;
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipo = tipo;
        this.clave = clave;
    }
    

    public Persona(int t) {
         telefono = 0;
        email = "";
        nombre = "";
        cedula = "";
        tipo = t;
        clave = "";
    }
    

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Persona{" + "telefono=" + telefono + ", email=" + email + ", nombre=" + nombre + ", cedula=" + cedula + ", tipo=" + tipo + ", clave=" + clave + '}';
    }

   
  
    
}
