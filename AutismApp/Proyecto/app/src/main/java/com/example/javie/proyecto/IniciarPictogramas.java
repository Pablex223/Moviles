package com.example.javie.proyecto;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Pictograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */

public class IniciarPictogramas extends Fragment {

    MainActivity activity;
    Button btnRespuest1,btnRespuest2,btnRespuest3,btnRespuest4,btnResponder,btnReinciar,btnRespuestaFinal = null;
    ImageView imgPictograma, mic1,mic2,mic3,mic4;
    List<Pictograma> listaPictogramas;
    List<Button> listaBotonesRespuestas;
    int contador;
    public IniciarPictogramas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictograma, container, false);
        activity = (MainActivity) getActivity();

        contador = 0;
        btnRespuest1 = (Button) view.findViewById(R.id.btnRespuesta1);
        btnRespuest2 = (Button) view.findViewById(R.id.btnRespuesta2);
        btnRespuest3 = (Button) view.findViewById(R.id.btnRespuesta3);
        btnRespuest4 = (Button) view.findViewById(R.id.btnRespuesta4);
        btnReinciar = (Button) view.findViewById(R.id.btnReinciar);
        btnReinciar.setVisibility(View.INVISIBLE);
        btnResponder = (Button) view.findViewById(R.id.btnResponder);

        mic1 = (ImageView) view.findViewById(R.id.mic1);
        mic2 = (ImageView) view.findViewById(R.id.mic2);
        mic3 = (ImageView) view.findViewById(R.id.mic3);
        mic4 = (ImageView) view.findViewById(R.id.mic4);

        imgPictograma = (ImageView) view.findViewById(R.id.imgPictograma);

        btnRespuest1.setOnClickListener(marcarRespuesta);
        btnRespuest2.setOnClickListener(marcarRespuesta);
        btnRespuest3.setOnClickListener(marcarRespuesta);
        btnRespuest4.setOnClickListener(marcarRespuesta);

        mic1.setOnClickListener(escucharRespuesta);
        mic2.setOnClickListener(escucharRespuesta);
        mic3.setOnClickListener(escucharRespuesta);
        mic4.setOnClickListener(escucharRespuesta);

        btnResponder.setOnClickListener(responder);

        reiniciarListaBotones();
        inicializarPictogramas();
        return view;
    }


  private void reiniciarListaBotones(){
      listaBotonesRespuestas = new ArrayList<Button>();
      listaBotonesRespuestas.add(btnRespuest1);
      listaBotonesRespuestas.add(btnRespuest2);
      listaBotonesRespuestas.add(btnRespuest3);
      listaBotonesRespuestas.add(btnRespuest4);
  }
    private View.OnClickListener escucharRespuesta = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            MainActivity myActivity = (MainActivity) getActivity();
            switch(v.getId()) {
                case R.id.mic1:
                    myActivity.speak(btnRespuest1.getText().toString());
                    break;
                case R.id.mic2:
                    myActivity.speak(btnRespuest2.getText().toString());
                    break;
                case R.id.mic3:
                    myActivity.speak(btnRespuest3.getText().toString());
                    break;
                case R.id.mic4:
                    myActivity.speak(btnRespuest4.getText().toString());
                    break;
            }
        }
    };
    private View.OnClickListener marcarRespuesta = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Button b = (Button) v;
            String respuesta = b.getText().toString();
            b.setPaintFlags(b.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
            desmarcarRespuesta(b);
            btnRespuestaFinal = b;
            Toast.makeText(getActivity(), respuesta, Toast.LENGTH_SHORT).show();
        }
    };

    private void desmarcarRespuesta(Button marcado){
        switch (marcado.getId()){
            case R.id.btnRespuesta1: {
                btnRespuest2.setPaintFlags(0);
                btnRespuest3.setPaintFlags(0);
                btnRespuest4.setPaintFlags(0);
            }
            break;
            case R.id.btnRespuesta2: {
                btnRespuest1.setPaintFlags(0);
                btnRespuest3.setPaintFlags(0);
                btnRespuest4.setPaintFlags(0);
            }
            break;
            case R.id.btnRespuesta3: {
                btnRespuest2.setPaintFlags(0);
                btnRespuest1.setPaintFlags(0);
                btnRespuest4.setPaintFlags(0);
            }
            break;
            case R.id.btnRespuesta4: {
                btnRespuest2.setPaintFlags(0);
                btnRespuest3.setPaintFlags(0);
                btnRespuest1.setPaintFlags(0);
            }
            break;
        }
    }

    private View.OnClickListener responder = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if(btnRespuestaFinal != null){
                //Revisar si la respuesta es correcta
                //Aumentarla en el marcador
                if(++contador < listaPictogramas.size()){
                    Pictograma actual = listaPictogramas.get(contador);
                    imgPictograma.setImageBitmap(actual.getImagen());
                    Button respuestaCorrecta = botonRandom();
                    respuestaCorrecta.setText(actual.getRespuesta());
                    //Toast.makeText(getActivity(), actual.getRespuestasAlternas().toString(), Toast.LENGTH_SHORT).show();
                    llenarRespuestas(actual.getRespuestasAlternas());
                    reiniciarListaBotones();
                }
                else{
                    Toast.makeText(getActivity(), "¡Gracias por jugar!", Toast.LENGTH_SHORT).show();
                    deshabilitarBotones();
                    btnReinciar.setVisibility(View.VISIBLE);
                    btnReinciar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            IniciarPictogramas iniciarPictogramas = new IniciarPictogramas();
                            manager.beginTransaction().replace(R.id.contenedor,
                                    iniciarPictogramas,
                                    iniciarPictogramas.getTag()).commit();
                        }
                    });
                }
            }
            else
                Toast.makeText(getActivity(), "¡Seleccione una opcion!", Toast.LENGTH_SHORT).show();
        }
    };


    private boolean respouestaCorrecta(int idPictograma, String idButton){
        String correcta = "";

        return correcta.equals(idButton);
    }

    private void inicializarPictogramas(){
        listaPictogramas = activity.getPictogramasPorCategoria("Emocional");
        Pictograma actual = listaPictogramas.get(0);
        imgPictograma.setImageBitmap(actual.getImagen());
        Button respuestaCorrecta = botonRandom();
        respuestaCorrecta.setText(actual.getRespuesta());
        //Toast.makeText(getActivity(), actual.getRespuestasAlternas().toString(), Toast.LENGTH_SHORT).show();
        llenarRespuestas(actual.getRespuestasAlternas());
        reiniciarListaBotones();
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

    private Button botonRandom(){
        Random rand = new Random();
        int n = rand.nextInt(listaBotonesRespuestas.size());
        Button btnRand = listaBotonesRespuestas.get(n);
        listaBotonesRespuestas.remove(n);
        return btnRand;
    }

    private void llenarRespuestas(List<String> listaAlternativas) {
        botonRandom().setText(listaAlternativas.get(0));
        botonRandom().setText(listaAlternativas.get(1));
        botonRandom().setText(listaAlternativas.get(2));
    }

}
