package itse.isc.investigacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import itse.isc.investigacion.model.SensorData;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ITSE.MainActivity";

    private Button buttonStart;
    private Button buttonViewData;
    private TextView textViewDate;
    private TextView textViewLatitude;
    private TextView textViewLongitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializamos controles de usuario
        buttonStart             = (Button) findViewById(R.id.buttonStart);
        buttonViewData          = (Button) findViewById(R.id.buttonViewData);
        textViewDate            = (TextView) findViewById(R.id.textViewDate);
        textViewLatitude        = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude       = (TextView) findViewById(R.id.textViewLongitude);
        // En este caso, el acceso a la base de datos para visualización de la información
        // almacenada se hará en un Activity aparte

        // Habilitamos el servicio de ubicación (Google Service Localization)
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Obtenemos la información relevante y almacenamos en base de datos
                final SensorData data = new SensorData();
                data.setDateData(Calendar.getInstance().getTime());
                data.setLocationLatitude(location.getLatitude());
                data.setLocationLongitude(location.getLongitude());
                // Desplegamos en interfaz de usuario
                textViewDate.setText(String.valueOf(data.getDateData()));
                textViewLatitude.setText(String.valueOf(data.getLocationLatitude()));
                textViewLongitude.setText(String.valueOf(data.getLocationLongitude()));
                // Almacenamos la información en la base de datos (en un hilo aparte)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        App.get().getDatabase().sensorDataDao().insert(data);
                    }
                }).start();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prototipo de Aplicación", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Verificando versión de SDK y solicitando permisos
        // El botón para obtener mediciones solo se debe habilitar si tenemos permiso para
        // acceder al servicio de ubicación (location)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
            else {
                configureButton();
            }
        }
        buttonViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSensorData();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart.setText("Tomando mediciones");
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });
    }

    private void openSensorData() {
        Intent intent = new Intent(this, ViewSensorDataActivity.class);
        startActivity(intent);
    }
}
