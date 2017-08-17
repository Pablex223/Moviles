
package backend.entidades;

import java.util.Date;



abstract public class Persona {
    protected String  cedula;
    protected String  nombre;
    protected String telefono;
    protected String email;
    protected Date nacimiento;
    

    public Persona(String cedula, String nombre, String telefono, String email, Date nacimiento) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.nacimiento = nacimiento;
    }
    
    
   

    final  public String getCedula() {
        return cedula;
    }

    final public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    final public String getNombre() {
        return nombre;
    }

    final public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    final public String getTelefono() {
        return telefono;
    }

   final  public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

   final  public String getEmail() {
        return email;
    }

   final  public void setEmail(String email) {
        this.email = email;
    }

    final public Date getNacimiento() {
        return nacimiento;
    }

    final public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    @Override
    abstract public String toString();
    
    
    
    
    
}
