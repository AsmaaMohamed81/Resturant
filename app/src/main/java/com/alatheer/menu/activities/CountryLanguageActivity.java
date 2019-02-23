package com.alatheer.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.CountryAdapter;
import com.alatheer.menu.adapters.SliderAdapter;
import com.alatheer.menu.fagments.Fragment_Slider;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.Country;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class CountryLanguageActivity extends AppCompatActivity {
    private ViewPager pager;
    private ExpandableLayout expandLayout;
    private RecyclerView recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private ImageView image_flag, image_state;
    private TextView tv_title;
    private Button btn_en, btn_ar;
    private ProgressBar progBar;
    private SliderAdapter sliderAdapter;
    private Timer timer;
    private MyTimerTask myTimerTask;
    private List<Country> CountryModelList;
    Country CountryModel;
    private LinearLayout container;
    private View view;
    private Preferences preferences;
    private Animation animation;
    com.alatheer.menu.singletone.Countries countries;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        String lang = Paper.book().read("language");

        if (lang != null) {
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

        }

        super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_language);
        initView();
    }

    private void initView() {

       /* String lang = Paper.book().read("language");

        LanguageHelper.onAttach(this,lang);


        Log.e("lang",Paper.book().read("language"));
*/
        preferences = Preferences.getInstance();
        countries = com.alatheer.menu.singletone.Countries.getInstance();

        if (preferences.isChooseLang(this)) {
            Intent intent = new Intent(CountryLanguageActivity.this, ChooseLocationActivity.class);
            startActivity(intent);
            finish();

        }

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim90);

        CountryModelList = new ArrayList<>();
        view = findViewById(R.id.view);
        container = findViewById(R.id.container);
        pager = findViewById(R.id.pager);
        expandLayout = findViewById(R.id.expand_layout);
        recView = findViewById(R.id.recView);

        manager = new LinearLayoutManager(this);
        recView.setLayoutManager(manager);
        adapter = new CountryAdapter(this, CountryModelList);
        recView.setAdapter(adapter);


        image_flag = findViewById(R.id.image_flag);
        image_state = findViewById(R.id.image_state);
        tv_title = findViewById(R.id.tv_title);
        btn_en = findViewById(R.id.btn_en);
        btn_ar = findViewById(R.id.btn_ar);
        progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        pager.beginFakeDrag();

        sliderAdapter = new SliderAdapter(getSupportFragmentManager());
        sliderAdapter.AddFragment(Fragment_Slider.getInstance(getString(R.string.title1), R.drawable.slider1));
        sliderAdapter.AddFragment(Fragment_Slider.getInstance(getString(R.string.title3), R.drawable.slider2));
        sliderAdapter.AddFragment(Fragment_Slider.getInstance(getString(R.string.title2), R.drawable.slider3));
        pager.setAdapter(sliderAdapter);
        myTimerTask = new MyTimerTask();
        timer = new Timer();
        timer.scheduleAtFixedRate(myTimerTask, 6000, 10000);

        AddCountry();


        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_state.clearAnimation();
                image_state.startAnimation(animation);
                expandLayout.toggle(true);
            }
        });

        expandLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if (state == ExpandableLayout.State.EXPANDING) {


                    container.setBackgroundColor(ContextCompat.getColor(CountryLanguageActivity.this, R.color.white));
                    image_state.setImageResource(R.drawable.arrowcircleblack);
                    view.setVisibility(View.VISIBLE);
                    tv_title.setTextColor(ContextCompat.getColor(CountryLanguageActivity.this, R.color.black));

                } else if (state == ExpandableLayout.State.COLLAPSED) {
                    container.setBackgroundColor(ContextCompat.getColor(CountryLanguageActivity.this, R.color.transparent));

                    view.setVisibility(View.GONE);
                    tv_title.setTextColor(ContextCompat.getColor(CountryLanguageActivity.this, R.color.white));
                    image_state.setImageResource(R.drawable.arrowcircle);


                } else if (state == ExpandableLayout.State.COLLAPSING) {
                    image_state.setImageResource(R.drawable.arrowcircleblack);
                    tv_title.setTextColor(ContextCompat.getColor(CountryLanguageActivity.this, R.color.black));

                }
            }
        });


        Typeface ar_font = Typeface.createFromAsset(getAssets(), Tags.AR_FONT_NAME);
        Typeface en_font = Typeface.createFromAsset(getAssets(), Tags.EN_FONT_NAME);
        btn_ar.setTypeface(en_font);
        btn_ar.setTypeface(ar_font);

        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("language", "ar");


                preferences.UpdateChooseLang(CountryLanguageActivity.this, true);
                countries.setCountrydata(CountryModel);


                LanguageHelper.setLocality(CountryLanguageActivity.this, Paper.book().read("language"));
                refreshLayout();

            }
        });

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("language", "en");


                preferences.UpdateChooseLang(CountryLanguageActivity.this, true);
                countries.setCountrydata(CountryModel);


                LanguageHelper.setLocality(CountryLanguageActivity.this, Paper.book().read("language"));
                refreshLayout();

            }
        });
    }

    private void refreshLayout() {
        Intent intent = getIntent();
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    private void AddCountry() {
//        CountryModelList.add(new CountryModel("قطر",R.drawable.flag));
//        CountryModelList.add(new CountryModel("الكويت",R.drawable.flag));
//        CountryModelList.add(new CountryModel("السعودية",R.drawable.flag));
//        CountryModelList.add(new CountryModel("الامارات",R.drawable.flag));
//        adapter.notifyDataSetChanged();


        Api.getService()
                .getCountries()
                .enqueue(new Callback<List<Country>>() {
                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        if (response.isSuccessful()) {

                            CountryModelList.addAll(response.body());


                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        Toast.makeText(CountryLanguageActivity.this, "" + R.string.something, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void pos(int pos) {
        CountryModelList.get(pos);

        Log.e("country", CountryModelList.get(pos).getCountryName() + "");

        expandLayout.collapse();

        Picasso.with(CountryLanguageActivity.this).load(Tags.image_url + CountryModelList.get(pos).getCountryFlag()).into(image_flag);
        tv_title.setText(CountryModelList.get(pos).getCountryName());


        CountryModel = CountryModelList.get(pos);


        preferences.setCountry_NaionalityData(CountryLanguageActivity.this, CountryModel);
        countries.setCountrydata(CountryModel);


        String co = preferences.getCountry_Nationality(CountryLanguageActivity.this).getCountryIdPk();
        Log.e("ya", co + "");


    }


    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            CountryLanguageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pager.getCurrentItem() < pager.getChildCount() - 1) {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);

                    } else {
                        pager.setAdapter(sliderAdapter);

                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        myTimerTask.cancel();
    }
}
