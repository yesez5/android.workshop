package com.cocomsys.gmaps101;

import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Programador on 22/08/2014.
 */
public class AnimationUtils {

    private static final String TAG = AnimationUtils.class.getSimpleName();
    private boolean isListenerExecuted;

    //source: http://android-er.blogspot.hk/2013/01/implement-bouncing-marker-for-google.html
    public void animateMarker(GoogleMap map, final Marker marker, final OnAnimListener listener){
        isListenerExecuted = false;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        final LatLng markerCoords = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerCoords);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerCoords.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerCoords.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                final double limit = 1.0;
                if (t < limit) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
                if(t > (limit / 2)){
                    if(!isListenerExecuted)
                        listener.onStartDelayed();
                    isListenerExecuted = true;
                }
            }
        });
    }

    public interface OnAnimListener {
        void onStartDelayed();
    }

}
