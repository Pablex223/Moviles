package com.example.javie.proyecto.Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by javie on 12/2/2017.
 */

public class Categoria {

    //ATRIBUTOS
    private String nombre;
    List<String> listaRespuestas;

    public Categoria() {
        nombre = "";
    }
    public Categoria(String n) {
        nombre = n;
        listaRespuestas = new ArrayList<String>();
        listaRespuestas.add("Alegria");
        listaRespuestas.add("Tristeza");
        listaRespuestas.add("Llanto");
        listaRespuestas.add("Sorpresa");
        listaRespuestas.add("Emocion");
        listaRespuestas.add("Amor");
    }

    public List<String> getRespuestasAlternas(String respuestaCorrecta){
        Random rand = new Random();
        int cont = 0;
        List<String> aux =listaRespuestas;
        List<String> listaAlternativas = new ArrayList<>();
        while(cont < 3){
            int index = rand.nextInt(aux.size());
            String alt = aux.get(index);
            if(!respuestaCorrecta.equalsIgnoreCase(alt)){
                listaAlternativas.add(alt);
                aux.remove(index);
                cont++;
            }
        }
        return listaAlternativas;
    }



}
