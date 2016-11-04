package com.jctechcloud.entity;

/**
 * Region entity
 * <p>
 * Created by jcincera on 04/11/2016.
 */
public class Region {

    private String id;
    private String name;
    private Double geoLat;
    private Double geoLon;

    public Region(String id, String name, Double geoLat, Double geoLon) {
        this.id = id;
        this.name = name;
        this.geoLat = geoLat;
        this.geoLon = geoLon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public Double getGeoLon() {
        return geoLon;
    }
}
