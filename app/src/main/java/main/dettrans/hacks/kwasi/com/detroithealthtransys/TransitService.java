package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.RouteStopPattern;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.RouteStopPatterns;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.ScheduleStopPair;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.ScheduleStopPairs;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.Stop;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.TransitStops;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kwas7493 on 1/25/2017.
 */

public class TransitService {
    public static final String API_BASE_URL = "https://transit.land";
    public static List<Stop> destinationStopsList;
    public static List<Stop> originStopsList;
    private static List<RouteStopPattern> routeStopPattern;
    private static RouteStopPatterns routeStopPatterns;


    /**
     * The all the transit stops for the destination within the specified
     * radius
     *
     * @param lon
     * @param lat
     * @param Radius
     * @return
     * @throws IOException
     */

    public static Map<String, String> getTransitStopsAtDestination(double lon, double lat, int Radius) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final HashMap<String, String> hashMap = new HashMap<>();
        ITransitService myTransitService = retrofit.create(ITransitService.class);
        final Call<TransitStops> transitStops = myTransitService.searchStopsWithinRadius(lon, lat, Radius);
        transitStops.enqueue(new Callback<TransitStops>() {
            @Override
            public void onResponse(Call<TransitStops> call, Response<TransitStops> response) {
                if (response.body().getStops() != null && response.body().getStops().size() > 0) {
                    destinationStopsList = response.body().getStops();
                    for (Stop stops : destinationStopsList) {
                        hashMap.put("Stop Name", stops.getName());
                        hashMap.put("OneStopID", stops.getOnestop_id());
                    }
                    Log.e("Response", "" + response.body().getStops().get(0).getName());
                } else
                    Log.e("DestinationStops", "there no stops in the region");
            }

            @Override
            public void onFailure(Call<TransitStops> call, Throwable t) {
                Log.e("onError: ", "" + t.getMessage());
            }
        });

        return hashMap;
    }


    /**
     * Performs the same function getTransitStopsAtDestination exception this is
     * for the user's current location.
     *
     * @param lon
     * @param lat
     * @param Radius
     * @return
     * @throws IOException
     */
    public static Map<String, String> getTransitStopsAtOrigin(double lon, double lat, int Radius) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final HashMap<String, String> hashMap = new HashMap<>();
        ITransitService stopsTransitService = retrofit.create(ITransitService.class);
        final Call<TransitStops> transitStops = stopsTransitService.searchStopsWithinRadius(lon, lat, Radius);
        transitStops.enqueue(new Callback<TransitStops>() {
            @Override
            public void onResponse(Call<TransitStops> call, Response<TransitStops> response) {
                if (response.body().getStops() != null && response.body().getStops().size() > 0) {
                    originStopsList = response.body().getStops();
                    for (Stop stops : originStopsList) {
                        hashMap.put("Stop Name", stops.getName());
                        hashMap.put("OneStopID", stops.getOnestop_id());
                    }
                    Log.e("Stop Name", "" + response.body().getStops().get(0).getName());
                } else
                    Log.e("Stop Error", "there no stops in the region");
            }

            @Override
            public void onFailure(Call<TransitStops> call, Throwable t) {
                Log.e("StopServiceError: ", "" + t.getMessage());
            }
        });

        return hashMap;
    }


    /**
     * Use this method to return the route stop patterns for the provided origin and destination
     * stops. The geometry of the returned stops can be retrieve for later plotting using
     * esri's android API
     *
     * @param originStopsMap
     * @param destStopsMap
     * @return
     */

    public static synchronized RouteStopPatterns getRouteBtnStops(Map<String, String> originStopsMap,
                                                     Map<String, String> destStopsMap) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITransitService routeStopPatternService = retrofit.create(ITransitService.class);
        ArrayList<String> originStops = new ArrayList<>();
        ArrayList<String> destinationStops = new ArrayList<>();
        routeStopPatterns = new RouteStopPatterns();

        //populate all the possible origin/destination stops;
        for(int i=0; i < originStopsMap.size(); i++){
                originStops.add(originStopsMap.get("OneStopID"));
                destinationStops.add(destStopsMap.get("OneStopID"));
        }


        //now permute and perform route search
        if(originStops.size() > 0 && destinationStops.size() > 0){
            int counter = originStops.size();
            /*for(String origin : originStops){
                for(String destination : destinationStops){
                  Call<RouteStopPatterns> routeStopPatternsCall =
                          routeStopPatternService.searchRoutesWithOneStopIds(origin, destination);

                  *//*  routeStopPatternsCall.enqueue(new Callback<RouteStopPatterns>() {
                        @Override
                        public void onResponse(Call<RouteStopPatterns> call, Response<RouteStopPatterns> response) {
                            routeStopPattern = response.body().getRouteStopPatterns();
                            if(routeStopPattern.size() > 0){
                                routeStopPatterns.setRouteStopPatterns(routeStopPattern);
                            }
                        }

                        @Override
                        public void onFailure(Call<RouteStopPatterns> call, Throwable t) {
                                    Log.e("RouteError", t.getMessage());
                        }
                    });*//*
                    Response<RouteStopPatterns> routeStopPatternsCallback = routeStopPatternsCall.execute();
                    routeStopPattern = routeStopPatternsCallback.body().getRouteStopPatterns();
                    if(routeStopPattern.size() > 0){
                        routeStopPatterns.setRouteStopPatterns(routeStopPattern);
                    }


                }

            }*/


            Call<RouteStopPatterns> routeStopPatternsCall =  null;
            for(int i =0; i < originStops.size(); i++){
                routeStopPatternsCall =
                        routeStopPatternService.searchRoutesWithOneStopIds(originStops.get(i), destinationStops.get(i));
                if(routeStopPatternsCall != null){
                    break;
                }
            }

            Response<RouteStopPatterns> routeStopPatternsCallback = routeStopPatternsCall.execute();
            routeStopPattern = routeStopPatternsCallback.body().getRouteStopPatterns();
            if(routeStopPattern.size() > 0){
                routeStopPatterns.setRouteStopPatterns(routeStopPattern);
            }

        }

        return routeStopPatterns;

    }


    public static List<ScheduleStopPair> getStopSchedulePairs(String... strings) throws IOException{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITransitService transitService = retrofit.create(ITransitService.class);
        Call<ScheduleStopPairs> stopSchedulePair = transitService.searchDestinationScheduleStopPairs(strings);

        Response<ScheduleStopPairs> stopSchedulePairs = stopSchedulePair.execute();

        return stopSchedulePairs.body().getScheduleStopPairs();


    }

}
