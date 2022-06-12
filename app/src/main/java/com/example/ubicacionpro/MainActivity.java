package com.example.ubicacionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        TimerTask SPS = new TimerTask() {
            @Override
            public void run() {
                Intent pPrincipal = new Intent(MainActivity.this, MENU.class);
                startActivity(pPrincipal);
                finish();
            }
        };
        Timer time = new Timer();
        time.schedule(SPS, 3000);
    }
}