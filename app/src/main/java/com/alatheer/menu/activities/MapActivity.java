package com.alatheer.menu.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.PlaceAutocompleteAdapter;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.connection.NetworkConnection;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.LocationModel;
import com.alatheer.menu.service.LocationService;
import com.alatheer.menu.tags.Tags;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks {
    private ImageView image_back;
    private AutoCompleteTextView tv_search;
    private FloatingActionButton fab;
    private Button btn_confirm;
    private final int error_dialog = 9001;
    private LocationManager manager;
    private final String FineLoc = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 5520;
    private final int gps_req = 526;
    private GoogleMap mMap;
    private Marker marker;
    private Intent intentService ;
    private double myLat=0.0,myLng=0.0;
    private final float zooming = 16.5f;
    private GoogleApiClient googleApiClient;
    private LatLngBounds latLngBounds = new LatLngBounds(
            new LatLng(-33.880490, 151.184363),
            new LatLng(-33.858754, 151.229596));
    private AutocompleteFilter filter;

    private PlaceAutocompleteAdapter adapter;
    private GeoDataClient geoDataClient;
    private String type;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        String lang = Paper.book().read("language");
        if (Paper.book().read("language").equals("ar"))
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

        }else
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }

        super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        EventBus.getDefault().register(this);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            type = intent.getStringExtra("type");
        }
    }

    private void initView() {
        image_back = findViewById(R.id.image_back);
        tv_search = findViewById(R.id.tv_search);
        fab = findViewById(R.id.fab);
        btn_confirm = findViewById(R.id.btn_confirm);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (isServiceOk()) {

           checkPermission();
        }

        fab.setOnClickListener(v -> {
            marker.setPosition(new LatLng(myLat,myLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat,myLng),zooming));
        });

        if (Paper.book().read("language").equals("ar"))
        {
            image_back.setRotation(180f);
        }

        image_back.setOnClickListener(v ->

                {
                    if (type.equals("1"))
                    {
                        Intent intent = new Intent(MapActivity.this,ChooseLocationActivityold.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        finish();
                    }
                }

        );

    }

    private void checkPermission()
    {
        String[] perm = {FineLoc};

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

                if (isGpsOpen())
                {
                    initMap();
                }else
                {
                    OpenGps();
                }
            }else
                {
                    ActivityCompat.requestPermissions(this,perm,loc_req);
                }



    }

    private void initGoogleApi()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    private void initMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.setIndoorEnabled(true);
            mMap.setTrafficEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map));
            try {
                mMap.setMyLocationEnabled(true);

            }catch (SecurityException e){}
            initGoogleApi();

            startLocationUpdate();

            geoDataClient = Places.getGeoDataClient(this,null);

            filter = new AutocompleteFilter.Builder()
                    .setCountry("eg")
                    .build();

            adapter = new PlaceAutocompleteAdapter(this,geoDataClient,latLngBounds,filter);
            if (NetworkConnection.getConnection(MapActivity.this))
            {
                tv_search.setAdapter(adapter);
                tv_search.setOnItemClickListener(itemClickListener);
            }else
                {
                    Toast.makeText(this, R.string.inter, Toast.LENGTH_SHORT).show();
                }




        }

    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AutocompletePrediction item = adapter.getItem(i);
            String place_id = item.getPlaceId();
            PendingResult<PlaceBuffer> bufferPendingResult = Places.GeoDataApi.getPlaceById(googleApiClient,place_id);
            bufferPendingResult.setResultCallback(resultCallback);

        }
    };

    private ResultCallback<PlaceBuffer> resultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            try {
                if (!places.getStatus().isSuccess())
                {
                    places.release();
                    return;
                }
                tv_search.setText(null);
                Common.closeKeyboard(tv_search,MapActivity.this);
                Place place = places.get(0);
                myLat = place.getLatLng().latitude;
                myLat = place.getLatLng().longitude;



                places.release();
            }catch (NullPointerException e)
            {

            }

        }
    };

    private void  AddMarker(double lat,double lng,boolean zoom)
    {

        if (marker==null)
        {
            marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(getBitmapMarkerIcon(BitmapFactory.decodeResource(getResources(),R.drawable.map_pin)))).position(new LatLng(lat,lng)));


        }
        mMap.setOnCameraChangeListener(cameraPosition -> marker.setPosition(new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zooming));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMyLocation(LocationModel locationModel)
    {
        fab.setVisibility(View.VISIBLE);
        this.myLat = locationModel.getLat();
        this.myLng = locationModel.getLng();

        AddMarker(locationModel.getLat(),locationModel.getLng(),true);
    }

    private void startLocationUpdate() {
        intentService = new Intent(MapActivity.this, LocationService.class);
        startService(intentService);
    }


    private boolean isServiceOk() {
        int availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (availability == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availability)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, availability, error_dialog);
            dialog.show();
        }
        return false;
    }


    private boolean isGpsOpen() {
        if (manager != null) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return true;
    }


    private void OpenGps() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent,gps_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gps_req) {
            if (resultCode == RESULT_CANCELED) {
                if (isGpsOpen()) {
                    initMap();
                }else
                {
                    OpenGps();

                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == loc_req) {
            if (grantResults.length > 0) {

                 if (grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }else
                        {
                            if (isGpsOpen())
                            {
                                initMap();
                            }else
                            {
                                OpenGps();
                            }
                        }


                }


            }

    }


    private Bitmap getBitmapMarkerIcon(Bitmap bitmap)
    {
        int req_width = 90;
        int req_height = 90;
        float width_scale = (float) req_width/bitmap.getWidth();
        float height_scale = (float) req_height/bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(width_scale,height_scale);

        Bitmap returnedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return returnedBitmap;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient!=null)
        {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onDestroy() {
        stopService();
        if (googleApiClient!=null)
        {
            googleApiClient.disconnect();
        }
        super.onDestroy();
    }

    private void stopService() {
        if (intentService!=null)
        {
            stopService(intentService);
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (type.equals("1"))
        {
            Intent intent = new Intent(MapActivity.this,ChooseLocationActivityold.class);
            startActivity(intent);
            finish();
        }else
        {
            super.onBackPressed();
        }
    }
}
