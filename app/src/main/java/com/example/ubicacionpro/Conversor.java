package com.example.ubicacionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Conversor extends AppCompatActivity {
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);
        getSupportActionBar().hide();
        texto = findViewById(R.id.textoMenu);
        String RLongi = getIntent().getStringExtra("Latitud");
        String RLati = getIntent().getStringExtra("Longitud");
        String RCiudad = getIntent().getStringExtra("ciudad");
        String RCalles = getIntent().getStringExtra("calles");
        String RPais = getIntent().getStringExtra("pais");

        JSONArray jsonArray = new JSONArray();
        JSONObject latiJ, LongiJ, CallesJ, CiudadJ, PaisJ;
        latiJ = new JSONObject();
        LongiJ= new JSONObject();
        CallesJ= new JSONObject();
        CiudadJ= new JSONObject();
        PaisJ= new JSONObject();
        try {
            latiJ.put("Latitud", RLati);
            LongiJ.put("Longitud", RLongi);
            CallesJ.put("Dirección", RCalles);
            CiudadJ.put("Ciudad", RCiudad);
            PaisJ.put("Pais", RPais);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(latiJ);
        jsonArray.put(LongiJ);
        jsonArray.put(CallesJ);
        jsonArray.put(CiudadJ);
        jsonArray.put(PaisJ);

        String textoArr = jsonArray.toString();
        FileOutputStream fo = null;
        try {
            fo = openFileOutput("objeto", MODE_PRIVATE);
            fo.write(textoArr.getBytes());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        texto.setText(jsonArray.toString());
        Toast.makeText(this, "Mostrando objetos almacenados",
                Toast.LENGTH_SHORT).show();
    }

    public void regresar(View view){
        Intent pPrincipal = new Intent(this, MENU.class);
        startActivity(pPrincipal);
    }
}