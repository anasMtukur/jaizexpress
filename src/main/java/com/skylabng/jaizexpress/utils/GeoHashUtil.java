package com.skylabng.jaizexpress.utils;

import ch.hsr.geohash.GeoHash;

public class GeoHashUtil {
    private final static int PRECISION = 12;
    public static String generateGeoHash( double latitude, double longitude ){

        return GeoHash.withCharacterPrecision(latitude, longitude, PRECISION).toBase32();
    }

}
