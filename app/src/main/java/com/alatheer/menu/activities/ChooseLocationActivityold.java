package com.alatheer.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.tags.Tags;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseLocationActivityold extends AppCompatActivity {

    private ImageView image_arrow,image_back;
    private Button btn_find;
    private Preferences preferences;
    private LinearLayout ll_select_area,ll_diff_loc;

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
        setContentView(R.layout.activity_choose_location);

        initView();
    }

    private void initView() {


        preferences  = Preferences.getInstance();
        String m_courntid=preferences.getCountry_Nationality(this).getCountryIdPk();

        Log.e("asasaa",m_courntid+"");

        image_arrow = findViewById(R.id.image_arrow);
        image_back = findViewById(R.id.image_back);
        ll_select_area = findViewById(R.id.ll_select_area);
        ll_diff_loc = findViewById(R.id.ll_diff_loc);


        btn_find=findViewById(R.id.btn_find);

        if (Paper.book().read("language").equals("ar"))
        {
            image_arrow.setRotation(180);
            image_back.setRotation(180);

        }

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.press_anim);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                preferences.UpdateChooseLang(ChooseLocationActivityold.this,false);
                Intent intent = new Intent(ChooseLocationActivityold.this,CountryLanguageActivity.class);
                startActivity(intent);
                finish();
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

        ll_select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivityold.this,LocationSearchActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                finish();
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivityold.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        ll_diff_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocationActivityold.this,MapActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                finish();


            }
        });



    }

    @Override
    public void onBackPressed() {
        preferences.UpdateChooseLang(ChooseLocationActivityold.this,false);
        Intent intent = new Intent(ChooseLocationActivityold.this,CountryLanguageActivity.class);
        startActivity(intent);
        finish();
    }
}
