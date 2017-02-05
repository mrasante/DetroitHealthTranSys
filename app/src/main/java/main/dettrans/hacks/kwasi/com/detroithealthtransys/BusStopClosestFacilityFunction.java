package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.os.AsyncTask;
import android.util.Log;

import com.esri.core.geometry.Polyline;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.na.ClosestFacilityParameters;
import com.esri.core.tasks.na.ClosestFacilityResult;
import com.esri.core.tasks.na.ClosestFacilityTask;
import com.esri.core.tasks.na.Route;

import static main.dettrans.hacks.kwasi.com.detroithealthtransys.PerformClosestFacilityFunction.PASSWORD;
import static main.dettrans.hacks.kwasi.com.detroithealthtransys.PerformClosestFacilityFunction.USERNAME;
import static main.dettrans.hacks.kwasi.com.detroithealthtransys.PerformClosestFacilityFunction.progressDialog;


/**
 * Created by kwas7493 on 1/23/2017.
 */

public class BusStopClosestFacilityFunction extends AsyncTask<ClosestFacilityParameters, Void,
        ClosestFacilityResult> {


    private ClosestFacilityTask closestFacilityTask;
    private boolean authentication;
    private Route busRoute;
    private Graphic busStopGrpahic;


    private static final String NASERVERTASK_URL = "http://route.arcgis.com/arcgis/rest/services/World/ClosestFacility/NAServer/ClosestFacility_World";
    @Override
    public void onPreExecute(){
        progressDialog.setTitle("Finding Bus Stops");
        progressDialog.setMessage("Locating closest bus stops to hospital....");
    }

    @Override
    protected ClosestFacilityResult doInBackground(ClosestFacilityParameters... closestFacilityParameterses) {
        ClosestFacilityResult closestFacilityResult = null;
        if (USERNAME == "" && PASSWORD == "") {
            authentication = false;
            closestFacilityTask = new ClosestFacilityTask(NASERVERTASK_URL, null);

        } else {
            authentication = true;
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setUserAccount(USERNAME, PASSWORD);
            closestFacilityTask = new ClosestFacilityTask(NASERVERTASK_URL, userCredentials);

        }

        try {
            closestFacilityResult = closestFacilityTask.solve(closestFacilityParameterses[0], new CallbackListener<ClosestFacilityResult>() {
                @Override
                public void onCallback(ClosestFacilityResult result) {
                    Log.e("Results: ", ""+result.getFacilities().getGraphics().length);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("Error Closest: ", throwable.getMessage());
                }
            }).get();
        } catch (Exception ex) {
            Log.e("Solver Error: ", ex.getMessage());
        }
        return  closestFacilityResult;
    }

    @Override
    public void onPostExecute(ClosestFacilityResult results){
        if(progressDialog != null){
          progressDialog.dismiss();
        }
        busRoute = results.getRoutes().get(0);
        Log.e("I made it: ", ""+busRoute.getRoutingDirections().size());
        Graphic routeGraphic = new Graphic(busRoute.getRouteGraphic().getGeometry(), null);

        //this graphic is essentially the closest bus stop
        busStopGrpahic = new Graphic(((Polyline) routeGraphic.getGeometry()).getPoint(
                ((Polyline) routeGraphic.getGeometry()).getPointCount() - 1), null);


    }
}
