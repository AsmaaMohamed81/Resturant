package com.alatheer.menu.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.UserSingleTone;
import com.alatheer.menu.tags.Tags;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText edt_email, edt_password, edt_first_name, edt_second_name, medt_phone;
    private TextInputLayout email_container, password_container, first_name_container, second_name_container;
    private LinearLayout txt_terms_register;
    private Button register_btn;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private Preferences preferences;
    private UserSingleTone userSingleTone;

    String m_email, m_password, m_first_name, m_phone, m_courntid;

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
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();

        img_back = findViewById(R.id.img_back);
        edt_email = findViewById(R.id.edtEmail);
        edt_password = findViewById(R.id.edtPassword);
        medt_phone = findViewById(R.id.edt_phone);
        edt_first_name = findViewById(R.id.edt_first_name);

        email_container = findViewById(R.id.email_container);
        password_container = findViewById(R.id.password_container);
        first_name_container = findViewById(R.id.first_name_container);

        txt_terms_register = findViewById(R.id.txt_terms_register);
        register_btn = findViewById(R.id.register_btn);

        m_courntid = preferences.getCountry_Nationality(RegisterActivity.this).getCountryIdPk();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);

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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_back.clearAnimation();
                img_back.startAnimation(animation);
            }
        });

        ////////////////////////////////////

        txt_terms_register.setOnClickListener(v ->
                {
                    Intent i = new Intent(RegisterActivity.this, AboutActivity.class);
                    startActivity(i);

                }
        );

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckData();
            }
        });

        CreateProgressDialog();
        CreateAlertDialog();
    }

    private void CreateProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setMessage(getString(R.string.wait));
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
    }

    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.inv_cred)
                .setPositiveButton(R.string.ok_txt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    private void CheckData() {
        m_email = edt_email.getText().toString();
        m_password = edt_password.getText().toString();
        m_first_name = edt_first_name.getText().toString();
        m_phone = medt_phone.getText().toString();


        if (TextUtils.isEmpty(m_first_name)) {
            first_name_container.setErrorEnabled(true);
            first_name_container.setError(getString(R.string.name_req));

        } else if (TextUtils.isEmpty(m_email)) {
            first_name_container.setErrorEnabled(false);
            first_name_container.setError(null);


            email_container.setErrorEnabled(true);
            email_container.setError(getString(R.string.email_req));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(m_email).matches()) {
            first_name_container.setErrorEnabled(false);
            first_name_container.setError(null);


            email_container.setErrorEnabled(true);
            email_container.setError(getString(R.string.email_inv));
        } else if (TextUtils.isEmpty(m_password)) {
            first_name_container.setErrorEnabled(false);
            first_name_container.setError(null);


            email_container.setErrorEnabled(false);
            email_container.setError(null);

            password_container.setErrorEnabled(true);
            password_container.setError(getString(R.string.pass_req));
        } else if (m_password.length() < 8) {
            first_name_container.setErrorEnabled(false);
            first_name_container.setError(null);


            email_container.setErrorEnabled(false);
            email_container.setError(null);

            password_container.setErrorEnabled(true);
            password_container.setError(getString(R.string.pass_is_short));
        } else {
            first_name_container.setErrorEnabled(false);
            first_name_container.setError(null);

            email_container.setErrorEnabled(false);
            email_container.setError(null);
            password_container.setErrorEnabled(false);
            password_container.setError(null);
            Common.closeKeyboard(edt_email, this);
            Register();
        }
    }

    private void Register() {

        dialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                alertDialog.show();
//            }
//        },5000);
//
//

        Log.e("Register", m_first_name + "");
        Log.e("Register", m_password + "");
        Log.e("Register", m_phone + "");
        Log.e("Register", m_email + "");
        Log.e("Register", m_courntid + "");


        Api.getService()
                .register(m_first_name, m_password, m_phone, m_email, m_courntid)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.e("response", response + "");
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            dialog.dismiss();

                            if (response.body().getSuccess() == 1) {
                                dialog.dismiss();

                                UserModel userModel = response.body();


                                userSingleTone = UserSingleTone.getInstance();

                                userSingleTone.setUserModel(userModel);

                                preferences.Create_Update_UserData(RegisterActivity.this, userModel);

                         //       Toast.makeText(RegisterActivity.this, "user : "+userModel.getUser_name(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else if (response.body().getSuccess() == 2) {
                                Toast.makeText(RegisterActivity.this, R.string.em_ph_exist, Toast.LENGTH_LONG).show();

                            } else if (response.body().getSuccess() == 3) {

                                Toast.makeText(RegisterActivity.this, R.string.something, Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        dialog.dismiss();
                        Log.e("errorRegister", t + "");

                    }
                });
    }
}