package com.cocomsys.gmaps101;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by Programador on 25/08/2014.
 */
public class GoogleLocationManager extends BaseGoogleLocationManager {

    private OnLocationListener mResponseListener;

    public GoogleLocationManager(Context ctx, int intervalInSeconds,
                                 int displacementInMeters, OnLocationListener mResponseListener) {
        super(ctx, intervalInSeconds, displacementInMeters);
        this.mResponseListener = mResponseListener;
        mPriority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;

        build();
    }

    public GoogleLocationManager(Context ctx, int intervalInSeconds, int displacementInMeters,
                                 int priority, OnLocationListener mResponseListener) {
        super(ctx, intervalInSeconds, displacementInMeters, priority);
        this.mResponseListener = mResponseListener;

        build();
    }

    @Override
    public void buildLocationListener() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mResponseListener.onLocationChanged(location);
            }
        };
        requestUpdates();
    }

    @Override
    protected void buildLocationClient() {
        mLocationClient = new LocationClient(mCtx, new GooglePlayServicesClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                buildLocationListener();
            }
            @Override
            public void onDisconnected() {
                mResponseListener.onDisconnected();
            }
        }, new GooglePlayServicesClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                mResponseListener.onConnectionFailed(connectionResult);
            }
        });
    }

    public interface OnLocationListener {
        void onLocationChanged(Location location);
        void onDisconnected();
        void onConnectionFailed(ConnectionResult connectionResult);
    }
}
