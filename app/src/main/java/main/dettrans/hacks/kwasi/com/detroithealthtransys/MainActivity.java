package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.Callout;
import com.esri.android.map.CalloutStyle;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.event.OnLongPressListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.na.ClosestFacilityParameters;
import com.esri.core.tasks.na.StopGraphic;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.dettrans.hacks.kwasi.com.detroithealthtransys.models.EsriAuthenticator;

import static main.dettrans.hacks.kwasi.com.detroithealthtransys.LogInActivity.portal;
import static main.dettrans.hacks.kwasi.com.detroithealthtransys.R.id.map_view;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, EsriAuthenticator.OnFragmentInteractionListener  {

    private Toolbar toolbar;
    private Drawer result;
    public static MapView mapView;
    private LocationDisplayManager locationDisplay;
    private FloatingActionButton floatingActionButton;
    private ArcGISFeatureLayer hospitalFeatureLayer;
    private Callout callout;
    private ListView mListView;
    private AccountHeader headerResult;
    public Point stopPoint;
    public static Point userOriginPoint;
    private Bitmap bitmap;
    private ArcGISDynamicMapServiceLayer trafficServiceMapLayer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.bringToFront();
        setSupportActionBar(toolbar);

        //search suggestions controls
        mListView = (ListView) findViewById(R.id.list);

        //handle intent for searches
        handleIntent(getIntent());


        //configure map view and its properties
        mapView = (MapView) findViewById(map_view);
        mapView.setEsriLogoVisible(true);
        //mapView.setShowMagnifierOnLongPress(true);
        mapView.setAllowMagnifierToPanMap(true);
        mapView.setAllowRotationByPinch(true);


        //allow USERNAME to determine his/her location
        floatingActionButton = (FloatingActionButton) findViewById(R.id.my_location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ensure USERNAME has granted permission before using GPS
                if (ensureLocationPermission()) {
                    locationDisplay = mapView.getLocationDisplayManager();
                    if (locationDisplay.getLocation() != null) {
                        mapView.centerAt(locationDisplay.getLocation().getLongitude(), locationDisplay.getLocation().getLatitude(), true);
                    } else
                        Snackbar.make(mapView, "Permission needed for position fix", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //allow users to bookmark a view
        FloatingActionButton homeFloatingButton = (FloatingActionButton) findViewById(R.id.floating_home);
        //TODO add logic to bookmark a map view

        //allow user to zoom to full extent of the map
        FloatingActionButton zoomExtentFloater = (FloatingActionButton) findViewById(R.id.float_extent_max);
        zoomExtentFloater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapView.setExtent(GeometryEngine.project(new Envelope(-82.954, 42.298, -83.24, 42.436), SpatialReference.create(4326)
                , mapView.getSpatialReference()));
                mapView.zoomout(true);
                //TODO debugging code; remove before production
                /*for(int i=0; i <mapView.getLayers().length; i++){
                    Log.e("Index "+i, mapView.getLayers()[i].getName());
                }*/
            }
        });

        //create the navigation drawer
        try {
            createCrossFadeDrawer(savedInstanceState, toolbar, getIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //wire events to show popup
        mapView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float x, float y) {
                //check to see if callout exist and dismiss it
                dismissCallout();
                showCalloutOrBeginRouting(x, y);
            }
        });


        //wire event to listen for status change on the map_view and add the hospital layer
        mapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (status == STATUS.INITIALIZED) {
                    addHospitalLayers();
                }
            }
        });

        //perform closest facility analysis on long press
        mapView.setOnLongPressListener(new OnLongPressListener() {
            @Override
            public boolean onLongPress(float v, float v1) {
                userOriginPoint = mapView.toMapPoint(v, v1);
                return performAnalysisOnLongPress(userOriginPoint, retrieveHospitalAsGraphics());
            }
        });
    }


    /**
     * Retrieve all hospitals and create stop graphic from them
     */

    private StopGraphic[] retrieveHospitalAsGraphics() {
        GraphicsLayer featureLayer = null;
        for(int i=0; i <mapView.getLayers().length; i++){
            if(mapView.getLayers()[i].getName().equalsIgnoreCase("Detroit_Hospitals")){
                    featureLayer = (GraphicsLayer) mapView.getLayers()[i];
                break;
            }
        }

        int[] uniqueGrpahicIDs = null;
        if(featureLayer != null){
            uniqueGrpahicIDs = featureLayer.getGraphicIDs();

        }else
        return null;
        Graphic graphic = null;
        StopGraphic[] stopGraphics = new StopGraphic[uniqueGrpahicIDs.length];
        int counter = 0;
        StopGraphic stopGraphic = null;
        for (int graphicId : uniqueGrpahicIDs) {
            graphic = featureLayer.getGraphic(graphicId);
            stopGraphic = new StopGraphic(graphic);
            stopGraphics[counter] = stopGraphic;
            counter++;
        }
        return stopGraphics;
    }

    /**
     * Retrieve all stops as graphics. These graphics serve as the facilities for the
     * transit route retrieval from the service (transit land)
     */

    public static StopGraphic[] retrieveAllStopsAsGraphics() {
        ArcGISFeatureLayer featureLayer = (ArcGISFeatureLayer) mapView.getLayers()[1];
        int[] uniqueStopGraphicIDs = featureLayer.getGraphicIDs();
        ArrayList<StopGraphic> stopGraphics = new ArrayList<>(100);
        StopGraphic stopGraphic = null;
        for (int i = 0; i < 100; i++) {
            stopGraphic = new StopGraphic(featureLayer.getGraphic(uniqueStopGraphicIDs[i]));
            stopGraphics.add(stopGraphic);
        }
        StopGraphic[] stopGraphics1 = stopGraphics.toArray(new StopGraphic[stopGraphics.size()]);
        return stopGraphics1;
    }

    /**
     * Perform closest facility analysis based on location and hospital location
     *
     * @param point
     * @param stopGraphics
     * @return
     */

    private boolean performAnalysisOnLongPress(Point point, StopGraphic[] stopGraphics) {
        ClosestFacilityParameters cfp = StaticParameters.getClosestFacilityParams(new Graphic(point, new SimpleMarkerSymbol(Color.RED, 4,
                SimpleMarkerSymbol.STYLE.DIAMOND)), stopGraphics);
        PerformClosestFacilityFunction closestFacilityFunction = new PerformClosestFacilityFunction(this);
        closestFacilityFunction.execute(cfp);
        return true;
    }


    /**
     * Create and return bottom sheet for display of various information
     */

    public static BottomSheetDialog showBottomSheeet(String title) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mapView.getContext());
        bottomSheetDialog.setTitle(title);
        View bottomsheetView = LayoutInflater.from(mapView.getContext()).inflate(R.layout.bottomsheet, null);
        bottomSheetDialog.setContentView(bottomsheetView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetView.findViewById(R.id.sheet_grid));
        bottomSheetBehavior.setPeekHeight(20);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }


    /**
     * Dismisses call outs on the map on single tap
     */

    private void dismissCallout() {
        if (callout != null && callout.isShowing()) {
            callout.animatedHide();
        }
    }


    /**
     * Adds hospital layers that will be used to identify
     */

    private void addHospitalLayers() {
        for(Layer layer : mapView.getLayers()){
            if(layer.getName() != null && layer.getName().equalsIgnoreCase("Detroit_Hospitals")){
                hospitalFeatureLayer = (ArcGISFeatureLayer) layer;
                mapView.addLayer(hospitalFeatureLayer);
                Log.e("LayerAdded", "Hospitals Added");
                break;
            }
        }

         /*for(int i=0; i <mapView.getLayers().length; i++){
                    Log.e("Index "+i, mapView.getLayers()[i].getName());
                }*/
    }


    /**
     * creates a popup using callout from the mapview and shows
     * selected feature information.
     */

    private void showCalloutOrBeginRouting(float x, float y) {
        callout = mapView.getCallout();
        RelativeLayout calloutLayout =
                (RelativeLayout) LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.callout_feature_layout, null);
        //wire event to the directions click
        ImageView directionImage = (ImageView)calloutLayout.findViewById(R.id.directions);
        directionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO call methods to search for routes
            }
        });

        //get the clicked feature and assign values to the callout
        stopPoint = mapView.toMapPoint(x, y);
        callout.setCoordinates(stopPoint);
        callout.setContent(calloutLayout);
        //configure style and set it
        CalloutStyle calloutStyle = new CalloutStyle(getApplicationContext());
        calloutStyle.setAnchor(5);
        calloutStyle.setCornerCurve(10);
        calloutStyle.setFrameColor(Color.GRAY);

        callout.setStyle(calloutStyle);
        int[] graphicIdArray = hospitalFeatureLayer.getGraphicIDs(x, y, 50);
        //check for returned hospital and show call out
        if (graphicIdArray.length != 0) {
            String attributeName = hospitalFeatureLayer.getGraphic(graphicIdArray[0])
                    .getAttributeValue("FACNAME").toString();
            TextView textView = (TextView) calloutLayout.findViewById(R.id.feature_name);
            textView.setText(attributeName);
            callout.animatedShow(stopPoint, calloutLayout);
            mapView.centerAt(stopPoint, true);
        } else {
            //if no hospital found within 50 pixels tolerance of clicked region then
            // perform routing to hospital from given location
            //performAnalysisOnLongPress(stopPoint, retrieveHospitalAsGraphics());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean ensureLocationPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationDisplay = mapView.getLocationDisplayManager();
            locationDisplay.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            locationDisplay.start();
            return true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            int[] intArray = {PackageManager.PERMISSION_GRANTED};
            onRequestPermissionsResult(1, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, intArray);
            LocationDisplayManager locationDisplay = mapView.getLocationDisplayManager();
            locationDisplay.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            locationDisplay.start();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocationDisplayManager locationDisplay = mapView.getLocationDisplayManager();
            locationDisplay.start();
        }
    }


    /**
     * creates and manages options menu
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

//        toolbar.inflateMenu(R.menu.options_menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }


    /**
     * Call to handle intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            //handle the click in the search view
            Intent suggestionIntent = new Intent(this, this.getClass());
            suggestionIntent.setData(intent.getData());
            startActivity(suggestionIntent);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            showResults(intent.getStringExtra(SearchManager.QUERY));
        }
    }


    /**
     * This method ensures results are shown for all the hospitals loaded in the database
     * @param query
     */

    private void showResults(String query) {
        Cursor cursor = getContentResolver().query(DetHosContentProvider.CONTENT_URI, null, null, new String[]{query}, null);
        if (cursor == null) {
            Snackbar.make(findViewById(map_view), "No matching text", Snackbar.LENGTH_LONG).show();
        } else {
            int count = cursor.getCount();
            String countString = getResources().getQuantityString(R.plurals.search_results, count,
                    new Object[]{count, query});

            //column to display in results
            String[] fromColumn = new String[]{SuggestionOpenHelper.KEY_WORD};

            //layout to assign results to
            int[] to = new int[]{R.id.word};

            //create the adapter
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.results,
                    cursor, fromColumn, to, Intent.FLAG_ACTIVITY_NEW_TASK);
            mListView.setAdapter(simpleCursorAdapter);

            //wire onclick events to list view

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), this.getClass());
                    Uri dataInUri = Uri.withAppendedPath(DetHosContentProvider.CONTENT_URI, String.valueOf(l));
                    intent.setData(dataInUri);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // handleIntent(intent);
        Cursor cursor = getContentResolver().query(intent.getData(), null, null, new String[]{""}, null);
        if (cursor == null) {
            finish();
        } else {
            cursor.moveToFirst();

            //retrieve address data
            int addressField = cursor.getColumnIndexOrThrow(SuggestionOpenHelper.SUGGESTION);
            String addressToGeocode = cursor.getString(addressField);
            Point point = GeoLocator.getGeocodePoint(addressToGeocode);
            Point projectedPoint = GeometryEngine.project(point.getX(), point.getY(), mapView.getSpatialReference());
            mapView.zoomToResolution(projectedPoint, 2);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return false;
        }
    }


    class PortalNetworkAsyncTask extends AsyncTask<Void, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(Void... voids) {
            try{
            byte[] portalUser = portal.fetchUser().fetchThumbnail();
                bitmap = BitmapFactory.decodeByteArray(portalUser, 0, portalUser.length);

            }catch(Exception exception){
                exception.printStackTrace();
            }

            return bitmap;
        }
    }

    /**
     * Creates a cross-fading navigation drawer using the material drawer sdk by mike penz
     *
     * @param savedInstanceState
     * @param toolbar
     */


    private void createCrossFadeDrawer(final Bundle savedInstanceState, Toolbar toolbar, Intent intent) throws Exception{
        IProfile profile = null;
        String username = intent.getStringExtra("FBUserFullName");
        Bitmap iconBitmap = new PortalNetworkAsyncTask().execute().get();
        if(username != null){
            profile = new ProfileDrawerItem().withName(username);
        }else if(portal != null){
            String portalUserFullName = intent.getStringExtra("PortalUserFullName");
            profile = new ProfileDrawerItem().withName(portalUserFullName)
            .withIcon(iconBitmap);
        }

        //Create the account header
        headerResult = new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.drawable.material_drawer_badge)
                .withHeaderBackground(R.drawable.dark_material_design_wallpapert)
                .withEmailTypeface(Typeface.SANS_SERIF)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer_layout)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background, null))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Map").withIcon(R.drawable.map).withIdentifier(1).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Routes").withIcon(R.drawable.route).withBadge("22").withTextColor(Color.WHITE).withBadgeStyle(
                                new BadgeStyle(Color.RED, Color.blue(20))).withIdentifier(2).withSelectable(true),
                        new PrimaryDrawerItem().withName("Hospitals").withIcon(R.drawable.hospital).withIdentifier(3).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Bus Stops").withIcon(R.drawable.bus).withIdentifier(4).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Traffic").withIcon(R.drawable.traffic).withIdentifier(5).withTextColor(Color.WHITE),

                        new SectionDrawerItem().withDivider(true),
                        new SecondaryDrawerItem().withName("Settings").withIcon(R.drawable.settings).withTextColor(Color.WHITE),
                        new SecondaryDrawerItem().withName("Feedback").withIcon(R.drawable.feedback).withTextColor(Color.WHITE),
                        new SecondaryDrawerItem().withName("About").withIcon(R.drawable.info).withTag("Bullhorn").withTextColor(Color.WHITE)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 1){
                            if(trafficServiceMapLayer !=null){
                                trafficServiceMapLayer.setVisible(false);
                            }
                        }else if(drawerItem.getIdentifier() == 2){
                            toggleRoutes();
                        }else if(drawerItem.getIdentifier() == 3){
                            toggleHospitals();
                        }else if(drawerItem.getIdentifier() == 4){
                            toggleBusStops();
                        }else if(drawerItem.getIdentifier() == 5){
                            toggleTrafficData();
                        }
                        return false;
                    }
                }).withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        final CrossfadeDrawerLayout crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background,
                com.mikepenz.materialdrawer.R.color.material_drawer_dark_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }

    private void toggleTrafficData() {
        EsriAuthenticator esriAuthenticator = new EsriAuthenticator();
        //getSupportFragmentManager().beginTransaction().add(R.id.map_view, esriAuthenticator, "AuthenticationFrag").commit();
       //UserCredentials userCredentials = esriAuthenticator.getUserLoginCredentials();
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserAccount("mrasante1", "apple@4GONES");
        trafficServiceMapLayer = new ArcGISDynamicMapServiceLayer(getString(R.string.arcgistrafficserviceurl),
                null, userCredentials, true);
        mapView.addLayer(trafficServiceMapLayer);
        List<Layer> layerCollection = Arrays.asList(mapView.getLayers());
       //go through map and remove layer if it's already in there
        for(Layer layer : layerCollection){
            if(layer.getUrl() == trafficServiceMapLayer.getUrl()){
                trafficServiceMapLayer.setVisible(true);
            }else {
               trafficServiceMapLayer.setVisible(false);
            }
        }
    }

    private void toggleBusStops() {
        ArcGISFeatureLayer mapHosFeatureLayer = (ArcGISFeatureLayer) mapView.getLayers()[1];
        if(mapHosFeatureLayer.isVisible()){
            mapHosFeatureLayer.setVisible(false);
        }else {
            mapHosFeatureLayer.setVisible(true);
        }
    }

    private void toggleHospitals() {
        ArcGISFeatureLayer mapHosFeatureLayer = (ArcGISFeatureLayer) mapView.getLayers()[4];
        ArcGISFeatureLayer idHosFeaturelayer = (ArcGISFeatureLayer)mapView.getLayers()[5];
        if(mapHosFeatureLayer.isVisible() && idHosFeaturelayer.isVisible()){
            mapHosFeatureLayer.setVisible(false);
            idHosFeaturelayer.setVisible(false);
        }else {
            mapHosFeatureLayer.setVisible(true);
            idHosFeaturelayer.setVisible(true);
        }
    }


    private void toggleRoutes() {
        ArcGISFeatureLayer smartbusRouteLayer = (ArcGISFeatureLayer) mapView.getLayers()[3];
        ArcGISFeatureLayer ddotRoutesLayer = (ArcGISFeatureLayer)mapView.getLayers()[2];
        if(smartbusRouteLayer.isVisible() && ddotRoutesLayer.isVisible()){
            smartbusRouteLayer.setVisible(false);
            ddotRoutesLayer.setVisible(false);
        }else {
            smartbusRouteLayer.setVisible(true);
            ddotRoutesLayer.setVisible(true);
        }
    }


}
