package com.alatheer.menu.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_Add_Drinks;
import com.alatheer.menu.fagments.Fragment_Add_Food;
import com.alatheer.menu.fagments.Fragment_Add_Meal;
import com.alatheer.menu.fagments.Fragment_Create_Meal;
import com.alatheer.menu.languagehelper.LanguageHelper;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddItemActivity extends AppCompatActivity {

    private ImageView image_back;
    private Toolbar toolBar;
    private ExpandableLayout expand_layout;
    private TextView tv_title;
    private int rb_lastSelected = -1;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        String lang = Paper.book().read("language");

        if (lang!=null)
        {
            if (lang.equals("ar"))
            {
          /*  CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

*/
                super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));

            }else
            {
          /*  CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());*/
                super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, "en")));

            }
        }





    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initView();
    }

    private void initView() {
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        image_back = findViewById(R.id.image_back);
        expand_layout = findViewById(R.id.expand_layout);
        tv_title = findViewById(R.id.tv_title);

        String lang = Paper.book().read("language");
        if (lang!=null)
        {
            if (lang.equals("ar"))
            {
                image_back.setRotation(180f);
            }
        }
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UpdateTitle(getString(R.string.add_foods));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Add_Food.getInstance()).commit();

    }

    private void UpdateTitle(String title)
    {
        tv_title.setText(title);

    }


    private void CreateMealDialog()
    {

        View view = LayoutInflater.from(this).inflate(R.layout.alert_meal,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        AlertDialog alertDialog = dialog.create();

        RadioButton rb_exist_meal = view.findViewById(R.id.rb_exist_meal);
        RadioButton rb_create_meal = view.findViewById(R.id.rb_create_meal);
        FrameLayout fl_exist_meal = view.findViewById(R.id.fl_exist_meal);
        FrameLayout fl_create_meal = view.findViewById(R.id.fl_create_meal);

        if (rb_lastSelected==2)
        {
            rb_create_meal.setChecked(true);
            fl_exist_meal.setVisibility(View.INVISIBLE);
            fl_create_meal.setVisibility(View.VISIBLE);


        }else if (rb_lastSelected==1)
        {
            rb_exist_meal.setChecked(true);
            fl_exist_meal.setVisibility(View.VISIBLE);
            fl_create_meal.setVisibility(View.INVISIBLE);
        }
        rb_exist_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_exist_meal.isChecked())
                {
                    rb_lastSelected =1;
                    fl_exist_meal.setVisibility(View.VISIBLE);
                    fl_create_meal.setVisibility(View.INVISIBLE);
                    alertDialog.dismiss();

                    UpdateTitle(getString(R.string.exist_meal));

                    expand_layout.collapse(true);
                    new Handler()
                            .postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Add_Meal.getInstance()).commit();
                                    expand_layout.expand(true);
                                }
                            },2200);

                }



            }
        });

        rb_create_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_create_meal.isChecked())
                {
                    rb_lastSelected =2;
                    fl_exist_meal.setVisibility(View.INVISIBLE);
                    fl_create_meal.setVisibility(View.VISIBLE);
                    alertDialog.dismiss();
                    UpdateTitle(getString(R.string.create_meal));

                    expand_layout.collapse(true);
                    new Handler()
                            .postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Create_Meal.getInstance()).commit();
                                    expand_layout.expand(true);
                                }
                            },2200);


                }







            }
        });

        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_food_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id =item.getItemId();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        switch (id)
        {

            case R.id.add_food:
                if (fragment instanceof Fragment_Add_Food)
                {

                }else
                    {
                        UpdateTitle(getString(R.string.add_foods));

                        expand_layout.collapse(true);
                        new Handler()
                                .postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Add_Food.getInstance()).commit();
                                        expand_layout.expand(true);
                                    }
                                },2200);

                    }

                break;
            case R.id.add_meal:

                if (fragment instanceof Fragment_Add_Drinks)
                {

                }else
                {

                    CreateMealDialog();

                }

                break;
                case R.id.add_drink:
                    if (fragment instanceof Fragment_Add_Drinks)
                    {

                    }else
                    {
                        UpdateTitle(getString(R.string.add_drinks));

                        expand_layout.collapse(true);

                        new Handler()
                                .postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Add_Drinks.getInstance()).commit();
                                        expand_layout.expand(true);
                                    }
                                },2200);

                    }

                    break;
        }

        return true;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment:fragmentList)
        {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment:fragmentList)
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }



    }
}
