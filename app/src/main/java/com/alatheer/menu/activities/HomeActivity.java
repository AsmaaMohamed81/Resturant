package com.alatheer.menu.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.FragmentLocationSearch;
import com.alatheer.menu.fagments.Fragment_Chif;
import com.alatheer.menu.fagments.Fragment_Filter;
import com.alatheer.menu.fagments.Fragment_Home_Restaurants;
import com.alatheer.menu.fagments.Fragment_Search;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.singletone.UserSingleTone;
import com.alatheer.menu.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    public FragmentManager fragmentManager;
    private Fragment_Home_Restaurants fragment_home;
    private Fragment_Filter fragment_filter;
    private EditText edt_search;
    private ImageView img_filter;
    private boolean flag = false;
    private ImageView fab;
    private View root;
    private BottomSheetBehavior behavior;
    private ImageView img_sheet_close, nave_flag;
    private CollapsingToolbarLayout collapse_layout;
    private TextView tv_not_budget;
    private TextView tv_login_register, tv_title, tv_name, tv_mail, nav_txt, nav_change;
    private FragmentLocationSearch fragmentLocationSearch;
    private Fragment_Search fragmentSearch;
    private LinearLayout ll_loc, search_container;
    private String id_city, name_country, img_country;
    private UserSingleTone userSingleTone;
    private UserModel userModel, userModel2;
    private Preferences preferences;
    private Button search,fake;

    private String qeuryy, numberorder;
    String id = null;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
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

        super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Paper.init(this);
        LanguageHelper.onAttach(this, Paper.book().read("language"));

        preferences = Preferences.getInstance();

        id_city = preferences.getCITYData(this).getCityIdPk();
        name_country = preferences.getCountry_Nationality(this).getCountryName();
        img_country = preferences.getCountry_Nationality(this).getCountryFlag();
//        id_city = getIntent().getStringExtra("id_city");
        Log.e("home", id_city + "");

        initView();
        AddFragmentHome();


    }

    private void initView() {


        userSingleTone = userSingleTone.getInstance();


        if (userSingleTone.getUserModel() != null) {
            Log.d("loggggg", userSingleTone.getUserModel().getUser_id());

        } else {

            Log.d("idjkjdksjkdjslk", "nooooooooooo");

        }

//        Toast.makeText(this, "getUser_id : "+userSingleTone.getRestModel().getUser_id(), Toast.LENGTH_SHORT).show();
        userModel = preferences.getUserModel(this);
        fragmentManager = getSupportFragmentManager();
        ////////////////////////////////////////////////////////
        tv_not_budget = findViewById(R.id.tv_not_budget);
        collapse_layout = findViewById(R.id.collapse_layout);
        search_container = findViewById(R.id.search_container);
        root = findViewById(R.id.root);
        img_sheet_close = findViewById(R.id.img_close);
        tv_title = findViewById(R.id.tv_title);
        ll_loc = findViewById(R.id.ll_loc);
        fake=findViewById(R.id.fake);

        behavior = BottomSheetBehavior.from(root);

        // Toast.makeText(this, ""+userModel.getUser_name(), Toast.LENGTH_SHORT).show();


        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ////////////////////////////////////////////////////////
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        edt_search = findViewById(R.id.edt_search);
        search = findViewById(R.id.btn_search);
        img_filter = findViewById(R.id.img_filter);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);


        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        tv_login_register = view.findViewById(R.id.tv_login_register);


        ///////////////////
        tv_name = view.findViewById(R.id.tv_name);
        tv_mail = view.findViewById(R.id.tv_mail);

        nav_txt = view.findViewById(R.id.nav_txt);
        nav_txt.setText(name_country);

        nave_flag = view.findViewById(R.id.nave_flag);
        Picasso.with(this).load(Tags.image_url + img_country).into(nave_flag);

        nav_change = view.findViewById(R.id.nav_change);
        nav_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CountryLanguageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /////////////////////////

        if (userModel != null) {
            tv_mail.setText(userModel.getUser_email());
            tv_name.setText(userModel.getUser_name());


            tv_login_register.setVisibility(View.GONE);
            tv_name.setVisibility(View.VISIBLE);
            tv_mail.setVisibility(View.VISIBLE);
        }
        ////////////////////////////////////////////////////////
        String lang = Paper.book().read("language");
        if (lang == null) {
            Paper.book().write("language", "ar");
        }
        updateLayout(Paper.book().read("language"));


        /////////////////////////

        UpDateUi(userModel);

        ////////////////////////
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_filter.clearAnimation();
                img_filter.startAnimation(animation);

                if (flag == false) {

                    disableScroll();
                    img_filter.setImageResource(R.drawable.red_filter);
                    fab.setVisibility(View.GONE);
                    AddFragmentFilter();
                    flag = true;
                    //ll_loc.setEnabled(false);
                } else {
                    enableScroll();
                    img_filter.setImageResource(R.drawable.filter);
                    fragmentManager.popBackStack();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.show(fragmentManager.findFragmentByTag("fragment_home")).commit();
                    setFabAnim();
                    flag = false;
                    //ll_loc.setEnabled(true);


                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Chif fragment_chif = Fragment_Chif.getInstance();

                Bundle bundle = new Bundle();
                bundle.putString("id_city", id_city);
                fragment_chif.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame, fragment_chif)
                        .commit();
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
        });


        img_sheet_close.setOnClickListener(v -> behavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
        tv_login_register.setOnClickListener(v -> {

            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            drawer.closeDrawer(GravityCompat.START);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }, 300);

        });

        ll_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fragmentManager.findFragmentByTag("fragment_home") != null && fragmentManager.findFragmentByTag("fragment_home").isVisible()) {

                    AddFragmentLocation();
                    fab.setVisibility(View.GONE);


                } else if (fragmentManager.findFragmentByTag("fragment_filter") != null && fragmentManager.findFragmentByTag("fragment_filter").isVisible()) {
                    fragmentManager.popBackStack();
                    flag = false;
                    img_filter.setImageResource(R.drawable.filter);
                    AddFragmentLocation();


                } else {


                    AddFragmentHome();
                    setFabAnim();


                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edt_search.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    search(query);
                    Toast.makeText(HomeActivity.this, "click" + qeuryy, Toast.LENGTH_SHORT).show();

                    AddFragmentSearch();


                }
            }
        });
        setFabAnim();

/////////////////////////////////////////////
        fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Token", "onClick: "+ FirebaseInstanceId.getInstance().getToken());
            }
        });


    }

    private void UpDateUi(UserModel userModel) {


    }


    private void search(String query) {
        Log.e("query", query);
        qeuryy = query;


    }


    private void AddFragmentHome() {

        fragment_home = (Fragment_Home_Restaurants) fragmentManager.findFragmentByTag("fragment_home");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentLocationSearch != null) {

            fragmentManager.popBackStack();
        } else if (fragment_filter != null) {
            flag = false;
            img_filter.setImageResource(R.drawable.filter);
            fragmentManager.popBackStack();
            Log.e("2", "2");

        }
        if (fragment_home != null) {
            Log.e("0", "0");
            fragment_home = Fragment_Home_Restaurants.getInstance();


            Bundle bundle = new Bundle();
            bundle.putString("id_city", id_city);
            bundle.putString("qeuryy", qeuryy);

            fragment_home.setArguments(bundle);

            transaction.replace(R.id.fragment_home_container, fragment_home);

            transaction.commit();
            search_container.setVisibility(View.VISIBLE);

        } else {

            Log.e("1", "1");

            fragment_home = Fragment_Home_Restaurants.getInstance();
            transaction.add(R.id.fragment_home_container, fragment_home, "fragment_home");
            transaction.setCustomAnimations(R.anim.fragment_silde_up, R.anim.fragment_silde_down, R.anim.fragment_silde_up, R.anim.fragment_silde_down);
            transaction.addToBackStack("Fragment_Home_Restaurants");

            Bundle bundle = new Bundle();
            bundle.putString("id_city", id_city);
            bundle.putString("qeuryy", qeuryy);

            fragment_home.setArguments(bundle);

            transaction.commit();
            search_container.setVisibility(View.VISIBLE);

        }


    }

    private void AddFragmentSearch() {

        fragmentSearch = (Fragment_Search) fragmentManager.findFragmentByTag("fragment_search");
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (fragmentSearch != null) {


            transaction.hide(fragment_home);
            transaction.show(fragmentSearch);
            transaction.commit();
            search_container.setVisibility(View.GONE);


        } else {

            fragmentSearch = Fragment_Search.getInstance();

            transaction.add(R.id.fragment_home_container, fragmentSearch, "fragment_search");
            transaction.setCustomAnimations(R.anim.fragment_silde_up, R.anim.fragment_silde_down, R.anim.fragment_silde_up, R.anim.fragment_silde_down);
            transaction.addToBackStack("fragment_search");
            transaction.hide(fragment_home);

            Bundle bundle = new Bundle();
            bundle.putString("qeuryy", qeuryy);

            fragmentSearch.setArguments(bundle);

            transaction.commit();
            search_container.setVisibility(View.GONE);

        }


    }

    private void AddFragmentLocation() {

        fragmentLocationSearch = (FragmentLocationSearch) fragmentManager.findFragmentByTag("fragment_location");
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (fragmentLocationSearch != null) {


            transaction.hide(fragment_home);
            transaction.show(fragmentLocationSearch);
            transaction.commit();
            search_container.setVisibility(View.GONE);


        } else {

            fragmentLocationSearch = FragmentLocationSearch.getInstance();

            transaction.add(R.id.fragment_home_container, fragmentLocationSearch, "fragment_location");
            transaction.setCustomAnimations(R.anim.fragment_silde_up, R.anim.fragment_silde_down, R.anim.fragment_silde_up, R.anim.fragment_silde_down);
            transaction.addToBackStack("Fragment_Location");
            transaction.hide(fragment_home);
            transaction.commit();
            search_container.setVisibility(View.GONE);

        }


    }


    private void AddFragmentFilter() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment_filter = (Fragment_Filter) fragmentManager.findFragmentByTag("fragment_filter");
        if (fragment_filter != null) {
            transaction.hide(fragment_home);
            transaction.show(fragment_filter);
            transaction.commit();
        } else {
            fragment_filter = Fragment_Filter.getInstance();
            transaction.add(R.id.fragment_home_container, fragment_filter, "fragment_filter");
            transaction.addToBackStack("Fragment_Filter");
            transaction.hide(fragment_home);
            transaction.commit();
        }


    }

    private void enableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                collapse_layout.getLayoutParams();
        params.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        );
        collapse_layout.setLayoutParams(params);
    }

    private void disableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                collapse_layout.getLayoutParams();
        params.setScrollFlags(0);
        collapse_layout.setLayoutParams(params);
    }

    public void setFabAnim() {

        fab.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_anim);
        animation.setDuration(1000);
        fab.clearAnimation();
        fab.startAnimation(animation);
    }

    public void IncreaseNotification(int count) {
        if (count > 0) {
            tv_not_budget.setVisibility(View.VISIBLE);
            ScaleAnimation animation = new ScaleAnimation(.5f, 1f, .5f, 1f, 50f, 50f);
            animation.setDuration(500);
            tv_not_budget.setText(String.valueOf(count));
            tv_not_budget.clearAnimation();
            tv_not_budget.startAnimation(animation);
        } else if (count == 0) {
            tv_not_budget.setVisibility(View.INVISIBLE);

        }


    }

    private void updateLayout(String language) {
        LanguageHelper.setLocality(this, language);

    }

    private void RefreshLayout() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.lang:
                drawer.closeDrawer(GravityCompat.START);

                CreateLanguageDialog();

                break;

            case R.id.care:
                drawer.closeDrawer(GravityCompat.START);


                CreateServiceDialog();


                break;
            case R.id.about:
                drawer.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(intent);
                    }
                }, 300);


                break;
            case R.id.faqs:
                drawer.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent2 = new Intent(HomeActivity.this, FAQsActivity.class);
                        startActivity(intent2);
                    }
                }, 300);


                break;

            case R.id.logout:

                if (userModel != null) {
                    userModel = null;
                    preferences.ClearData(this);
                    userSingleTone.clearData();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();

                }
                break;
        }

        return true;
    }

    private void CreateLanguageDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.custom_lang_alert_dialog, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        AlertDialog alertDialog = dialog.create();

        RadioButton rb_ar = view.findViewById(R.id.rb_ar);
        RadioButton rb_en = view.findViewById(R.id.rb_en);
        FrameLayout fl_ar = view.findViewById(R.id.fl_ar);
        FrameLayout fl_en = view.findViewById(R.id.fl_en);

        String lang = Paper.book().read("language");
        if (lang.equals("en")) {
            rb_en.setChecked(true);
            fl_ar.setVisibility(View.INVISIBLE);
            fl_en.setVisibility(View.VISIBLE);

        } else if (lang.equals("ar")) {
            rb_ar.setChecked(true);
            fl_ar.setVisibility(View.VISIBLE);
            fl_en.setVisibility(View.INVISIBLE);
        }
        rb_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_ar.isChecked()) {
                    fl_ar.setVisibility(View.VISIBLE);
                    fl_en.setVisibility(View.INVISIBLE);
                    alertDialog.dismiss();
                    Paper.book().write("language", "ar");
                    updateLayout((String) Paper.book().read("language"));
                    RefreshLayout();
                }


            }
        });

        rb_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_en.isChecked()) {
                    fl_ar.setVisibility(View.INVISIBLE);
                    fl_en.setVisibility(View.VISIBLE);
                    alertDialog.dismiss();
                    Paper.book().write("language", "en");
                    updateLayout((String) Paper.book().read("language"));
                    RefreshLayout();

                }


            }
        });

        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

        alertDialog.show();

    }

    private void CreateServiceDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.custom_client_service_dialog, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        AlertDialog alertDialog = dialog.create();

        TextView tv_customer_care = view.findViewById(R.id.tv_customer_care);
        TextView tv_chat = view.findViewById(R.id.tv_chat);


        tv_customer_care.setOnClickListener(v -> {

            alertDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "22222775"));
            startActivity(intent);


        });

        tv_chat.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(HomeActivity.this, Contact_usActivity.class);
            startActivity(intent);


        });

        alertDialog.setView(view);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentByTag("fragment_home");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (fragment instanceof Fragment_Home_Restaurants) {
            finish();
            super.onBackPressed();

        } else {
            enableScroll();
            img_filter.setImageResource(R.drawable.filter);
            fragmentManager.popBackStack();
            AddFragmentHome();
            setFabAnim();
        }


    }

    public void setItem(View view) {

        Intent intent = new Intent(this, RestaurantMainMenuActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "image");
        startActivity(intent, optionsCompat.toBundle());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                numberorder = data.getStringExtra("numberorder");

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
