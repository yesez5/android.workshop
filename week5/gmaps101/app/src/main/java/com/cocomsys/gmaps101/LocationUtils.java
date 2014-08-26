package com.cocomsys.gmaps101;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Programador on 22/08/2014.
 */
public class LocationUtils {

    private static final String TAG = LocationUtils.class.getSimpleName();

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
            case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
                return true;
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
