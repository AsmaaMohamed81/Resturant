package com.alatheer.menu.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.LocationModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.service.LocationService;
import com.alatheer.menu.tags.Tags;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LocationSearchActivity extends AppCompatActivity {

    private ImageView image_back,image_delete_search_txt,image_arrow;
    private LinearLayout ll_delivered_diff_loc,ll_delivered_current_loc,ll_delivered_elsewhere;
    private AutoCompleteTextView tv_search;
    private ExpandableLayout expandableLayout;
    private RecyclerView recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private final String FineLoc = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 5520;
    private final int gps_req = 526;
    private LocationManager locationManager;
    private Intent intentService;
    private ProgressDialog dialog;
    private Preferences preferences;
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
        setContentView(R.layout.activity_location_search);

        initView();
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
         dialog = Common.createProgressDialog(this,getString(R.string.load_loc));

        EventBus.getDefault().register(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        image_back = findViewById(R.id.image_back);
        image_arrow = findViewById(R.id.image_arrow);
        image_delete_search_txt = findViewById(R.id.image_delete_search_txt);
        ll_delivered_diff_loc = findViewById(R.id.ll_delivered_diff_loc);
        ll_delivered_current_loc = findViewById(R.id.ll_delivered_current_loc);
        image_back = findViewById(R.id.image_back);
        ll_delivered_elsewhere = findViewById(R.id.ll_delivered_elsewhere);
        tv_search = findViewById(R.id.tv_search);
        expandableLayout = findViewById(R.id.expand_layout);
        recView = findViewById(R.id.recView);
        manager = new LinearLayoutManager(this);
        recView.setLayoutManager(manager);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.press_anim);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (type.equals("1"))
                {
                    Intent intent = new Intent(LocationSearchActivity.this,ChooseLocationActivityold.class);
                    startActivity(intent);
                    finish();
                }else
                    {
                        finish();
                    }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_back.clearAnimation();
                image_back.startAnimation(animation);
            }
        });


        ll_delivered_diff_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                expandableLayout.toggle(true);
                ll_delivered_diff_loc.setBackgroundColor(ContextCompat.getColor(LocationSearchActivity.this,R.color.white));

                if (expandableLayout.getState()==ExpandableLayout.State.EXPANDING)
                {
                    image_arrow.animate().rotation(0).start();
                }else if (expandableLayout.getState()==ExpandableLayout.State.COLLAPSING)
                {

                    image_arrow.animate().rotation(180).start();
                    ll_delivered_diff_loc.setBackgroundColor(ContextCompat.getColor(LocationSearchActivity.this,R.color.transparent));


                }else if (expandableLayout.getState()==ExpandableLayout.State.COLLAPSED)
                {
                    image_arrow.animate().rotation(180).start();

                }
            }
        });

        ll_delivered_elsewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationSearchActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        image_delete_search_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search.setText(null);
                image_delete_search_txt.setVisibility(View.INVISIBLE);
            }
        });


        ll_delivered_current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();

            }
        });

        tv_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tv_search.getText().toString().length()>0)
                {
                    image_delete_search_txt.setVisibility(View.VISIBLE);
                }else
                    {
                        image_delete_search_txt.setVisibility(View.INVISIBLE);

                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMyLocation(LocationModel locationModel)
    {

       getLocationDetails(locationModel);

    }

    private void getLocationDetails(LocationModel locationModel) {


        Geocoder geocoder = new Geocoder(this,new Locale("en"));
        try {
            List<Address> addressList = geocoder.getFromLocation(locationModel.getLat(),locationModel.getLng(),1);
            if (addressList.size()>0)
            {
                Address address = addressList.get(0);
                if (address!=null)
                {
                    String country_code = address.getCountryCode();
                    String locality = address.getLocality();

                    Log.e("country_code",country_code);
                    Log.e("locality",locality);
                    Log.e("aa",address.getFeatureName()+"++++");
                    Log.e("aa",address.getThoroughfare()+"++++");
                    Log.e("aa",address.getAdminArea()+"+++");

                    dialog.dismiss();
                    UpdateLocation(locality);




                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void UpdateLocation(String locality) {
        preferences = Preferences.getInstance();
        preferences.UpdateFirstTime(this);
        preferences.createUpdateLocation(this,locality);

        Intent intent = new Intent(LocationSearchActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void StartService()
    {
        dialog.show();
        intentService = new Intent(LocationSearchActivity.this, LocationService.class);
        startService(intentService);
    }

    private void StopService()
    {
        if (intentService!=null)
        {
            stopService(intentService);
        }
    }

    private void checkPermission()
    {
        String[] perm = {FineLoc};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

            if (isGpsOpen())
            {
                StartService();
            }else
            {
                OpenGps();
            }
        }else
        {
            ActivityCompat.requestPermissions(this,perm,loc_req);
        }



    }
    private boolean isGpsOpen() {
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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

                    StartService();
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
                        StartService();
                    }else
                    {
                        OpenGps();
                    }
                }


            }


        }

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        StopService();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (type.equals("1"))
        {
            Intent intent = new Intent(LocationSearchActivity.this,ChooseLocationActivityold.class);
            startActivity(intent);
            finish();
        }else
        {
            super.onBackPressed();
        }

    }
}
