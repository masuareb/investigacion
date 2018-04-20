package itse.isc.investigacion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itse.isc.investigacion.R;
import itse.isc.investigacion.model.SensorData;

/**
 * Created by Arturo2 on 13/02/2018.
 */

public class SensorDataAdapter extends RecyclerView.Adapter<SensorDataAdapter.SensorDataViewHolder> {
    private final List<SensorData> data;

    public SensorDataAdapter(List<SensorData> data) {
        this.data = data;
    }

    @Override
    public SensorDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false);
        return new SensorDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SensorDataViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SensorDataViewHolder extends RecyclerView.ViewHolder {
        private TextView dateData;
        private TextView locationData;
        private TextView accelerationData;

        public SensorDataViewHolder(View itemView) {
            super(itemView);
            dateData = itemView.findViewById(R.id.date_data);
            locationData = itemView.findViewById(R.id.location_data);
            accelerationData = itemView.findViewById(R.id.acceleration_data);
        }

        public void bind(SensorData data) {
            String str_date_data = String.valueOf(data.getDateData());
            String str_loc_data = String.valueOf(data.getLocationLatitude()) + ", " +
                    String.valueOf(data.getLocationLongitude());
            String str_acc_data = String.valueOf(data.getAccelerometerX()) + ", " +
                    String.valueOf(data.getAccelerometerY());
            dateData.setText(str_date_data);
            locationData.setText(str_loc_data);
            accelerationData.setText(str_acc_data);
        }
    }
}
