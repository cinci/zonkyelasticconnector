package com.jctechcloud.entity;

/**
 * Region geo
 * <p>
 * Created by jcincera on 04/11/2016.
 */
public class RegionGeo {

    private Double lat;
    private Double lon;

    public RegionGeo(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
