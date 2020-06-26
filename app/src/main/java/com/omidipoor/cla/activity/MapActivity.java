package com.omidipoor.cla.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Circle;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions;
import com.mapbox.mapboxsdk.plugins.annotation.OnCircleClickListener;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;

import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.RasterSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.mapboxsdk.utils.ColorUtils;
import com.mapbox.turf.TurfMeasurement;
import com.omidipoor.cla.R;
import com.omidipoor.cla.database.AppDatabase;
import com.omidipoor.cla.database.DatabaseInitializer;
import com.omidipoor.cla.database.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.omidipoor.cla.Common.DEFAULT_INTERVAL_IN_MILLISECONDS;
import static com.omidipoor.cla.Common.DEFAULT_MAX_WAIT_TIME;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {
    private MapView mapView;
    private MapboxMap mapboxMap;

    // for drawing
    CircleManager circleManager;
    List<Point> fakeLineVertexes;
    List<Point> fakeLineVertexesWithMid;

    FloatingActionButton btnGps, btnLayerSwitcher, btnDrawPoint, btnDrawLine, btnDrawPolygon;

    Toolbar mToolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;



    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.token));

        setContentView(R.layout.activity_map);


        // disp toolbar
        mToolbar =  findViewById(R.id.m_toolbar);
        setSupportActionBar(mToolbar);

        // display toggle in action bar
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        btnGps = findViewById(R.id.fab_gps);
        btnLayerSwitcher = findViewById(R.id.fabLayerSwitcher);
        btnDrawPoint = findViewById(R.id.drawMarker);
        btnDrawLine = findViewById(R.id.drawLineString);
        btnDrawPolygon = findViewById(R.id.drawPolygon);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        



        // initialise db
        DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));

        showWelcome();




    }



    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;




        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        AppDatabase db = AppDatabase.getAppDatabase(getBaseContext());
                        List<User> a = db.userDao().getAll();
                        Log.d("MyErr", a.get(0).getFirstName());

                        circleManager = new CircleManager(mapView, mapboxMap, style);
                        showNearPeople(mapboxMap.getStyle());
                        addEmptySourceToMap4ManageDrawing(mapboxMap.getStyle());

                        circleManager.addClickListener(new OnCircleClickListener() {
                            @Override
                            public void onAnnotationClick(Circle circle) {
                                manageMidOrMainPointClick(circle);                          }
                        });


                    }
                });



        mapView.addOnStyleImageMissingListener(new MapView.OnStyleImageMissingListener() {
            @Override
            public void onStyleImageMissing(@NonNull String id) {
                switch (id) {
                    case "Morteza":
                        addImage(id, R.drawable.morteza);
                        break;
                    case "Ara":
                        addImage(id, R.drawable.ara);
                        break;
                    case "Saeid":
                        addImage(id, R.drawable.saead);
                        break;
                    case "Ali":
                        addImage(id, R.drawable.ali);
                        break;
                    default:
                        addImage(id, R.drawable.mohamadreza);
                        break;
                }
            }
        });


        btnLayerSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBaseMap(mapboxMap.getStyle());
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location last = mapboxMap.getLocationComponent().getLastKnownLocation();
                if (last != null){
                    mapboxMap.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mapboxMap.getLocationComponent().getLastKnownLocation().getLatitude(),
                                    mapboxMap.getLocationComponent().getLastKnownLocation().getLongitude()),
                            14));
                }
            }
        });


        btnDrawPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawPointByClick(mapboxMap.getStyle());
            }
        });

        btnDrawLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDrawPolygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void manageMidOrMainPointClick(Circle circle) {

        circle.setCircleColor(Color.YELLOW);
        circle.setDraggable(true);
        circleManager.update(circle);
    }


    private void addEmptySourceToMap4ManageDrawing(Style style) {
        String f = "{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[51.4371045,35.6401296],[51.425867,35.6787903],[51.4407632,35.7303795],[51.4802251,35.7486294],[51.446774,35.7940239]]},\"properties\":{}}";
        String s = "{\"type\":\"LineString\",\"coordinates\":[[51.4371045,35.6401296],[51.425867,35.6787903],[51.4407632,35.7303795],[51.4802251,35.7486294],[51.446774,35.7940239]],\"properties\":{}}";
        String pf = "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[51.21757507324219,35.73982452242507],[51.216888427734375,35.70247433100471],[51.25122070312499,35.69968630125204],[51.28349304199219,35.721987809328716],[51.263580322265625,35.75208494531366],[51.21757507324219,35.73982452242507]]]}}";
        String ps = "{\"type\":\"Polygon\",\"coordinates\":[[[51.21757507324219,35.73982452242507],[51.216888427734375,35.70247433100471],[51.25122070312499,35.69968630125204],[51.28349304199219,35.721987809328716],[51.263580322265625,35.75208494531366],[51.21757507324219,35.73982452242507]]],\"properties\":{}}";
        style.addSource(new GeoJsonSource("fakeLineStringSource", f));
        style.addSource(new GeoJsonSource("fakePolygonSource", pf));

        LineString lineString = LineString.fromJson(s);
        Polygon polygon = Polygon.fromJson(ps);



        style.addLayerBelow(new LineLayer("fakeDrawLineStringLayer", "fakeLineStringSource").withProperties(
                PropertyFactory.lineDasharray(new Float[] {0.01f, 2f}),
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
        ), circleManager.getLayerId());

        style.addLayerBelow(new FillLayer("fakePolygonLayer", "fakePolygonSource").withProperties(
                PropertyFactory.fillColor(Color.GRAY),
                PropertyFactory.fillOpacity(0.5f)
                ),circleManager.getLayerId());

        addAllMidpointOfGeom(Feature.fromJson(f));
        addAllMidpointOfGeom(Feature.fromJson(pf));

    }

    private void addAllMidpointOfGeom(Feature feature) {

        if (feature.geometry().type().equals("LineString")) {
            List<Point> points = ((LineString) Objects.requireNonNull(feature.geometry())).coordinates();
            int size = points.size();
            for (int i=0; i < size; i++) {
                Point p = points.get(i);
                addSingleMainPointToCircleManager(p);
            }
            for (int i=0; i < size - 1; i++) {
                Point from = points.get(i);
                Point to = points.get( i + 1);
                Point midPoint = TurfMeasurement.midpoint(from, to);
                addSingleMidPointToCircleManager(midPoint);
            }
        } else if (feature.geometry().type().equals("Polygon")){
            List<List<Point>> pointsList = ((Polygon) Objects.requireNonNull(feature.geometry())).coordinates();
            int size = pointsList.get(0).size();
            for (int i=0; i < size - 1; i++) {
                Point p = pointsList.get(0).get(i);
                addSingleMainPointToCircleManager(p);
            }
            for (int i=0; i < size - 1; i++) {
                Point from = pointsList.get(0).get(i);
                Point to = pointsList.get(0).get( i + 1);
                Point midPoint = TurfMeasurement.midpoint(from, to);
                addSingleMidPointToCircleManager(midPoint);
            }

        } else {

        }

    }

    private void addSingleMidPointToCircleManager(Point midPoint) {
        CircleOptions circleOption = new CircleOptions()
                .withLatLng(new LatLng(midPoint.latitude(), midPoint.longitude()))
                .withCircleColor(ColorUtils.colorToRgbaString(Color.GREEN))
                .withCircleRadius(7f)
                .withDraggable(false);
        circleManager.create(circleOption);
    }

    private void addSingleMainPointToCircleManager(Point p) {
        CircleOptions circleOption = new CircleOptions()
                .withLatLng(new LatLng(p.latitude(), p.longitude()))
                .withCircleColor(ColorUtils.colorToRgbaString(Color.BLACK))
                .withCircleRadius(10f)
                .withDraggable(false);
        circleManager.create(circleOption);
    }

    private void drawPointByClick(Style style) {
        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(point));
                return true;
            }
        });
    }

    private void addImage(String id, int drawableImage) {
        Style style = mapboxMap.getStyle();
        if (style != null) {
            style.addImageAsync(id, BitmapUtils.getBitmapFromDrawable(
                    getResources().getDrawable(drawableImage)));
        }
    }


    private void showNearPeople(Style style) {
        // Add Features which represent the location of each profile photo SymbolLayer icon
        Feature carlosFeature = Feature.fromGeometry(Point.fromLngLat(51.388121,
                35.728728));
        carlosFeature.addStringProperty("PROFILE_NAME", "Ali");

        Feature antonyFeature = Feature.fromGeometry(Point.fromLngLat(51.391962,
                35.720288));
        antonyFeature.addStringProperty("PROFILE_NAME", "Ara");

        Feature mariaFeature = Feature.fromGeometry(Point.fromLngLat(51.389178,
                35.712495));
        mariaFeature.addStringProperty("PROFILE_NAME", "Saeid");

        Feature lucianaFeature = Feature.fromGeometry(Point.fromLngLat(51.398295,
                35.708067));
        lucianaFeature.addStringProperty("PROFILE_NAME", "Mohammadreza");

        style.addSource(new GeoJsonSource("ICON_SOURCE_ID", FeatureCollection.fromFeatures(new Feature[] {
                carlosFeature,
                antonyFeature,
                mariaFeature,
                lucianaFeature})));


        style.addLayer(new SymbolLayer("ICON_LAYER_ID", "ICON_SOURCE_ID").withProperties(
                iconImage(get("PROFILE_NAME")),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
                //textField(get("PROFILE_NAME")),
                // textIgnorePlacement(true),
                // textAllowOverlap(true),
                // textColor(Color.BLACK),
                // textOffset(new Float[] {0f, 2f})
        ));


    }


    private void changeBaseMap(Style style) {
        Layer satelliteLayer = style.getLayer("SATELLITE_RASTER_LAYER_ID");
        // Create a data source for the satellite raster image and add the source to the map
        if (satelliteLayer == null) {
            style.addSource(new RasterSource("SATELLITE_RASTER_SOURCE_ID",
                    "mapbox://mapbox.satellite", 512));
            style.addLayer(
                    new RasterLayer("SATELLITE_RASTER_LAYER_ID", "SATELLITE_RASTER_SOURCE_ID"));
        } else {

            if (VISIBLE.equals(satelliteLayer.getVisibility().getValue())){
                satelliteLayer.setProperties(visibility(NONE));
            } else {
                satelliteLayer.setProperties(visibility(VISIBLE));
            }
        }



    }

    private void showWelcome() {
        Snackbar snk = Snackbar.make(drawerLayout, "Welcome to LAC-SDSS",
                Snackbar.LENGTH_LONG);
        snk.show();
    }


    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);

            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "permissionsToExplain",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "user_location_permission_not_granted", Toast.LENGTH_LONG).show();
        }
    }

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MapActivity> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(MapActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            MapActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

// Create a Toast which displays the new location's coordinates
//                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
//                        String.valueOf(result.getLastLocation().getLatitude()),
//                        String.valueOf(result.getLastLocation().getLongitude())),
//                        Toast.LENGTH_SHORT).show();

// Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }


        @Override
        public void onFailure(@NonNull Exception exception) {
            MapActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}