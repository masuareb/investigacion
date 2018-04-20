package itse.isc.investigacion.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Arturo2 on 12/02/2018.
 */

public class DateTypeConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
