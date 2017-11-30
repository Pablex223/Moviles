package com.example.javie.proyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Pictograma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javie on 11/22/2017.
 */

public class CrearPictogramas extends Fragment {

    Button btnRespuest1,btnRespuest2,btnRespuest3,btnRespuest4;
    ImageView imgPictograma;
    List<Pictograma> listaPictogramas;
    int contador;
    public CrearPictogramas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictograma, container, false);
        contador = 0;
        listaPictogramas = new ArrayList<Pictograma>();
        inicializarPictogramas();

        btnRespuest1 = (Button) view.findViewById(R.id.btnRespuesta1);
        btnRespuest2 = (Button) view.findViewById(R.id.btnRespuesta2);
        btnRespuest3 = (Button) view.findViewById(R.id.btnRespuesta3);
        btnRespuest4 = (Button) view.findViewById(R.id.btnRespuesta4);
        imgPictograma = (ImageView) view.findViewById(R.id.imgPictograma);
        imgPictograma.setImageResource(listaPictogramas.get(contador).getId());

        btnRespuest1.setOnClickListener(marcarRespuesta);
        btnRespuest2.setOnClickListener(marcarRespuesta);
        btnRespuest3.setOnClickListener(marcarRespuesta);
        btnRespuest4.setOnClickListener(marcarRespuesta);

        return view;
    }

    private View.OnClickListener marcarRespuesta = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            String respuesta = ((Button) v).getText().toString();
            Toast.makeText(getActivity(), respuesta, Toast.LENGTH_SHORT).show();
            if(++contador < listaPictogramas.size()){
                imgPictograma.setImageResource(listaPictogramas.get(contador).getId());
            }
            else{
                Toast.makeText(getActivity(), "Gracias por jugar!", Toast.LENGTH_SHORT).show();
                deshabilitarBotones();
            }

        }
    };

    private boolean respouestaCorrecta(int idPictograma, String idButton){
        String correcta = "";

        return correcta.equals(idButton);
    }

    private void inicializarPictogramas(){
        //sacarlos de la base
        //pruebas..
        listaPictogramas.add(new Pictograma(1, R.drawable.acerca_de, ""));
        listaPictogramas.add(new Pictograma(2, R.drawable.usuario, ""));
        listaPictogramas.add(new Pictograma(3, R.drawable.ic_menu_camera, ""));
        listaPictogramas.add(new Pictograma(4, R.drawable.ic_menu_gallery, ""));
    }

    private void deshabilitarBotones(){
        int colorDisabled = Color.parseColor("#919191");
        btnRespuest1.setEnabled(false);
        btnRespuest1.setBackgroundColor(colorDisabled);
        btnRespuest2.setEnabled(false);
        btnRespuest2.setBackgroundColor(colorDisabled);
        btnRespuest3.setEnabled(false);
        btnRespuest3.setBackgroundColor(colorDisabled);
        btnRespuest4.setEnabled(false);
        btnRespuest4.setBackgroundColor(colorDisabled);
    }


}
