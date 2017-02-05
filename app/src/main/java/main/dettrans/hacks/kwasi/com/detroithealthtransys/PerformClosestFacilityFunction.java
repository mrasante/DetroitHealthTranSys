package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.na.ClosestFacilityParameters;
import com.esri.core.tasks.na.ClosestFacilityResult;
import com.esri.core.tasks.na.ClosestFacilityTask;
import com.esri.core.tasks.na.Route;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.TransitType;
import com.here.android.mpa.routing.RouteElement;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.Router;
import com.here.android.mpa.routing.UMCalculateResult;
import com.here.android.mpa.routing.UMRouteOptions;
import com.here.android.mpa.routing.UMRouter;
import com.here.android.mpa.urbanmobility.ErrorCode;
import com.here.android.mpa.urbanmobility.TransportType;
import com.here.android.mpa.urbanmobility.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.ScheduleStopPair;

import static main.dettrans.hacks.kwasi.com.detroithealthtransys.MainActivity.mapView;

/**
 * Created by kwas7493 on 1/23/2017.
 */

public class PerformClosestFacilityFunction extends AsyncTask<ClosestFacilityParameters, String,
        ClosestFacilityResult> {

    public static final String USERNAME = "mrasante1";
    public static final String PASSWORD = "apple@4GONES";
    boolean authentication;
    public static final String PRODUCTION_URL = "http://route.arcgis.com/arcgis/rest/services/World/ClosestFacility/NAServer/ClosestFacility_World";
    public static ProgressDialog progressDialog;
    ClosestFacilityTask closestFacilityTask;

    private Route routeOne;
    private GraphicsLayer routeGraphicsLayer;
    private Graphic endGraphic;
    private SpatialReference inputSR;
    private List<Double> listOfDouble;
    private Polyline polyline;
    private Point originPointToUse;
    private Point destinationPointToUse;
    private List<ScheduleStopPair> originStopSchedulePairs;
    private Point originPoint;
    private Point destinationPoint;
    private Context appContext;
    private List<Point> coordVals = new ArrayList<>();
    private Point destPoint;
    private List<RouteElement> routeElements;


    public PerformClosestFacilityFunction(Context context) {
        this.appContext = context;
    }

    @Override
    protected void onPreExecute() {


        progressDialog = ProgressDialog.show(mapView.getContext(), "Facility Search",
                "Finding closest facility...");

        routeGraphicsLayer = new GraphicsLayer(mapView.getSpatialReference(), mapView.getMaxExtent(),
                GraphicsLayer.RenderingMode.DYNAMIC);
        routeGraphicsLayer.setName("GraphicsLayer");
        for (int i = 0; i < mapView.getLayers().length; i++) {
            if (mapView.getLayer(i).getName().equalsIgnoreCase("GraphicsLayer")) {
                mapView.removeLayer(mapView.getLayer(i));
            }
        }
        mapView.addLayer(routeGraphicsLayer);
        inputSR = mapView.getSpatialReference();
    }


    @Override
    protected ClosestFacilityResult doInBackground(ClosestFacilityParameters... closestFacilityParameterses) {
        ClosestFacilityResult closestFacilityResult = null;
        final Map<String, String> destinationMap = null;
        Map<String, String> originMap = null;
        if (USERNAME == "" && PASSWORD == "") {
            authentication = false;
            closestFacilityTask = new ClosestFacilityTask(PRODUCTION_URL, null);

        } else {
            authentication = true;
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setUserAccount(USERNAME, PASSWORD);
            closestFacilityTask = new ClosestFacilityTask(PRODUCTION_URL, userCredentials);

        }

        try {
            closestFacilityResult = closestFacilityTask.solve(closestFacilityParameterses[0]);
            destinationPoint = (Point) closestFacilityResult.getFacilities().getGraphics()[0].getGeometry();
            originPoint = MainActivity.userOriginPoint;
            originPointToUse = (Point) GeometryEngine.project(originPoint, inputSR, SpatialReference.create(4326));
        } catch (Exception ex) {
            Log.e("Solver Error: ", ex.getMessage());
            ex.printStackTrace();
        }

        return closestFacilityResult;
    }

    @Override
    protected void onProgressUpdate(String... updates) {
        if (progressDialog != null) {
            progressDialog.setMessage(updates[0]);
        }
    }

    /**
     * Clear the operation and dismiss the dialog
     *
     * @param result
     */

    @Override
    public void onPostExecute(final ClosestFacilityResult result) {
        //dismiss the dialog
        if (progressDialog != null) {
            progressDialog.dismiss();
        }


        //this is really not necessary
        if (result == null && authentication == true) {
            Snackbar.make(mapView, "Username and PASSWORD not valid", Snackbar.LENGTH_LONG).show();
            return;
        }


        //location point of closest facility
        routeOne = result.getRoutes().get(0);
        //symbolize the route
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(Color.BLUE, 4, SimpleLineSymbol.STYLE.SOLID);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(mapView.getContext(),
                mapView.getResources().getDrawable(R.drawable.destination, mapView.getContext().getTheme()));
        final Graphic routeGraphic = new Graphic(routeOne.getRouteGraphic().getGeometry(), simpleLineSymbol);
        //this is the closest hospital to the user's location
        //which now becomes the incident for the next analysis
        endGraphic = new Graphic(((Polyline) routeGraphic.getGeometry()).getPoint(
                ((Polyline) routeGraphic.getGeometry()).getPointCount() - 1), pictureMarkerSymbol);
        destPoint = (Point) endGraphic.getGeometry();
        destinationPointToUse = (Point) GeometryEngine.project(destPoint, inputSR, SpatialReference.create(4326));

        MapEngine.getInstance().init(appContext, new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(Error error) {
                if (error == Error.NONE) {

                    UMRouter umRouter = new UMRouter();
                    UMRouteOptions umRouteOptions = new UMRouteOptions();
                    b b = new b();
                    umRouteOptions.setTransportOptions(TransportType.BUS, b);
                    umRouteOptions.setPublicTransportTypeAllowed(TransitType.BUS_PUBLIC, true);
                    umRouteOptions.setTransitWalkMaxDistance(1000);
                    umRouteOptions.setTransitWalkTimeMultiplier(0.75f);
                    umRouteOptions.setRouteType(RouteOptions.Type.FASTEST);

                    RoutePlan routePlan = new RoutePlan();
                    routePlan.setRouteOptions(umRouteOptions);
                    RouteWaypoint startRouteWaypoint = new RouteWaypoint(new GeoCoordinate(originPointToUse.getY(), originPointToUse.getX())); //40.750488, -73.993546));//
                    RouteWaypoint destRouteWaypoint = new RouteWaypoint(new GeoCoordinate(destinationPointToUse.getY(), destinationPointToUse.getX())); //40.749877, -73.845853));//
                    routePlan.addWaypoint(startRouteWaypoint);
                    routePlan.addWaypoint(destRouteWaypoint);
                    umRouter.calculateRoute(routePlan, new Router.Listener<UMCalculateResult, ErrorCode>() {
                        @Override
                        public void onCalculateRouteFinished(UMCalculateResult response, ErrorCode error) {
                            if (response != null) {
                                if (response.getResults().size() > 0) {
                                    routeElements = response.getResults().get(0).getRoute().getRouteElements().getElements();
                                    List<GeoCoordinate> routeGeom = response.getResults().get(0).getRoute().getRouteGeometry();//response.getResults().get(0).getRoute().getRouteGeometry();
                                    Point point = null;
                                    for (GeoCoordinate geoCoord : routeGeom) {
                                        point = new Point(geoCoord.getLongitude(), geoCoord.getLatitude());
                                        Point pointToUse = (Point) GeometryEngine.project(point,
                                                SpatialReference.create(SpatialReference.WKID_WGS84)
                                                , inputSR);
                                        coordVals.add(pointToUse);
                                    }
                                    polyline = createRouteLines(coordVals);
                                } else {
                                    Log.e("RouteError", error.name());
                                }


                                SimpleLineSymbol stopPatternSymbol = new SimpleLineSymbol(Color.GREEN, 4, SimpleLineSymbol.STYLE.SOLID);
                                Graphic routeLineGraphic = new Graphic(polyline, stopPatternSymbol);
                                routeGraphicsLayer.addGraphics(new Graphic[]{routeGraphic, routeLineGraphic, endGraphic});
                                mapView.setExtent(polyline, 500);
                                BottomSheetDialog bottomSheetDialog = MainActivity.showBottomSheeet("Testing Show");
                                TextView bottomSheetLegend = (TextView) bottomSheetDialog.findViewById(R.id.legend_text);
                                RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id.sheet_recycler);
                                recyclerView.setLayoutManager(new LinearLayoutManager(bottomSheetDialog.getContext()));
                                DialogViewAdapter bottomSheetDialogAdapter = new DialogViewAdapter(routeElements);
                                recyclerView.setAdapter(bottomSheetDialogAdapter);
                                bottomSheetLegend.setText("Testing modifications");

                            }
                        }


                        @Override
                        public void onProgress(int percentage) {
                            Log.e("EngineProgress", "" + percentage);
                        }
                    });
                }
            }
        });


        //mapView.zoomTo((Point) endGraphic.getGeometry(), 19);


        /*routeGraphicsLayer.addGraphics(new Graphic[]{routeGraphic, endGraphic});
        mapView.setExtent(routeOne.getEnvelope(), 500);*/

        //create and display route line
        /*if(polyline != null && polyline.isValid()){
            double ymax, xmax, ymin, xmin;

            // for maximum x value
            if(originPointToUse.getX() > destinationPointToUse.getX()){
                xmax = originPointToUse.getX();
            }else {
                xmax = destinationPointToUse.getX();
            }

            //for minimum x value
            if(originPointToUse.getX() < destinationPointToUse.getX()){
                xmin = originPointToUse.getX();
            }else {
                xmin = destinationPointToUse.getX();
            }

            //for maximum y value
            if(originPointToUse.getY() > destinationPointToUse.getY()){
                ymax = originPointToUse.getY();
            }else {
                ymax = destinationPointToUse.getY();
            }

            //for minimum y value
            if(originPointToUse.getY() < destinationPointToUse.getY()){
                ymin = originPointToUse.getY();
            }else {
                ymin = destinationPointToUse.getY();
            }
            //clip polyline to geometry extent of the start and destination
            //TODO create a buffer of the envelope before using for the clipping.
           Polyline clippedPolyline  = (Polyline)GeometryEngine.clip(polyline, new Envelope(xmin, ymin, xmax, ymax),
                   SpatialReference.create(4326));
            Polygon bufferPolygon = (Polygon)GeometryEngine.buffer(clippedPolyline, SpatialReference.create(4326), 2, Unit.create(AngularUnit.Code.DEGREE));
            bufferPolygon.get
            Polyline polylineToUse = (Polyline)GeometryEngine.clip(polyline, new Envelope(xmin, ymin, xmax, ymax), SpatialReference.create(4326));
            //TODO code remove before production

            if(GeometryEngine.intersects(polyline, new Envelope(xmin, ymin, xmax, ymax), SpatialReference.create(4326))){
                Log.e("Envelope", "Envelope is enough!!");
            }else {
                Log.e("Envelope", "Envelope is NOT enough!!");
            }*/


    }


    private List<Point> createListOfPoints(List<List<Double>> listofCoordinates) {
        Point point = null;
        List<Point> pointList = new ArrayList<>();
        for (List<Double> coordValues : listofCoordinates) {
            point = new Point(coordValues.get(0), coordValues.get(1));
            Point pointToUse = (Point) GeometryEngine.project(point, SpatialReference.create(4326), inputSR);
            pointList.add(pointToUse);
        }
        if (pointList.size() > 0) {
            return pointList;
        } else
            return null;
    }


    private Polyline createRouteLines(List<Point> pointList) {
        Polyline polyline = new Polyline();
        polyline.startPath(pointList.get(0));
        for (int i = 1; i < pointList.size(); i++) {
            polyline.lineTo(pointList.get(i));
        }
        return polyline;
    }


}
