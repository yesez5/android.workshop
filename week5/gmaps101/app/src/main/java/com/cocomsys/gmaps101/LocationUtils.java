package com.cocomsys.gmaps101;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Programador on 22/08/2014.
 */
public class LocationUtils {

    private static final String TAG = LocationUtils.class.getSimpleName();
    private OnLocationListener mListener;
    private int mInterval;
    private int mDisplacement;
    private int mPriority;
    private Context mCtx;

    private static LocationRequest mLocationRequest;
    private static LocationClient mLocationClient;
    private LocationListener mLocationListener;
    private boolean isRequestingUpdates;

    public static boolean isGoogleServicesAvailable(Context ctx){
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        if (status == ConnectionResult.SUCCESS)
            return true;
        return false;
    }

    public static LatLng buildLatLng(Location location){
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public LocationUtils(){}

    public LocationUtils(Context ctx, int intervalInSeconds, int displacementInMeters, OnLocationListener listener){
        mCtx = ctx;
        mInterval = intervalInSeconds;
        mDisplacement = displacementInMeters;
        mListener = listener;

        configLocationComponents(mInterval, mDisplacement, mListener);
    }

    public LocationUtils(Context ctx, int intervalInSeconds, int displacementInMeters, int priority, OnLocationListener listener){
        mCtx = ctx;
        mInterval = intervalInSeconds;
        mDisplacement = displacementInMeters;
        mPriority = priority;
        mListener = listener;

        configLocationComponents();
    }

    private void configLocationComponents(){
        configLocationComponents(mInterval, mDisplacement,
                mPriority, mListener);
    }

    private void configLocationComponents(int intervalInSeconds, int displacementInMeters,
                                          OnLocationListener locationListener){
        configLocationComponents(intervalInSeconds, displacementInMeters,
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, locationListener);
    }

    private void configLocationComponents(int intervalInSeconds, int displacementInMeters, int priority,
                                          final OnLocationListener locationListener){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(intervalInSeconds * 1000);
        mLocationRequest.setSmallestDisplacement(displacementInMeters);
        mLocationRequest.setPriority(priority);
        mLocationClient = new LocationClient(mCtx, new GooglePlayServicesClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                buildLocationListener();
            }
            @Override
            public void onDisconnected() {
                locationListener.onDisconnected();
            }
        }, new GooglePlayServicesClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                locationListener.onConnectionFailed(connectionResult);
            }
        });
    }

    public void buildLocationListener(){
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mListener.onLocationChanged(location);
            }
        };
        requestUpdates();
    }

    public void requestUpdates(){
        mLocationClient.requestLocationUpdates(mLocationRequest, mLocationListener);
        isRequestingUpdates = true;
    }

    public void removeUpdates(){
        if(mLocationClient == null) return;
        mLocationClient.removeLocationUpdates(mLocationListener);
        isRequestingUpdates = false;
        //mLocationListener = null;
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

    public interface OnLocationListener {
        void onLocationChanged(Location location);
        void onDisconnected();
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    //region location service
    private static LocationManager generateManager(Context ctx){
        return (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }

    public static boolean isLocationServiceEnabled(Context ctx) {
        LocationManager manager = generateManager(ctx);
        return isKitKat()
                ? isKitKatLocationEnabled(ctx)
                : (isNetworkEnabled(manager) || isGpsEnabled(ctx, manager));
    }

    public static boolean isGpsEnabled(Context ctx){
        return isGpsEnabled(ctx, generateManager(ctx));
    }

    public static boolean isGpsEnabled(Context ctx, LocationManager manager) {
        if (!isKitKat()) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            return isKitKatLocationEnabled(ctx);
        }
    }

    public static boolean isNetworkEnabled(Context ctx){
        return isNetworkEnabled(generateManager(ctx));
    }

    public static boolean isNetworkEnabled(LocationManager manager) {
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isKitKatLocationEnabled(Context ctx){
        final int locationMode;
        try {
            locationMode = Settings.Secure.getInt(ctx.getContentResolver(),
                    Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        switch (locationMode) {
            case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
            case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
                return true;
            case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
            case Settings.Secure.LOCATION_MODE_OFF:
            default:
                return false;
        }
    }

    private static boolean isKitKat(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
    //endregion
}
