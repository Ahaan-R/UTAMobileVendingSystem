package com.example.utamobilevendingsystem;

import android.content.Context;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.List;

public class VehicleListAdapter extends ArrayAdapter<Vehicle> {

    private Context mContext;
    int mResource;

    public VehicleListAdapter(@NonNull Context context, int resource, @NonNull List<Vehicle> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String vehicleName = getItem(position).getVehicleName();
        int location = getItem(position).getLocationId();
        VehicleType type = getItem(position).getVehicleType();
        Vehicle v = new Vehicle(vehicleName,type, Status.AVAILABLE,1);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.vehicleName);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.vehicleLocation);
        TextView tvType = (TextView) convertView.findViewById(R.id.vehicleType);

        tvName.setText(vehicleName);
        tvLocation.setText("Location: "+String.valueOf(location));
        tvType.setText(String.valueOf(type.name()));

        return convertView;
    }
}
