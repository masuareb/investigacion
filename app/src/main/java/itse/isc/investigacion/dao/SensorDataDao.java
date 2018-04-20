package itse.isc.investigacion.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import itse.isc.investigacion.converters.DateTypeConverter;
import itse.isc.investigacion.model.SensorData;

/**
 * Created by Arturo2 on 12/02/2018.
 */

@Dao
public interface SensorDataDao {
    @Query("SELECT * FROM sensor_data")
    List<SensorData> getAll();

    @Query("SELECT * FROM sensor_data WHERE id IN (:sensorIds)")
    List<SensorData> loadAllByIds(int[] sensorIds);

    @Query("SELECT * FROM sensor_data WHERE date_data = :date")
    @TypeConverters({DateTypeConverter.class})
    List<SensorData> loadAllByDate(Date date);

    @Insert
    void insertAll(SensorData... data);

    @Insert
    void insert(SensorData data);

    @Delete
    void delete(SensorData data);

}
