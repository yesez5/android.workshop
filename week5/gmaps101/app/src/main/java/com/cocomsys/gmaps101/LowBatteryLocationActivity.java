package com.cocomsys.gmaps101;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LowBatteryLocationActivity extends ActionBarActivity {

    private static final String TAG = LowBatteryLocationActivity.class.getSimpleName();
    SimpleGoogleLocationManager locationManager;
    Location currentLocation;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_battery_location);
        init();
    }

    private void init() {
        tvData = (TextView)findViewById(R.id.tv_data);
        locationManager = new SimpleGoogleLocationManager(this,
                new SimpleGoogleLocationManager.OnSimpleLocationListener() {
            @Override
            public void onSuccess(Location location) {
                updateLocation(location);
            }

            @Override
            public void onError(int reason) {
                showMessage("something went wrong " + reason);
            }
        });
    }

    private void updateLocation(Location location){
        String coordsString = location.getLatitude() + ":" + location.getLongitude();
        Log.i(TAG, "location changed: " + coordsString);
        tvData.setText(coordsString);
        currentLocation = location;
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
