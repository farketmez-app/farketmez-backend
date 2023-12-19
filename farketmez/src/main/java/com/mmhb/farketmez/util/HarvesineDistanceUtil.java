package com.mmhb.farketmez.util;

import java.util.List;

public class HarvesineDistanceUtil {

    private static final double EARTH_RADIUS_KM = 6371; // Earth radius in kilometers

    public static BoundingBox calculateBoundingBox(double centerLatitude, double centerLongitude, double distanceInKm) {
        double radDistance = distanceInKm / EARTH_RADIUS_KM;

        double radCenterLat = Math.toRadians(centerLatitude);
        double radCenterLon = Math.toRadians(centerLongitude);

        double minLat = radCenterLat - radDistance;
        double maxLat = radCenterLat + radDistance;

        double deltaLon = Math.asin(Math.sin(radDistance) / Math.cos(radCenterLat));

        double minLon = radCenterLon - deltaLon;
        double maxLon = radCenterLon + deltaLon;

        // Convert back to degrees
        minLat = Math.toDegrees(minLat);
        maxLat = Math.toDegrees(maxLat);
        minLon = Math.toDegrees(minLon);
        maxLon = Math.toDegrees(maxLon);

        return new BoundingBox(minLat, maxLat, minLon, maxLon);
    }

    public static class BoundingBox {
        private final double minLatitude;
        private final double maxLatitude;
        private final double minLongitude;
        private final double maxLongitude;

        public BoundingBox(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
            this.minLatitude = minLatitude;
            this.maxLatitude = maxLatitude;
            this.minLongitude = minLongitude;
            this.maxLongitude = maxLongitude;
        }

        public double getMinLatitude() {
            return minLatitude;
        }

        public double getMaxLatitude() {
            return maxLatitude;
        }

        public double getMinLongitude() {
            return minLongitude;
        }

        public double getMaxLongitude() {
            return maxLongitude;
        }

        @Override
        public String toString() {
            return "BoundingBox{" +
                    "minLatitude=" + minLatitude +
                    ", maxLatitude=" + maxLatitude +
                    ", minLongitude=" + minLongitude +
                    ", maxLongitude=" + maxLongitude +
                    '}';
        }
    }
}
