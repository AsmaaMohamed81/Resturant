package com.alatheer.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.cityAdapter;
import com.alatheer.menu.adapters.governAdapter;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.City;
import com.alatheer.menu.models.Country;
import com.alatheer.menu.models.Govern;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.singletone.Countries;
import com.alatheer.menu.tags.Tags;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import io.paperdb.Paper;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseLocationActivity extends AppCompatActivity implements Countries.onCompleteListener {

    private LinearLayout container, container2;
    private ExpandableLayout expandableLayout, expandableLayout2;
    private RecyclerView recyc_View_govern, recyc_View_city;
    ArrayList<Govern> list_governs;
    ArrayList<City> list_city;
    governAdapter governAdapter;
    private cityAdapter cityAdapter;
    private Button btn_find;
    Country country;
    Countries countries;
    private RecyclerView.LayoutManager manager, manager2;
    private String id_city;


    private ImageView image_back, arrow_city, arrow_govern;
    private TextView tv_title, tv_title2, tv_no1, tv_no2;
    Govern govern;
    City city;


    Preferences preferences;
    private UserModel userModel;
    private boolean isClicked = true;
    private boolean isClicked2 = true;


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
        setContentView(R.layout.activity_choose_location2);
        if (isFirstTime()) {
            ShowIntro("قائمه المحافظات", Html.fromHtml("<font color='red'> اضغط لكي تختار المحافظه اولا </p>"), R.id.container, 1);
        }
        initView();

    }

    private void initView() {


        preferences = Preferences.getInstance();
        userModel = preferences.getUserModel(this);

//        if (preferences.getCITYData(ChooseLocationActivity.this).getCityName()!=null) {
//            Intent intent = new Intent(ChooseLocationActivity.this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//
//        }


        list_governs = new ArrayList<>();
        list_city = new ArrayList<>();
        govern = new Govern();
        city = new City();


        list_governs = preferences.getCountry_Nationality(this).getGoverns();


        btn_find = findViewById(R.id.btn_find);

        container = findViewById(R.id.container);
        container2 = findViewById(R.id.container2);

        expandableLayout = findViewById(R.id.expand_layout);
        expandableLayout2 = findViewById(R.id.expand_layout2);

        recyc_View_govern = findViewById(R.id.recView);
        recyc_View_city = findViewById(R.id.recView2);

        tv_no1 = findViewById(R.id.tv_no1);

        image_back = findViewById(R.id.image_back);


        //////////////
        arrow_city = findViewById(R.id.arrow_city);
        arrow_govern = findViewById(R.id.arrow_govern);

        changedirectionarrow();
        ////////////////

        tv_title = findViewById(R.id.tv_title);
        tv_title2 = findViewById(R.id.tv_title2);
////////////////////


        countries = Countries.getInstance();
        country = countries.getCountry();
        countries.getData(this);


        if (list_governs.size() > 0) {

            tv_no1.setVisibility(View.GONE);
            governAdapter = new governAdapter(this, list_governs);


            manager = new LinearLayoutManager(this);

            recyc_View_govern.setLayoutManager(manager);

            recyc_View_govern.setAdapter(governAdapter);
            governAdapter.notifyDataSetChanged();


        } else {
            tv_no1.setVisibility(View.VISIBLE);

        }


        //  Log.e("asmaa",country.getCountryName());
//        Log.e("asmaalist",list_governs.get(0).getGovernName());
//        Log.e("asmaalist2",list_city.get(0).getCityName());


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);

        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim90);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                preferences.UpdateChooseLang(ChooseLocationActivity.this, false);
                Intent intent = new Intent(ChooseLocationActivity.this, CountryLanguageActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isClicked) {


//                    arrow_govern.clearAnimation();
//
//                    arrow_govern.startAnimation(animation);

                    expandableLayout.toggle(true);

                    arrow_govern.setImageResource(R.drawable.dowen_circle_arrow);


                    isClicked = false;
                } else {

                    arrow_govern.setImageResource(R.drawable.left_circle_arrow);

                    expandableLayout.toggle(true);

                    isClicked = true;
                }


            }
        });
        container2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isClicked2) {


                    if (list_city.isEmpty()) {
                        Toast.makeText(ChooseLocationActivity.this, "Choose Govern First", Toast.LENGTH_SHORT).show();


                    } else {
                        expandableLayout2.toggle(true);

                        arrow_city.setImageResource(R.drawable.dowen_circle_arrow);


                        isClicked2 = false;
                    }

                } else {

                    arrow_city.setImageResource(R.drawable.left_circle_arrow);

                    expandableLayout2.toggle(true);

                    isClicked2 = true;
                }


            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_back.clearAnimation();
                image_back.startAnimation(animation);
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id_city != null) {

                    if (userModel != null) {
                        Intent intent = new Intent(ChooseLocationActivity.this, HomeActivity.class);
                        intent.putExtra("id_city", id_city);
                        Log.e("id_cityintente", id_city + "");

                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(ChooseLocationActivity.this, LoginActivity.class);
                        intent.putExtra("id_city", id_city);
                        Log.e("id_cityintente", id_city + "");

                        startActivity(intent);

                    }
                } else {

                    Toast.makeText(ChooseLocationActivity.this, "يجب اختيار مدينتك اولا " +
                            "" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void changedirectionarrow() {


        String lang = Paper.book().read("language");
        if (lang != null) {
            if (lang.equals("en")) {
                arrow_city.setRotation(180f);
                arrow_govern.setRotation(180f);

            }
        }
    }


    public void pos(int pos) {

        Log.e("asmaa", pos + "");

        govern = preferences.getCountry_Nationality(this).getGoverns().get(pos);
        manager2 = new LinearLayoutManager(this);


        list_city = govern.getCities();
        cityAdapter = new cityAdapter(this, list_city);

        recyc_View_city.setLayoutManager(manager2);
        recyc_View_city.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();


        tv_title.setText(govern.getGovernName());
        arrow_govern.setImageResource(R.drawable.left_circle_arrow);

        expandableLayout.toggle(true);

        isClicked = true;

    }

    @Override
    public void OnDataSuccess(Country country) {
        this.country = country;


    }


    private void ShowIntro(String title, Spanned text, int viewId, final int type) {

        new GuideView.Builder(this)
                .setTitle(title)
                .setTargetView(findViewById(viewId))
                .setContentTextSize(15)//optional
                .setTitleTextSize(20)//optional
                .setContentSpan((Spannable) text)
                .setGravity(Gravity.center)
                .setIndicatorHeight(30)
                .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (type == 1) {
                            ShowIntro("قائمه المدن", Html.fromHtml("<font color='red'> اضغط لكي تختار مدينتك اولا </p>"), R.id.container2, 2);
                        }
//                        else if (type == 4) {
//                            ShowIntro("Add Song", "Add your selected song on your video ", R.id.button_tool_music, 3);
//                        } else if (type == 3) {
//                            ShowIntro("Overlay", "Add your selected overlay effect on your video ", R.id.button_tool_overlay, 5);
//
//                        }
                        else if (type == 2) {
                            return;
                        }
                    }
                })
                .build()
                .show();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    public void poscity(int pos) {

        id_city = list_city.get(pos).getCityIdPk();
        Log.e("id_city", id_city + "");


        city = list_city.get(pos);
        preferences.setCITYData(this, city);
        preferences.UpdatCITYData(this, true);

        tv_title2.setText(list_city.get(pos).getCityName());
        arrow_city.setImageResource(R.drawable.left_circle_arrow);

        expandableLayout2.toggle(true);

        isClicked2 = true;


    }
}
