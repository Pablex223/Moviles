package Control;

import AccesoDatos.AccesoDB;
import Modelo.*;
import java.util.Observer;
import javax.swing.table.TableModel;

public class Control {

    public Control() {
        datos = new Modelo();
        this.user = new Persona(0);
      

    }
     public Control(Modelo nuevosDatos){
        datos = nuevosDatos;
         this.user = new Persona(0);
    }
    public void agregar(Persona nuevaPersona){
        datos.agregar(nuevaPersona);
    }
    public void eliminar(Persona personaEliminar){
       datos.eliminar(personaEliminar);
     }
    public void registrar(Observer nuevoObservador){
        datos.addObserver(nuevoObservador);
    }
    public int verificaUsuario(String usr, String pass) {
        accesoD.validaUser(user, usr, pass);
        return user.getTipo();
    }
 public TableModel modeloTabla(){
        return datos.modeloTabla();
    }
 
      public void cargarDatos(){
         datos.cargarDatos();
     }
    
    //Atributos
    private Persona user;
    private AccesoDB accesoD;
    private Modelo datos;

    
}
