package com.alatheer.menu.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.tags.Tags;
import com.lamudi.phonefield.PhoneInputLayout;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Contact_usActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_name,edt_email,edt_phone,edt_message;
    private ImageView image_back,image_send;
    private TextInputLayout edt_name_container,edt_email_container,edt_phone_container,edt_message_container;
    private PhoneInputLayout edtPhone_check;
    private LinearLayout load_container,contact_container;
    private ProgressBar progBar;
    private Animation animation;

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
        setContentView(R.layout.activity_contact_us);

        initView();

    }

    private void initView() {

        image_back = findViewById(R.id.image_back);
        image_send = findViewById(R.id.image_send);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_message = findViewById(R.id.edt_message);
        edt_name_container = findViewById(R.id.edt_name_container);
        edt_email_container = findViewById(R.id.edt_email_container);
        edt_phone_container = findViewById(R.id.edt_phone_container);
        edt_message_container = findViewById(R.id.edt_message_container);
        edtPhone_check = findViewById(R.id.edtPhone_check);
        load_container =findViewById(R.id.load_container);
        contact_container = findViewById(R.id.contact_container);
        progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        String language = Paper.book().read("language");
        if (language.equals("ar"))
        {
            image_back.setRotation(180);
        }else if (language.equals("en"))
        {
            image_back.setRotation(360);

        }

        image_send.setOnClickListener(this);
        image_back.setOnClickListener(this);
        animation = AnimationUtils.loadAnimation(this,R.anim.press_anim);

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                load_container.setVisibility(View.GONE);
                contact_container.setVisibility(View.VISIBLE);
                image_send.setVisibility(View.VISIBLE);
            }
        },6000);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.image_back:
                image_back.clearAnimation();
                image_back.startAnimation(animation);
                break;
            case R.id.image_send:
                CheckData();
                break;
        }
    }

    private void CheckData() {
        String m_name = edt_name.getText().toString();
        String m_email= edt_email.getText().toString();
        String m_phone= edt_phone.getText().toString();
        String m_msg = edt_message.getText().toString();
        edtPhone_check.setPhoneNumber(m_phone);
        if (!TextUtils.isEmpty(m_name)
                &&!TextUtils.isEmpty(m_email)&& Patterns.EMAIL_ADDRESS.matcher(m_email).matches()
                &&!TextUtils.isEmpty(m_phone)&&edtPhone_check.isValid()
                &&!TextUtils.isEmpty(m_msg))
        {

            edt_name_container.setErrorEnabled(false);
            edt_email_container.setErrorEnabled(false);
            edt_phone_container.setErrorEnabled(false);
            edt_message_container.setErrorEnabled(false);

            edt_name_container.setError(null);
            edt_email_container.setError(null);
            edt_phone_container.setError(null);
            edt_message_container.setError(null);

            sendContact(m_name,m_email,m_phone,m_msg);


        }
        if (TextUtils.isEmpty(m_name)
                &&TextUtils.isEmpty(m_email)
                &&TextUtils.isEmpty(m_phone)
                &&TextUtils.isEmpty(m_msg))
        {

            edt_name_container.setErrorEnabled(true);
            edt_email_container.setErrorEnabled(true);
            edt_phone_container.setErrorEnabled(true);
            edt_message_container.setErrorEnabled(true);

            edt_name_container.setError(getString(R.string.name_req));
            edt_email_container.setError(getString(R.string.email_req));
            edt_phone_container.setError(getString(R.string.phone_req));
            edt_message_container.setError(getString(R.string.msg_req));




        } if (TextUtils.isEmpty(m_name))
        {
            edt_name_container.setErrorEnabled(true);
            edt_name_container.setError(getString(R.string.name_req));


        }else
            {
                edt_name_container.setErrorEnabled(false);
                edt_name_container.setError(null);
            }
         if (TextUtils.isEmpty(m_email))
        {

            edt_email_container.setErrorEnabled(true);
            edt_email_container.setError(getString(R.string.email_req));


        }else if (!Patterns.EMAIL_ADDRESS.matcher(m_email).matches())
        {

            edt_email_container.setErrorEnabled(true);
            edt_email_container.setError(getString(R.string.email_inv));

        }else
        {
            edt_email_container.setErrorEnabled(false);
            edt_email_container.setError(null);
        }

         if (TextUtils.isEmpty(m_phone))
        {


            edt_phone_container.setErrorEnabled(true);
            edt_phone_container.setError(getString(R.string.phone_req));

        }else if (!edtPhone_check.isValid())
         {


             edt_phone_container.setErrorEnabled(true);
             edt_phone_container.setError(getString(R.string.phone_inv));
         }else
            {
                edt_phone_container.setErrorEnabled(false);
                edt_phone_container.setError(null);
            }

        if (TextUtils.isEmpty(m_msg))
        {


            edt_message_container.setErrorEnabled(true);
            edt_message_container.setError(getString(R.string.msg_req));
        }else
            {
                edt_message_container.setErrorEnabled(false);
                edt_message_container.setError(null);
            }
    }


    public void sendContact(String name,String email,String phone,String msg)
    {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

    }






}
