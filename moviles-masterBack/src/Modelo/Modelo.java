/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import AccesoDatos.AccesoDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.table.TableModel;

/**
 *
 * @author javie
 */
public class Modelo extends Observable{
    
    public Modelo(){
          this.accesoD = new AccesoDB();
        alumnos = new ArrayList<>();
        //Al enviar al objeto personas como parámetro al 
        //modelo de la tabla, hace que el modelo de la tabla
        //manipule los mismos objetos del ArrayList de ConjuntoPersonas
        modeloTabla = new ModeloTablaPersonas(alumnos);
    
    }
    
    public TableModel modeloTabla(){
        return modeloTabla;
    }
 
  public void agregar(Persona nuevaPersona){
      String respuesta = "No se pudo agregar la persona: ";
       if( accesoD.agrega(nuevaPersona) == 1){
           accesoD.getTodosAlumnos(alumnos);
          respuesta = "Se agrego la persona: ";
       }
        setChanged();
        notifyObservers(respuesta+ nuevaPersona.cedula);
    }
  public void eliminar(Persona eliminarPersona){
      String respuesta = "No se pudo eliminar la persona: ";
       if( accesoD.eliminar(eliminarPersona) == 1){
          accesoD.getTodosAlumnos(alumnos);
          respuesta = "Se elimino la persona: ";
       }
        setChanged();
        notifyObservers(respuesta+ eliminarPersona.cedula);
    }
     

     public void cargarDatos(){
        accesoD.getTodosAlumnos(alumnos);
       System.out.print(alumnos);
        //Se notifica que el modelo cambio su estado
        //porque agregó elementos al ArrayList
        setChanged();
        notifyObservers("Carga completada ...");
        
    }
     //Atributo (DAO)
     private final AccesoDB accesoD;
     private ModeloTablaPersonas modeloTabla;
      private List<Persona> alumnos;
}
