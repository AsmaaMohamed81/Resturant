package com.alatheer.menu.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.common.Common;
import com.alatheer.menu.models.OrderToUploadModel;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.singletone.RestSingleTone;
import com.alatheer.menu.singletone.UserSingleTone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Personal_Info_Activity extends AppCompatActivity {

    LinearLayout continu;
    private OrderToUploadModel orderToUploadModel;
    private EditText edt_phone,edt_adress;
    private UserSingleTone userSingleTone;
    private OrderItemsSingleTone orderItemsSingleTone;
    private RestSingleTone restSingleTone;
    private Preferences preferences;
    private String restiD,phone,adress,total,products_cost;
    private ImageView check;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info_);

        getDataIntent();
        initView();

    }

    private void getDataIntent() {

        Intent intent=getIntent();

        if (intent!=null){

            restiD = intent.getStringExtra("restiD");
            products_cost = intent.getStringExtra("products_cost");
            total = intent.getStringExtra("total");


        }
    }


    private void initView() {

//        Toast.makeText(this, ""+restiD, Toast.LENGTH_SHORT).show();
        continu=findViewById(R.id.continu);
        edt_adress=findViewById(R.id.edt_adress);
        edt_phone=findViewById(R.id.edt_phone);
        check=findViewById(R.id.check);

        userSingleTone=UserSingleTone.getInstance();
        preferences=Preferences.getInstance();
        orderItemsSingleTone=OrderItemsSingleTone.newInstance();
        restSingleTone=RestSingleTone.getInstance();

        orderToUploadModel = new OrderToUploadModel();


         animation= AnimationUtils.loadAnimation(this,R.anim.press_anim);





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
                orderToUploadModel.setOrderItemList(orderItemsSingleTone.getOrderItemList());
                CheckData();

                Log.d("setUser_id",preferences.getUserModel(Personal_Info_Activity.this).getUser_id());
                Log.d("setUser_id",edt_adress.getText().toString());
                Log.d("setUser_id",edt_phone.getText().toString());
                Log.d("setUser_id","1222");
                Log.d("setUser_id",restiD);
                Log.d("setUser_id",orderItemsSingleTone.getOrderItemList().get(0).getPrice());

            }
        });
    }

    private void CheckData() {

        phone=edt_phone.getText().toString();
        adress=edt_adress.getText().toString();


        if (TextUtils.isEmpty(adress)){
            edt_adress.setError(getString(R.string.address_req));

        }else if (TextUtils.isEmpty(phone)){
            edt_adress.setError(null);
            edt_phone.setError(getString(R.string.phone_req));


        }else {
            edt_adress.setError(null);
            edt_phone.setError(null);

            UploadOrder();


        }


    }

    private void UploadOrder() {

        final ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService()
                .uploadOrder(orderToUploadModel)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                        if (response.isSuccessful())
                        {

                            dialog.dismiss();

                            CloseKeyBoard(Personal_Info_Activity.this,edt_adress);
                            CloseKeyBoard(Personal_Info_Activity.this,edt_phone);
                            check.setVisibility(View.VISIBLE);
                            check.setAnimation(animation);


                          //  Toast.makeText(Personal_Info_Activity.this, R.string.order_sent_successfully, Toast.LENGTH_SHORT).show();

//                            Log.d("data",response.body().getData().toString());


                        }else
                        {
                            dialog.dismiss();
                            if (response.code()==404)
                            {
                                Toast.makeText(Personal_Info_Activity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Log.e("Error",t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }



    public static void CloseKeyBoard(Context context, View view)
    {
        if (context!=null&&view!=null)
        {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (manager!=null){
                manager.hideSoftInputFromWindow(view.getWindowToken(),0);

            }

        }


    }
}
