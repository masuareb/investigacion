package itse.isc.investigacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import itse.isc.investigacion.adapters.SensorDataAdapter;
import itse.isc.investigacion.model.SensorData;

public class ViewSensorDataActivity extends AppCompatActivity {
    private static final String TAG = "ITSE.ViewSensorData";
    public static final int REQUEST_WRITE_STORAGE = 112;

    RecyclerView recyclerView;
    Button buttonExportData;

    private final String filenameInternal = "couponsFile";
    private final String filenameExternal = "cashbackFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensor_data);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        buttonExportData = (Button) findViewById(R.id.buttonExportData);

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

        buttonExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Exportar datos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Obtenemos la información almacenada en la base de datos
                        List<SensorData> data = App.get().getDatabase().sensorDataDao().getAll();
                        // Visualizamos la información
                        exportData(data);
                    }
                }).start();                                    ;
            }
        });
    }

    private void populateData(final List<SensorData> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new SensorDataAdapter(data));
            }
        });
    }

    private void exportData(final List<SensorData> data) {
        Log.e(TAG, "Exportando datos");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE);
                return;
            }
            else {
                writeFileExternalStorage(data);
            }
        }
    }

    public void writeFileExternalStorage(final List<SensorData> data) {
        String state = Environment.getExternalStorageState();
        //external storage availability check
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        String publicDcimDirPath = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);
        String fileData = "Fecha,Latitud,Longitud\n";
        for (SensorData d: data) {
            fileData = fileData + d.toString() + "\n";
        }

        File newFile = new File(publicDcimDirPath, "investigacion.txt");
        try {
            FileWriter fw = new FileWriter(newFile);

            fw.write(fileData);
            fw.flush();
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
