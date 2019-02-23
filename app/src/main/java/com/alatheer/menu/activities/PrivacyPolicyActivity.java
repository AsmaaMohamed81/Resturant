package com.alatheer.menu.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.aboutUsModel;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.tags.Tags;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private ImageView image_back;
    private TextView txt_privacy;
    SmoothProgressBar smoothProgressBar;
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
        setContentView(R.layout.activity_privacy_policy);


        initView();
    }

    private void initView() {
        image_back =findViewById(R.id.image_back);
        txt_privacy=findViewById(R.id.txt_privacy);
        smoothProgressBar=findViewById(R.id.SmoothProgressBar);

        getDataService();

        String language = Paper.book().read("language");
        if (language.equals("ar"))
        {
            image_back.setRotation(180);
        }else if (language.equals("en"))
        {
            image_back.setRotation(360);

        }
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.press_anim);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
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
    }

    private void getDataService() {
        Api.getService()
                .getprivacy()
                .enqueue(new Callback<aboutUsModel>() {
                    @Override
                    public void onResponse(Call<aboutUsModel> call, Response<aboutUsModel> response) {
                        if (response.isSuccessful()){

                            txt_privacy.setText(response.body().getContent());

                            smoothProgressBar.progressiveStop();
                        }
                    }

                    @Override
                    public void onFailure(Call<aboutUsModel> call, Throwable t) {

                    }
                });
    }
}
