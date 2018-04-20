package itse.isc.investigacion;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by Arturo2 on 13/02/2018.
 */

public class App extends Application {
    public static App INSTANCE;
    private static final String DATABASE_NAME = "ITSE.AppInvestigacion";

    private AppDatabase database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Database creation
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        INSTANCE = this;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
