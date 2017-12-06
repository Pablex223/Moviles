package com.example.javie.proyecto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Formulario extends Fragment {

    RadioButton SiPregunta1, NoPregunta1;
    RadioButton SiPregunta2, NoPregunta2;
    RadioButton SiPregunta3, NoPregunta3;
    RadioButton SiPregunta4, NoPregunta4;
    RadioButton SiPregunta5, NoPregunta5;
    RadioButton SiPregunta6, NoPregunta6;
    RadioButton SiPregunta7, NoPregunta7;
    RadioButton SiPregunta8, NoPregunta8;
    RadioButton SiPregunta9, NoPregunta9;
    RadioButton SiPregunta10, NoPregunta10;
    RadioButton SiPregunta11, NoPregunta11;
    RadioButton SiPregunta12, NoPregunta12;

    Button btnSubmitFormulario;
    public Formulario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formulario, container, false);
        SiPregunta1 = (RadioButton) view.findViewById(R.id.SiPregunta1);
        NoPregunta1 = (RadioButton) view.findViewById(R.id.NoPregunta1);
        SiPregunta2 = (RadioButton) view.findViewById(R.id.SiPregunta2);
        NoPregunta2 = (RadioButton) view.findViewById(R.id.NoPregunta2);
        SiPregunta3 = (RadioButton) view.findViewById(R.id.SiPregunta3);
        NoPregunta3 = (RadioButton) view.findViewById(R.id.NoPregunta3);
        SiPregunta4 = (RadioButton) view.findViewById(R.id.SiPregunta4);
        NoPregunta4 = (RadioButton) view.findViewById(R.id.NoPregunta4);
        SiPregunta5 = (RadioButton) view.findViewById(R.id.SiPregunta5);
        NoPregunta5 = (RadioButton) view.findViewById(R.id.NoPregunta5);
        SiPregunta6 = (RadioButton) view.findViewById(R.id.SiPregunta6);
        NoPregunta6 = (RadioButton) view.findViewById(R.id.NoPregunta6);
        SiPregunta7 = (RadioButton) view.findViewById(R.id.SiPregunta7);
        NoPregunta7 = (RadioButton) view.findViewById(R.id.NoPregunta7);
        SiPregunta8 = (RadioButton) view.findViewById(R.id.SiPregunta8);
        NoPregunta8 = (RadioButton) view.findViewById(R.id.NoPregunta8);
        SiPregunta9 = (RadioButton) view.findViewById(R.id.SiPregunta9);
        NoPregunta9 = (RadioButton) view.findViewById(R.id.NoPregunta9);
        SiPregunta10 = (RadioButton) view.findViewById(R.id.SiPregunta10);
        NoPregunta10 = (RadioButton) view.findViewById(R.id.NoPregunta10);
        SiPregunta11 = (RadioButton) view.findViewById(R.id.SiPregunta11);
        NoPregunta11 = (RadioButton) view.findViewById(R.id.NoPregunta11);
        SiPregunta12 = (RadioButton) view.findViewById(R.id.SiPregunta12);
        NoPregunta12 = (RadioButton) view.findViewById(R.id.NoPregunta12);



        btnSubmitFormulario = (Button) view.findViewById(R.id.btnSubmitFormulario);

        SiPregunta1.setOnClickListener(respuesta1);
        NoPregunta1.setOnClickListener(respuesta1);


        btnSubmitFormulario.setOnClickListener(enviarFormulario);
        return view;
    }

    private View.OnClickListener respuesta1 = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Is the button now checked?
            boolean checked = ((RadioButton) v).isChecked();
            switch(v.getId()) {
                case R.id.SiPregunta1:
                    if (checked){
                        Toast.makeText(getActivity(), "Marco si", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.NoPregunta1:
                    if (checked){
                        Toast.makeText(getActivity(), "Marco no", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private int puntajePorCategoria(RadioButton rb1,RadioButton rb2,RadioButton rb3,RadioButton rb4){
        int puntaje = 0;
        if(rb1.isChecked()){
            puntaje++;
        }
        if(rb2.isChecked()){
            puntaje++;
        }
        if(rb3.isChecked()){
            puntaje++;
        }
        if(rb4.isChecked()){
            puntaje++;
        }
        return puntaje;
    }

    private View.OnClickListener enviarFormulario = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int puntajeTotal = puntajePorCategoria(SiPregunta1, SiPregunta2, SiPregunta3, SiPregunta4) +
                    puntajePorCategoria(SiPregunta5, SiPregunta6, SiPregunta7, SiPregunta8) +
                    puntajePorCategoria(SiPregunta9, SiPregunta10, SiPregunta11, SiPregunta12);
            String p = "Puntaje total: " + puntajeTotal;
            Toast.makeText(getActivity(), p, Toast.LENGTH_SHORT).show();
        }
    };
}
