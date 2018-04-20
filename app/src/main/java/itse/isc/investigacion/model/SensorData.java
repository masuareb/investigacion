package itse.isc.investigacion.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import itse.isc.investigacion.converters.DateTypeConverter;

/**
 * Created by Arturo2 on 12/02/2018.
 */

@Entity(tableName = "sensor_data")
public class SensorData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date_data")
    @TypeConverters({DateTypeConverter.class})
    public Date dateData;

    // Location data
    @ColumnInfo(name = "location_latitude")
    public double locationLatitude;

    @ColumnInfo(name = "location_longitude")
    public double locationLongitude;

    // Acelerometer data
    @ColumnInfo(name = "accelerometer_x")
    public double accelerometerX;

    @ColumnInfo(name = "accelerometer_y")
    public double accelerometerY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateData() {
        return dateData;
    }

    public void setDateData(Date dateData) {
        this.dateData = dateData;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public double getAccelerometerX() {
        return accelerometerX;
    }

    public void setAccelerometerX(double accelerometerX) {
        this.accelerometerX = accelerometerX;
    }

    public double getAccelerometerY() {
        return accelerometerY;
    }

    public void setAccelerometerY(double accelerometerY) {
        this.accelerometerY = accelerometerY;
    }
}

