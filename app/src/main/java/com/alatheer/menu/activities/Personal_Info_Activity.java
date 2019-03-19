package com.alatheer.menu.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.OrderToUploadModel;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.singletone.RestSingleTone;
import com.alatheer.menu.singletone.UserSingleTone;
import com.alatheer.menu.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Personal_Info_Activity extends AppCompatActivity {

    LinearLayout continu;
    private OrderToUploadModel orderToUploadModel;
    private EditText edt_phone, edt_adress;
    private UserSingleTone userSingleTone;
    private OrderItemsSingleTone orderItemsSingleTone;
    private RestSingleTone restSingleTone;
    private Preferences preferences;
    private String restiD, phone, adress, total, products_cost;
    private ImageView check;
    private TextView txt_success;
    Animation animation;
    private Animation animation2;
    private LinearLayout ll_scooter, ll_yourSelf;
    private ImageView image_cash, image_yourSelf;
    private TextView tv_cash, tv_yourSelf;
    private Context context;
    private int deliver_by;

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
        setContentView(R.layout.activity_personal__info_);

        if (isFirstTime()) {
            ShowIntro("لادخال عنوانك", Html.fromHtml("<font color='red'>  يجب ادخال عنوانك بالتفصيل اسم الشارع والمنطقه لتوصيل طلبك بنجاح </p>"), R.id.edt_adress, 1);
        }
        getDataIntent();
        initView();

    }

    private void getDataIntent() {

        this.context = Personal_Info_Activity.this;
        Intent intent = getIntent();

        if (intent != null) {

            restiD = intent.getStringExtra("restiD");
            products_cost = intent.getStringExtra("products_cost");
            total = intent.getStringExtra("total");


        }
    }


    private void initView() {

//        Toast.makeText(this, ""+restiD, Toast.LENGTH_SHORT).show();
        continu = findViewById(R.id.continu);
        edt_adress = findViewById(R.id.edt_adress);
        edt_phone = findViewById(R.id.edt_phone);
        check = findViewById(R.id.check);
        txt_success = findViewById(R.id.txt_success);


        userSingleTone = UserSingleTone.getInstance();
        preferences = Preferences.getInstance();
        orderItemsSingleTone = OrderItemsSingleTone.newInstance();
        restSingleTone = RestSingleTone.getInstance();

        orderToUploadModel = new OrderToUploadModel();


        animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);

        animation2 = AnimationUtils.loadAnimation(this, R.anim.rotat);


        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continu.clearAnimation();
                continu.startAnimation(animation);



                orderToUploadModel.setUser_id(preferences.getUserModel(Personal_Info_Activity.this).getUser_id());
                orderToUploadModel.setUser_address(edt_adress.getText().toString());
                orderToUploadModel.setUser_phone(edt_phone.getText().toString());
                orderToUploadModel.setRest_iD(restiD);
                orderToUploadModel.setProducts_cost(products_cost);
                orderToUploadModel.setType(restSingleTone.getRestModel().getType());
                orderToUploadModel.setToken(FirebaseInstanceId.getInstance().getToken());
                orderToUploadModel.setDeliver_by(deliver_by);
                orderToUploadModel.setOrderItemList(orderItemsSingleTone.getOrderItemList());
                CheckData();

                Log.d("setUser_id", preferences.getUserModel(Personal_Info_Activity.this).getUser_id());
                Log.d("setUser_id", edt_adress.getText().toString());
                Log.d("setUser_id", edt_phone.getText().toString());
                Log.d("setUser_id", "1222");
                Log.d("setUser_id", restiD);
                Log.d("setUser_id", orderItemsSingleTone.getOrderItemList().get(0).getPrice());


            }
        });


        ////////////////////////////////////////
        ll_scooter = findViewById(R.id.ll_scooter);
        ll_yourSelf = findViewById(R.id.ll_yourSelf);

        image_cash = findViewById(R.id.image_cash);
        image_yourSelf = findViewById(R.id.image_yourSelf);


        tv_cash = findViewById(R.id.tv_cash);
        tv_yourSelf = findViewById(R.id.tv_yourSelf);


        ll_scooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deliver_by = 1;

                ll_scooter.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_payment));
                image_cash.setImageResource(R.drawable.scooter);
                tv_cash.setTextColor(ContextCompat.getColor(context, R.color.white));

                ll_yourSelf.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg));
                image_yourSelf.setImageResource(R.drawable.commerce2);
                tv_yourSelf.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            }
        });


        ll_yourSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deliver_by = 2;

                ll_yourSelf.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_payment));
                image_yourSelf.setImageResource(R.drawable.commerce);
                tv_yourSelf.setTextColor(ContextCompat.getColor(context, R.color.white));

                ll_scooter.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_bg));
                image_cash.setImageResource(R.drawable.scooter2);
                tv_cash.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            }
        });
        /////////////////////////////////////////

    }

    private void CheckData() {

        phone = edt_phone.getText().toString();
        adress = edt_adress.getText().toString();


        if (TextUtils.isEmpty(adress)) {
            edt_adress.setError(getString(R.string.address_req));

        } else if (TextUtils.isEmpty(phone)) {
            edt_adress.setError(null);
            edt_phone.setError(getString(R.string.phone_req));


        } else {
            edt_adress.setError(null);
            edt_phone.setError(null);


            if (deliver_by == 0) {

                Toast.makeText(context, "قم بختيار طريقه التوصيل اولا", Toast.LENGTH_SHORT).show();


            } else {
                UploadOrder();
            }


        }


    }

    private void UploadOrder() {

        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService()
                .uploadOrder(orderToUploadModel)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                        if (response.isSuccessful()) {

                            dialog.dismiss();

                            CloseKeyBoard(Personal_Info_Activity.this, edt_adress);
                            CloseKeyBoard(Personal_Info_Activity.this, edt_phone);
                            check.setVisibility(View.VISIBLE);
                            txt_success.setVisibility(View.VISIBLE);
                            check.setAnimation(animation2);
                            orderItemsSingleTone.ClearCart();


                            //  Toast.makeText(Personal_Info_Activity.this, R.string.order_sent_successfully, Toast.LENGTH_SHORT).show();

//                            Log.d("data",response.body().getData().toString());


                        } else {
                            dialog.dismiss();
                            if (response.code() == 404) {
                                Toast.makeText(Personal_Info_Activity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Log.e("Error", t.getMessage());

                        } catch (Exception e) {
                        }
                    }
                });
    }


    public static void CloseKeyBoard(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }

        }


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
                            ShowIntro("ادخال رقم هاتفك", Html.fromHtml("<font color='red'>يجب ادخال رقم تلفونك صحيح للمساعده علي توصيل طلبك بأمان </p>"), R.id.edt_phone, 6);
                        } else if (type == 6) {
                            ShowIntro("طريقه توصيل الطلبات", Html.fromHtml("<font color='red'> يجب اختار طريقه التوصيل  </p>"), R.id.ll_all_drive, 2);

                        } else if (type == 2) {
                            ShowIntro("زر ارسال طلبك", Html.fromHtml("<font color='red'> اضغط لارسال طلبك للمطعم</p>"), R.id.fl_continue, 4);
                        }
//                        else if (type == 3) {
//                            ShowIntro("Overlay", "Add your selected overlay effect on your video ", R.id.button_tool_overlay, 5);
//
//                        }
                        else if (type == 3) {
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
}
