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
public class SimpleGoogleLocationManager extends BaseGoogleLocationManager {

    private OnSimpleLocationListener mResponseListener;
    private Location mLastLocation;

    private static final int SECONDS = 2;
    private static final int DISPLACEMENT = 0;

    public Location getLastLocation() {
        return mLastLocation;
    }

    public SimpleGoogleLocationManager(Context ctx, OnSimpleLocationListener listener){
        super(ctx, SECONDS, DISPLACEMENT);
        mResponseListener = listener;
        mPriority = LocationRequest.PRIORITY_LOW_POWER;

        build(mCtx, true, SECONDS, DISPLACEMENT, mPriority, mResponseListener);
    }

    public SimpleGoogleLocationManager(Context ctx, int priority, OnSimpleLocationListener listener){
        super(ctx, SECONDS, DISPLACEMENT, priority);
        mResponseListener = listener;
        build(mCtx, true, SECONDS, DISPLACEMENT, mPriority, mResponseListener);
    }

    public void build(Context ctx, boolean connect, int interval, int displacement, int priority, final OnSimpleLocationListener responseListener){
        if(!LocationUtils.isGoogleServicesAvailable(ctx)){
            responseListener.onError(3);
            return;
        }

        if(!LocationUtils.isLocationServiceEnabled(ctx)){
            responseListener.onError(4);
        }

        super.build(interval, displacement, priority);
        if(connect){
            connect();
        }
    }

    @Override
    public void buildLocationListener() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLastLocation = location;
                mResponseListener.onSuccess(location);
                removeUpdates();
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
                mResponseListener.onError(1);
            }
        }, new GooglePlayServicesClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                mResponseListener.onError(2);
            }
        });
    }

    public interface OnSimpleLocationListener {
        void onSuccess(Location location);
        void onError(int reason); //TODO: cambiar reason de int a una custom class con código de error, descripción para dev y para user
    }
}
