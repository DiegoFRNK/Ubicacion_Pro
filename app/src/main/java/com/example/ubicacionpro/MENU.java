package com.example.ubicacionpro;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MENU extends AppCompatActivity {
    WifiManager admini_wifi;
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation, abrirconversor;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;

    private ProgressBar carga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        admini_wifi=(WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocation = findViewById(R.id.showLocation);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        abrirconversor = findViewById(R.id.btnJson);
        carga= (ProgressBar)findViewById(R.id.progressBar);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setestadowifi();
                carga.setVisibility(View.VISIBLE);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            OnGPS();
                        }
                    }, 5000);


                } else {
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            getLocation();
                        }
                    }, 5000);
                }
            }
        });
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void setestadowifi() {
        if (!admini_wifi.isWifiEnabled()) {
            admini_wifi.setWifiEnabled(true);
            Toast toast1 = Toast.makeText(this, "WiFi encendido", Toast.LENGTH_LONG);
            toast1.show();
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MENU.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MENU.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                carga.setVisibility(View.GONE);

                /*<Address> addresses = new ArrayList<Address>();
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                String city = "unknown location";
                String country = "unknown location";
                String address = "unknown address";
                try {
                    city = addresses.get(0).getAddressLine(1);
                    country = addresses.get(0).getAddressLine(2);
                    address = addresses.get(0).getAddressLine(0);
                } catch (Exception e) {
                    city = "unknown location";
                    country = "unknown location";
                    address = "unknown address";
                }*/

                showLocation.setText("Tu localizacion es: " + "\n" + "Latitud: " + latitude + "\n" + "Longitud: " + longitude/* + "\n" +"Pais: " + country+ "\n" +"Ciudad: " + city + "\n" + "Dirección: " + address*/);
            } else {
                carga.setVisibility(View.GONE);
                Toast.makeText(this, "No podemos encontrarte", Toast.LENGTH_SHORT).show();
                showLocation.setText("Localización desconocida");
            }
        }
    }

    public void enviarMensaje(View view) {
        if (ActivityCompat.checkSelfPermission(
                MENU.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MENU.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                carga.setVisibility(View.GONE);

                /*List<Address> addresses = new ArrayList<Address>();
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                String city = "unknown location";
                String country = "unknown location";
                String address = "unknown address";
                try {
                    city = addresses.get(0).getAddressLine(1);
                    country = addresses.get(0).getAddressLine(2);
                    address = addresses.get(0).getAddressLine(0);
                } catch (Exception e) {
                    city = "unknown location";
                    country = "unknown location";
                    address = "unknown address";
                }*/

            }

        }
        Intent intent = new Intent(this, Conversor.class);
        intent.putExtra("Latitud", latitude);
        intent.putExtra("Longitud", longitude);
                /*intent.putExtra("ciudad", city);
                intent.putExtra("calles", address);
                intent.putExtra("pais", country);*/
        startActivity(intent);
    }
}

