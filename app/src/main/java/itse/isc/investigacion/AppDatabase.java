package itse.isc.investigacion;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import itse.isc.investigacion.dao.SensorDataDao;
import itse.isc.investigacion.dao.UserDao;
import itse.isc.investigacion.model.SensorData;
import itse.isc.investigacion.model.User;

/**
 * Created by Arturo2 on 12/02/2018.
 * v1.- Initial Schema
 * v2.- Autogenerate ID of SensorData
 */
@Database(entities = {User.class, SensorData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract SensorDataDao sensorDataDao();
}
