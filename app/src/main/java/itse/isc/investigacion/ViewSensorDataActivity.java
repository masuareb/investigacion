package itse.isc.investigacion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import itse.isc.investigacion.adapters.SensorDataAdapter;
import itse.isc.investigacion.model.SensorData;

public class ViewSensorDataActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensor_data);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Habilitamos el acceso a la base de datos (en un hilo aparte)
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Obtenemos la información almacenada en la base de datos
                List<SensorData> data = App.get().getDatabase().sensorDataDao().getAll();
                // Visualizamos la información
                populateData(data);
            }
        }).start();                                    ;

    }

    private void populateData(final List<SensorData> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new SensorDataAdapter(data));
            }
        });
    }
}
