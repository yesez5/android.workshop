package com.cocomsys.gmaps101;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;

public class LowBatteryLocationActivity extends ActionBarActivity {

    private static final String TAG = LowBatteryLocationActivity.class.getSimpleName();
    LocationUtils locationManager;
    Location currentLocation;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_battery_location);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reconnectLocationIfApply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeLocationUpdates();
    }

    private void init() {
        if(!LocationUtils.isGoogleServicesAvailable(this)) {
            showMessage("ocurrió un error al obtener los servicios de Google");
            return;
        }
        tvData = (TextView)findViewById(R.id.tv_data);
        configLocationService();
    }

    private void configLocationService(){
        if(!LocationUtils.isLocationServiceEnabled(this)){
            String msg = "location service disabled";
            Log.e(TAG, msg);
            showMessage(msg);
            return;
        }

        if(LocationUtils.isGoogleServicesAvailable(this)){
            locationManager.connect();
        }else{
            showMessage("ocurrió un error al conectar");
            Log.e(TAG, "service unavailable");
            return;
        }

        locationManager = new LocationUtils(this, 5, 0,
                LocationRequest.PRIORITY_LOW_POWER,
                new LocationUtils.OnLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        updateLocation(location);
                    }

                    @Override
                    public void onDisconnected() {
                        showMessage("disconnected");
                    }

                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        showMessage("connection failed");
                    }
                });

        boolean isConnecting = locationManager.isConnecting();
        boolean isConnected = locationManager.isConnected();
        if(!isConnecting && !isConnected){
            showMessage("ocurrió un error al conectar");
        }
    }

    private void updateLocation(Location location){
        String coordsString = location.getLatitude() + ":" + location.getLongitude();
        Log.i(TAG, "location changed: " + coordsString);
        tvData.setText(coordsString);

        currentLocation = location;
        removeLocationUpdates();
    }

    private void reconnectLocationIfApply(){
        if(locationManager != null && currentLocation == null){
            locationManager.reconnectIfApply();
        }
    }

    private void removeLocationUpdates(){
        if(locationManager != null)
            locationManager.removeUpdates();
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
