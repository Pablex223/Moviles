package Control;

import AccesoDatos.AccesoDB;
import Modelo.*;
import java.util.ArrayList;
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
    public void agregar(Persona nuevPersona){
        datos.agregar(nuevPersona);
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
  public void actualizar(Object evento){
         datos.actualizar(evento);
     }
      public void cargarDatos(){
         datos.cargarDatos();
     }
    
    //Atributos
    private Persona user;
    private AccesoDB accesoD;
    private Modelo datos;

    
}
