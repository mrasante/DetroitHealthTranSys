package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import com.esri.core.map.Graphic;
import com.esri.core.tasks.na.ClosestFacilityParameters;
import com.esri.core.tasks.na.NAFeaturesAsFeature;
import com.esri.core.tasks.na.NATravelDirection;
import com.esri.core.tasks.na.StopGraphic;

import static main.dettrans.hacks.kwasi.com.detroithealthtransys.MainActivity.mapView;

/**
 * Created by kwas7493 on 1/23/2017.
 */

public class StaticParameters {


    public static ClosestFacilityParameters getClosestFacilityParams(Graphic locationGraphic, StopGraphic[] stopGraphic){
        NAFeaturesAsFeature locationFeature = new NAFeaturesAsFeature();
        locationFeature.addFeature(locationGraphic);
        locationFeature.setSpatialReference(mapView.getSpatialReference());

        NAFeaturesAsFeature naFeaturesAsFeature = new NAFeaturesAsFeature();
        naFeaturesAsFeature.setSpatialReference(mapView.getSpatialReference());
        naFeaturesAsFeature.addFeatures(stopGraphic);

        //set up the parameters
        ClosestFacilityParameters closestFacilityParameters = new ClosestFacilityParameters();
        closestFacilityParameters.setReturnFacilities(true);
        closestFacilityParameters.setOutSpatialReference(mapView.getSpatialReference());
        closestFacilityParameters.setTravelDirection(NATravelDirection.TO_FACILITY);
        closestFacilityParameters.setIncidents(locationFeature);
        closestFacilityParameters.setFacilities(naFeaturesAsFeature);
        closestFacilityParameters.setDefaultTargetFacilityCount(Integer.valueOf(1));
        return closestFacilityParameters;
    }

    public static ClosestFacilityParameters getClosestBusStopsParams(Graphic hospitalLocation, StopGraphic[] busStop){
        NAFeaturesAsFeature hospitalLocationFeature = new NAFeaturesAsFeature();
        hospitalLocationFeature.addFeature(hospitalLocation);
        hospitalLocationFeature.setSpatialReference(mapView.getSpatialReference());

        NAFeaturesAsFeature naFeaturesAsFeature = new NAFeaturesAsFeature();
        naFeaturesAsFeature.setSpatialReference(mapView.getSpatialReference());
        naFeaturesAsFeature.addFeatures(busStop);

        //set up the parameters
        ClosestFacilityParameters closestFacilityParameters = new ClosestFacilityParameters();
        closestFacilityParameters.setReturnFacilities(true);
        closestFacilityParameters.setOutSpatialReference(mapView.getSpatialReference());
        closestFacilityParameters.setTravelDirection(NATravelDirection.TO_FACILITY);
        closestFacilityParameters.setIncidents(hospitalLocationFeature);
        closestFacilityParameters.setFacilities(naFeaturesAsFeature);
        closestFacilityParameters.setDefaultTargetFacilityCount(Integer.valueOf(1));
        return  closestFacilityParameters;
    }
}
