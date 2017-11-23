package com.example.escinf.labservlets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    TextView txtMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMensaje = (TextView) findViewById(R.id.txtMensaje);

    }
    public String PostData(View v) {
        String msg="";
        try
        {
            msg = txtMensaje.getText().toString();
            URL url =new URL("http://localhost:8080/Servlet/servlet?msg="+msg);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.disconnect();
        }
        catch(Exception exception)  {}
        return msg;
    }




}
