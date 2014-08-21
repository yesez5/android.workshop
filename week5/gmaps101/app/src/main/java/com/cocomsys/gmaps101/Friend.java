package com.cocomsys.gmaps101;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Programador on 21/08/2014.
 */
public class Friend {
    private int id;
    private String name;
    private String picture;

    private double lat;
    private double lng;

    public Friend(){}

    public Friend(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Friend(String name, LatLng coords){
        this.name = name;
        this.lat = coords.latitude;
        this.lng = coords.longitude;
    }

    public Friend(int id, String name, String picture, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
