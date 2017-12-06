package com.example.javie.proyecto;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngresarUsuario extends Fragment {

    Button btnIngresar;
    EditText txtUsuarioIngresar, txtContrasenaIngresar;
    TextView linkCrearCuenta;
    ProgressBar progressBar;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CONTRASENA = "contrasenaKey";
    public static final String EMAIL = "emailKey";
    SharedPreferences sharedpreferences;



    public IngresarUsuario() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ingresar_usuario, container, false);
        btnIngresar = (Button) view.findViewById(R.id.btnIngresar);
        txtUsuarioIngresar = (EditText) view.findViewById(R.id.txtUsuarioIngresar);
        txtContrasenaIngresar = (EditText) view.findViewById(R.id.txtContrasenaIngresar);
        linkCrearCuenta = (TextView) view.findViewById(R.id.linkCrearCuenta);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        linkCrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getActivity().getSupportFragmentManager();
                CrearUsuario crearUsuario = new CrearUsuario();
                manager.beginTransaction().replace(R.id.contenedor,
                        crearUsuario,
                        crearUsuario.getTag()).commit();

            }
        });
        final MainActivity myActivity = (MainActivity) getActivity();
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                  new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt", "0");
//                if(verificarUsuario()){
//                    Usuario nuevoUsuario = new Usuario();
//                    String nombre = txtUsuarioIngresar.getText().toString();
//                    String contrasenna = txtContrasenaIngresar.getText().toString();
//                    nuevoUsuario.setEmail(nombre);
//                    nuevoUsuario.setContrasena(contrasenna);
//
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                    editor.putString(EMAIL, nombre);
//                    editor.putString(CONTRASENA, contrasenna);
//                    editor.commit();
//
//                    Toast.makeText(getActivity(), nuevoUsuario.toString(),Toast.LENGTH_SHORT).show();
//
//                    progressBar.setVisibility(View.VISIBLE);// To Show ProgressBar
//
//                   //Espera 3 segundos para cambiar de pantalla
//                    Handler handler = new Handler();
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            //Second fragment after 5 seconds appears
//                            progressBar.setVisibility(View.INVISIBLE);
//                            FragmentManager manager = getActivity().getSupportFragmentManager();
//                            Inicio inicio = new Inicio();
//                            manager.beginTransaction().replace(R.id.contenedor,
//                                    inicio,
//                                    inicio.getTag()).commit();
//                        }
//                    };
//                    handler.postDelayed(runnable, 3000);
//
//                }
//               else{
//                    Toast.makeText(getActivity(), "Por favor, complete los campos",Toast.LENGTH_SHORT).show();
//               }

            }
        });

        return view;
    }

    private boolean verificarUsuario(){
        String nombre = txtUsuarioIngresar.getText().toString();
        String contrasenna = txtContrasenaIngresar.getText().toString();
        if(TextUtils.isEmpty(nombre)) {
            txtUsuarioIngresar.setError("Ingrese un usuario!");
            return false;
        }
        else if(TextUtils.isEmpty(contrasenna)) {
            txtContrasenaIngresar.setError("Ingrese una contrasena!");
            return false;
        }
        else return true;
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();


                String line = "";
                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArrey = parentObject.getJSONArray("movies");
                JSONObject finalObject = parentArrey.getJSONObject(0);
                String moviename = finalObject.getString("movie");
                int year = finalObject.getInt("year");
                if(params[1] == "1") {

                    return moviename + " - " + year;
                }
                else {
                    return year + " - " + moviename;
                }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                if(reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            btnIngresar.setText(result);
        }
    }


}
