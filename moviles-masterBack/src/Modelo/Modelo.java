/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import AccesoDatos.AccesoDB;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.table.TableModel;

/**
 *
 * @author javie
 */
public class Modelo extends Observable{
    
    public Modelo(){
          this.accesoD = new AccesoDB();
        alumnos = new ConjuntoPersonas();
        //Al enviar al objeto personas como parámetro al 
        //modelo de la tabla, hace que el modelo de la tabla
        //manipule los mismos objetos del ArrayList de ConjuntoPersonas
        modeloTabla = new ModeloTablaPersonas(alumnos);
    
    }
    
    public TableModel modeloTabla(){
        return modeloTabla;
    }
  public void agregar(Persona nuevaPersona){
        accesoD.agrega(nuevaPersona);
        setChanged();
        notifyObservers(nuevaPersona);
        
    
    }
     public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
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
      private ConjuntoPersonas alumnos;
}
