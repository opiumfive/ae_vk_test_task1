package com.travels.searchtravels.api;

import android.graphics.Bitmap;
import com.google.api.services.vision.v1.model.LatLng;
import java.util.Random;

public class VisionApiMocked implements IVisionApi {

    private Random r = new Random();

    public void findLocation(Bitmap bitmap, String token, OnVisionApiListener onVisionApiListener) {

        int responseVariantsCounter = r.nextInt() % 10;

        switch (responseVariantsCounter) {
            case 5:
                onVisionApiListener.onErrorPlace("ocean");
                break;
            case 6:
                onVisionApiListener.onErrorPlace("snow");
                break;
            case 7:
                onVisionApiListener.onErrorPlace("mountain");
                break;
            case 8:
                onVisionApiListener.onErrorPlace("beach");
                break;
            case 9:
                onVisionApiListener.onErrorPlace("sea");
                break;
            default:
                onVisionApiListener.onSuccess(getRandomCoordsInFrance());
        }
    }

    private LatLng getRandomCoords() {
        LatLng coords = new LatLng();
        coords.setLatitude((r.nextDouble() * -180.0) + 90.0);
        coords.setLongitude((r.nextDouble() * -360.0) + 180.0);
        return coords;
    }

    public LatLng getRandomCoordsInFrance() {
        double x0 = 46.25;
        double y0 = 2.1;
        int radius = 450000;

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = r.nextDouble();
        double v = r.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        double new_x = x / Math.cos(Math.toRadians(y0));

        double foundLongitude = new_x + x0;
        double foundLatitude = y + y0;
        LatLng coords = new LatLng();
        coords.setLatitude(foundLatitude);
        coords.setLongitude(foundLongitude);
        return coords;
    }
}
