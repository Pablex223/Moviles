package com.example.pablo.lab9db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnEst;
    Button btnCur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Matricula");

        btnCur=(Button) findViewById(R.id.botonCursos);
        btnEst= (Button) findViewById(R.id.botonEstudiantes);





    }

}

