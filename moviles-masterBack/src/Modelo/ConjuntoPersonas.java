

package Modelo;

import java.util.ArrayList;


public class ConjuntoPersonas {
    
    public ConjuntoPersonas(){
        personas = new ArrayList<>();
    }
    
    public void agregar(Persona nuevaPersona){
        personas.add(nuevaPersona);
    }
    
    public void eliminar(int posicion){
        personas.remove(posicion);
    }
    
    public Persona recuperar(int p){
        return personas.get(p);
    }
    
    public int numPersonas(){
        return personas.size();
    }
    
    public void cargar(){
        System.out.println("Entre al método cargar");
        agregar(new Persona(89999,"correr","Pepita","kkk",5,"aa"));
    
        System.out.println("Cantidad de personas" + personas.size());
    
    }
    
    
    
    //Atributos
    private ArrayList<Persona> personas;
    
}
