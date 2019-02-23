package com.alatheer.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.tags.Tags;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout ll;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        String lang = Paper.book().read("language");
        if (lang!=null)
        {
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
                Log.e("d","dfdf");

            }
        }

        super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        preferences =Preferences.getInstance();
        ll = findViewById(R.id.ll);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.VISIBLE);
                ll.startAnimation(animation);

            }
        },300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (preferences.isFirstTime(SplashActivity.this))
                        {
                            if (preferences.isChooseLang(SplashActivity.this))
                            {
                                if (preferences.iCITYData(SplashActivity.this)) {
                                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else {
                                    Intent intent = new Intent(SplashActivity.this,ChooseLocationActivity.class);
                                    startActivity(intent);
                                    finish();

                                }


                            }else
                            {
                                Intent intent = new Intent(SplashActivity.this,CountryLanguageActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }else
                        {
                            Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                },200);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
