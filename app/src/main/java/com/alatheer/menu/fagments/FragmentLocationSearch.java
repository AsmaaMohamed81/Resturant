package com.alatheer.menu.fagments;

import android.Manifest;
import android.app.Activity;
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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.MapActivity;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.LocationModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.service.LocationService;
import com.alatheer.menu.tags.Tags;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by elashry on 30/09/2018.
 */

public class FragmentLocationSearch extends Fragment {
    private ImageView image_delete_search_txt,image_arrow;
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



    @Override
    public void onAttach(Context context) {

        Paper.init(context);
        String lang = Paper.book().read("language");

        if (Paper.book().read("language").equals("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }
        super.onAttach(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(context, lang)));
    }


    public static FragmentLocationSearch getInstance()
    {
        FragmentLocationSearch fragment = new FragmentLocationSearch();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locationsearch,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Log.e("dd","rwer");
        dialog = Common.createProgressDialog(getActivity(),getString(R.string.load_loc));

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        image_arrow = view.findViewById(R.id.image_arrow);
        image_delete_search_txt =view. findViewById(R.id.image_delete_search_txt);
        ll_delivered_diff_loc = view.findViewById(R.id.ll_delivered_diff_loc);
        ll_delivered_current_loc = view.findViewById(R.id.ll_delivered_current_loc);
        ll_delivered_elsewhere = view.findViewById(R.id.ll_delivered_elsewhere);
        tv_search = view.findViewById(R.id.tv_search);
        expandableLayout = view.findViewById(R.id.expand_layout);
        recView = view.findViewById(R.id.recView);
        manager = new LinearLayoutManager(getActivity());
        recView.setLayoutManager(manager);



        ll_delivered_diff_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                expandableLayout.toggle(true);
                ll_delivered_diff_loc.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));

                if (expandableLayout.getState()==ExpandableLayout.State.EXPANDING)
                {
                    image_arrow.animate().rotation(0).start();
                }else if (expandableLayout.getState()==ExpandableLayout.State.COLLAPSING)
                {

                    image_arrow.animate().rotation(180).start();
                    ll_delivered_diff_loc.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent));


                }else if (expandableLayout.getState()==ExpandableLayout.State.COLLAPSED)
                {
                    image_arrow.animate().rotation(180).start();

                }
            }
        });

        ll_delivered_elsewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapActivity.class);
                intent.putExtra("type","2"); 
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


        Geocoder geocoder = new Geocoder(getActivity(),new Locale("en"));
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
        preferences.UpdateFirstTime(getActivity());
        preferences.createUpdateLocation(getActivity(),locality);

        //gafinish();
    }

    private void StartService()
    {
        dialog.show();
        intentService = new Intent(getActivity(), LocationService.class);
       getActivity(). startService(intentService);
    }

    private void StopService()
    {
        if (intentService!=null)
        {
            getActivity().stopService(intentService);
        }
    }

    private void checkPermission()
    {
        String[] perm = {FineLoc};

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

            if (isGpsOpen())
            {
                StartService();
            }else
            {
                OpenGps();
            }
        }else
        {
            ActivityCompat.requestPermissions(getActivity(),perm,loc_req);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gps_req) {
            if (resultCode == Activity.RESULT_CANCELED) {
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
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
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

}
