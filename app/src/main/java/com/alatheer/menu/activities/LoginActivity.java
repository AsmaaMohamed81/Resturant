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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private ImageView img_back;
    private LinearLayout new_account;
    private EditText edt_Phone, edt_password;
    private TextInputLayout email_container, password_container;
    private TextView tv_forget_password;
    private Button login_btn;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private String m_phone, m_password;
    private Preferences preferences;
    private UserSingleTone userSingleTone;

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
        setContentView(R.layout.activity_login);


        initView();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        edt_Phone = findViewById(R.id.edtPhone);
        edt_password = findViewById(R.id.edtPassword);
        email_container = findViewById(R.id.email_container);
        password_container = findViewById(R.id.password_container);
        tv_forget_password = findViewById(R.id.tv_forgetPassword);
        login_btn = findViewById(R.id.login_btn);

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

        new_account = findViewById(R.id.new_account);
        new_account.setOnClickListener(v ->
                {
                    Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(i);

                }
        );

        login_btn.setOnClickListener(new View.OnClickListener() {
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
        m_phone = edt_Phone.getText().toString();
        m_password = edt_password.getText().toString();

        if (TextUtils.isEmpty(m_phone)) {
            email_container.setErrorEnabled(true);
            email_container.setError(getString(R.string.phone_req));
        }  else if (TextUtils.isEmpty(m_password)) {
            email_container.setErrorEnabled(false);
            email_container.setError(null);

            password_container.setErrorEnabled(true);
            password_container.setError(getString(R.string.pass_req));
        } else if (m_password.length() < 2) {
            email_container.setErrorEnabled(false);
            email_container.setError(null);

            password_container.setErrorEnabled(true);
            password_container.setError(getString(R.string.pass_is_short));
        } else {
            email_container.setErrorEnabled(false);
            email_container.setError(null);
            password_container.setErrorEnabled(false);
            password_container.setError(null);
            Common.closeKeyboard(edt_Phone, this);
            Login();
        }
    }

    private void Login() {
        dialog.show();

        Api.getService()
                .Login(m_phone, m_password)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {

                            dialog.dismiss();
                            if (response.body().getSuccess() == 1) {

                                UserModel userModel = response.body();

                                preferences = preferences.getInstance();
                                userSingleTone=UserSingleTone.getInstance();

                                userSingleTone.setUserModel(userModel);
                                preferences.Create_Update_UserData(LoginActivity.this, userModel);

                                Log.d("loggggg",userSingleTone.getUserModel().getUser_id());

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();


                            } else {

                                Toast.makeText(LoginActivity.this, R.string.chk_user_pas, Toast.LENGTH_LONG).show();


                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        dialog.dismiss();
                        Log.e("errorLogin", t + "");
                        Toast.makeText(LoginActivity.this, getString(R.string.something), Toast.LENGTH_LONG).show();


                    }
                });
    }
}
