package com.cocomsys.gmaps101;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    GoogleMap mainMap;
    SupportMapFragment mapFragment;
    ArrayList<Friend> friends;
    private HashMap<Marker, Friend> markersMap;
    private Marker userMarker;
    private Friend currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        markersMap = new HashMap<Marker, Friend>();
        friends = buildFriendsList();
        configMap();
        LatLng defaultCoords = getDefaultCoords();

        setMapPosition(mainMap, defaultCoords);
        addMarkers(friends, mainMap);

        mainMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

        String userTitle = getString(R.string.you_are_here);
        userMarker = setUserMarker(mainMap, defaultCoords, userTitle);
        currentUser = new Friend(userTitle, defaultCoords);
        friends.add(currentUser);
        markersMap.put(userMarker, currentUser);
    }

    private void configMap() {
        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment == null) return;
        if (mainMap == null) {
            mainMap = mapFragment.getMap();
        }

        if (mainMap == null) {
            showMessage(getString(R.string.error_creating_map));
            return;
        }

        mainMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        mainMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                showActionsDialog(marker);
            }
        });
    }

    private void showActionsDialog(final Marker marker) {
        final Friend model = markersMap.get(marker);
        if(model == null) return;
        if(model == currentUser) return;

        String[] items = {getString(R.string.visit), getString(R.string.view_profile)};
        AlertDialog.Builder actionsDialog = new AlertDialog.Builder(this);
        actionsDialog.setTitle(R.string.actions);
        actionsDialog.setCancelable(true);
        actionsDialog.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showMessage("visitar a " + model.getName());
                        break;
                    case 1:
                        showMessage("perfil de " + model.getName());
                        break;
                }
            }
        });
        actionsDialog.show();
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setMapPosition(GoogleMap map, LatLng coords) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 10));
    }

    private ArrayList<Friend> buildFriendsList() {
        ArrayList<Friend> list = new ArrayList<Friend>();

        list.add(new Friend("José Martinez", 12.077239, -86.280441));
        list.add(new Friend("Carlos Lenon", 12.157801, -86.351852));
        list.add(new Friend("Wendy Hernández", 12.040978, -86.207657));

        return list;
    }

    private LatLng getDefaultCoords() {
        return generateCoord(12.106781, -86.229630);
    }

    private Marker setUserMarker(GoogleMap map, LatLng coords, String title) {
        return buildMarker(map, title, coords,
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    }

    private void addMarkers(ArrayList<Friend> list, GoogleMap map) {
        if (list == null || list.isEmpty()) return;
        for (Friend model : list) {
            Marker currentMarker = buildMarker(map, model.getName(),
                    generateCoord(model.getLat(), model.getLng()), null);
            markersMap.put(currentMarker, model);
        }
    }

    private Marker buildMarker(GoogleMap map, String title, LatLng coords, BitmapDescriptor icon) {
        Marker marker = null;
        MarkerOptions options = new MarkerOptions().position(coords);

        if (!TextUtils.isEmpty(title))
            options.title(title);
        if (icon != null)
            options.icon(icon);

        if (map != null)
            marker = map.addMarker(options);
        return marker;
    }

    private LatLng generateCoord(double lat, double lng) {
        return new LatLng(lat, lng);
    }

    private View buildWindowView(View v, Friend model) {
        TextView markerLabel = (TextView) v.findViewById(R.id.tv_marker_label);
        Button btnActions = (Button)v.findViewById(R.id.btn_actions);

        String label = model != null && !TextUtils.isEmpty(model.getName()) ? model.getName() : "unknown";
        markerLabel.setText(label);

        if(model != null && model == currentUser)
            btnActions.setVisibility(View.GONE);

        return v;
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        public MarkerInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.map_window_layout, null);
            Friend model = markersMap.get(marker);
            v = buildWindowView(v, model);
            return v;
        }
    }

}
