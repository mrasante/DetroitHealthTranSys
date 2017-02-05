package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.util.Log;

import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by kwas7493 on 1/20/2017.
 */

public class GeoLocator {
    static Locator locator;
    private static final String ONLINE_LOCATOR_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";

    public static Locator getLocator(String locatorURL) {
        locator = Locator.createOnlineLocator(locatorURL); //, new UserCredentials());
        return locator;
    }


    public static Point getGeocodePoint(String address) {
        Point point = null;

        try {
            final Future<List<LocatorGeocodeResult>> futureListLocatorResults = Locator.findLocation(address, getLocator(ONLINE_LOCATOR_URL),
                    new CallbackListener<List<LocatorGeocodeResult>>() {
                        @Override
                        public void onCallback(List<LocatorGeocodeResult> locatorGeocodeResults) {
                            for (LocatorGeocodeResult results : locatorGeocodeResults) {
                                Point point = results.getLocation();
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("Error Locating", throwable.getMessage());
                        }
                    });

            List<LocatorGeocodeResult> locatorGeocodeResultList = futureListLocatorResults.get();

            for (LocatorGeocodeResult locatorGeocodeResult : locatorGeocodeResultList) {
                if(locatorGeocodeResult.getAddress().equalsIgnoreCase(address) && locatorGeocodeResult.getScore() == 100){
                    point = locatorGeocodeResult.getLocation();
                    break;
                }

            }
        } catch (ExecutionException | InterruptedException ex) {
            Log.e("Execution Exception: ", ex.getMessage());
        }
        if (point == null) {
            return null;
        } else
            return point;
    }
/*
    public SpatialReference getLocatorSpatialReference(){
        locator.s
    }*/

    private static ArrayList<Integer> sortCollection(ArrayList<Integer> unsortedArrayList){
        Collections.sort(unsortedArrayList);
        return unsortedArrayList;
    }


    public static Point getPointFromAddress(String address) {
        Point point = null;

        try {
            final Future<List<LocatorGeocodeResult>> futureListLocatorResults = Locator.findLocation(address, getLocator(ONLINE_LOCATOR_URL),
                    new CallbackListener<List<LocatorGeocodeResult>>() {
                        @Override
                        public void onCallback(List<LocatorGeocodeResult> locatorGeocodeResults) {
                            for (LocatorGeocodeResult results : locatorGeocodeResults) {
                                Point point = results.getLocation();
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("Error Locating", throwable.getMessage());
                        }
                    });

            List<LocatorGeocodeResult> locatorGeocodeResultList = futureListLocatorResults.get();

            for (LocatorGeocodeResult locatorGeocodeResult : locatorGeocodeResultList) {
                if(locatorGeocodeResult.getScore() == 100){
                    point = locatorGeocodeResult.getLocation();
                    break;
                }

            }
        } catch (ExecutionException | InterruptedException ex) {
            Log.e("Execution Exception: ", ex.getMessage());
        }
        if (point == null) {
            return null;
        } else
            return point;
    }

}
