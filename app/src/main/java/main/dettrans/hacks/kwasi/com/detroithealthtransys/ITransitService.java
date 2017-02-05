package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.RouteStopPatterns;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.ScheduleStopPairs;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.TransitStops;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kwas7493 on 1/25/2017.
 */

public interface ITransitService {


    /**
     * Search for all stop locations for within the specified radius
     *
     * @param lon lon is longitude
     * @param lat lat is latitude
     * @param radius r is radius of search in meters (if not specified, defaults to 100 meters)
     * @return
     */

    @GET("/api/v1/stops")
    Call<TransitStops> searchStopsWithinRadius(@Query("lon") double lon, @Query("lat") double lat, @Query("r") int radius);  //e.g.: lon=-83.129984&lat=42.389399&r=100


    /**
     * Finds Route Stop Patterns with stops_visited in stop_pattern.
     *
     * @param oneStopIds any one or more stop Onestop IDs, separated by comma.
     * @return
     */
    @GET("/api/v1/route_stop_patterns")
    Call<RouteStopPatterns> searchRoutesWithOneStopIds(@Query("stops_visited") String... oneStopIds);   //e.g.: s-dr5rec3weg-whitehallferryterminal,s-dr5r4rn32w-stgeorgeferryterminal


    /**
     * Find all Schedule Stop Pairs from origin. Accepts multiple Onestop IDs, separated by commas.
     *
     * @param originStopIds
     * @return
     */
    @GET("/api/v1/schedule_stop_pairs")
    Call<ScheduleStopPairs> searchOriginScheduleStopPairs(@Query("origin_onestop_id")String... originStopIds); //e.g.: api/v1/schedule_stop_pairs?origin_onestop_id=s-dpscjr7mc3-oakland~buenavista


    /**
     * Find all Schedule Stop Pairs to a destination. Accepts multiple Onestop IDs, separated by commas.
     *
     * @param destinationStopIds
     * @return
     */

    @GET("/api/v1/schedule_stop_pairs")
    Call<ScheduleStopPairs> searchDestinationScheduleStopPairs(@Query("destination_onestop_id")String... destinationStopIds); //e.g.: api/v1/schedule_stop_pairs?origin_onestop_id=s-dpscjr7mc3-oakland~buenavista


}
