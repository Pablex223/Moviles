package Modelo;

public class Administrador extends Persona {

    public Administrador(int telefono, String email, String nombre, String cedula, String clave) {
        super(telefono, email, nombre, cedula,1, clave);
    }

    public Administrador() {
    super(1);
    }

    
}
