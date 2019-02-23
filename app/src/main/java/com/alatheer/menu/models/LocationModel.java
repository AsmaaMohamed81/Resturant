package com.alatheer.menu.models;

import java.io.Serializable;

/**
 * Created by elashry on 27/09/2018.
 */

public class LocationModel implements Serializable{
    private double lat;
    private double lng;

    public LocationModel(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
