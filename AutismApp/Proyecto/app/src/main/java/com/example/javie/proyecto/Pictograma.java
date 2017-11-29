package com.example.javie.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by javie on 11/22/2017.
 */

public class Pictograma extends Fragment {

    Button btnRespuest1,btnRespuest2,btnRespuest3,btnRespuest4;

    public Pictograma() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictograma, container, false);
        btnRespuest1 = (Button) view.findViewById(R.id.btnRespuesta1);
        btnRespuest2 = (Button) view.findViewById(R.id.btnRespuesta2);
        btnRespuest3 = (Button) view.findViewById(R.id.btnRespuesta3);
        btnRespuest4 = (Button) view.findViewById(R.id.btnRespuesta4);


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

        }
    };


}
