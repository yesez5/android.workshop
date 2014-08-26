package com.cocomsys.gmaps101;

import android.content.Context;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by Programador on 25/08/2014.
 */
public abstract class BaseGoogleLocationManager {

    protected int mPriority;
    protected int mInterval;
    protected int mDisplacement;
    protected Context mCtx;
    protected static LocationRequest mLocationRequest;
    protected static LocationClient mLocationClient;
    protected LocationListener mLocationListener;
    protected boolean isRequestingUpdates;

    public abstract void buildLocationListener();
    protected abstract void buildLocationClient();

    public BaseGoogleLocationManager(Context ctx, int intervalInSeconds, int displacementInMeters){
        mCtx = ctx;
        mInterval = intervalInSeconds;
        mDisplacement = displacementInMeters;
    }

    public BaseGoogleLocationManager(Context ctx, int intervalInSeconds, int displacementInMeters, int priority){
        mCtx = ctx;
        mInterval = intervalInSeconds;
        mDisplacement = displacementInMeters;
        mPriority = priority;
    }

    public void build(){
        build(mInterval, mDisplacement, mPriority);
    }

    public void build(int intervalInSeconds, int displacementInMeters, int priority){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(intervalInSeconds * 1000);
        mLocationRequest.setSmallestDisplacement(displacementInMeters);
        mLocationRequest.setPriority(priority);

        buildLocationClient();
    }

    public void requestUpdates(){
        mLocationClient.requestLocationUpdates(mLocationRequest, mLocationListener);
        isRequestingUpdates = true;
    }

    public void removeUpdates(){
        if(mLocationClient == null) return;
        mLocationClient.removeLocationUpdates(mLocationListener);
        isRequestingUpdates = false;
    }

    public void connect(){
        connect(false);
    }

    public void connect(boolean force){
        if(mLocationClient == null) return;
        boolean isConnecting = isConnecting();
        boolean isConnected = isConnected();
        if(force || (!isConnecting && !isConnected)){
            mLocationClient.connect();
        }
    }

    public void reconnectIfApply() {
        if (isConnected()){
            if (mLocationListener == null && !isRequestingUpdates) {
                buildLocationListener();
            }else{
                requestUpdates();
            }
        } else{
            connect();
        }
    }

    public void disconnect(){
        if(mLocationClient == null) return;
        mLocationClient.disconnect();
    }

    public boolean isConnected(){
        return mLocationClient.isConnected();
    }

    public boolean isConnecting(){
        return mLocationClient.isConnecting();
    }

}
