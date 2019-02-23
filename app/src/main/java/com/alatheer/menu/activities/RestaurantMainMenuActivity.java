package com.alatheer.menu.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.RestaurantMainMenuAdapter;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.OrderItemsSingleTone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantMainMenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<RestaurantMenuModel> list;
    RestaurantMainMenuAdapter adapter;

    private TextView tv_no;
    private ProgressBar progBar;
    private String restiD ,rest_discount;
    private int numberorder;
    private OrderItemsSingleTone orderItemsSingleTone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main_menu);

        getdataIntent();
        list=new ArrayList<>();
        getResmenu();
        recyclerView=findViewById(R.id.recyc);
        progBar=findViewById(R.id.progBar);
        tv_no=findViewById(R.id.tv_no);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        tv_no.setText(R.string.No_menu);



        orderItemsSingleTone=OrderItemsSingleTone.newInstance();
         adapter = new RestaurantMainMenuAdapter(this,list);


         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);


    }


    public void getResmenu() {

        Api.getService()
                .getRestaurantmenu(restiD)
                .enqueue(new Callback<List<RestaurantMenuModel>>() {
                    @Override
                    public void onResponse(Call<List<RestaurantMenuModel>> call, Response<List<RestaurantMenuModel>> response) {

                        if (response.isSuccessful()){

                            progBar.setVisibility(View.GONE);


                            if (response.body().size()>0){

                                list.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                tv_no.setVisibility(View.GONE);

                            }else {

                                tv_no.setVisibility(View.VISIBLE);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<List<RestaurantMenuModel>> call, Throwable t) {

                        progBar.setVisibility(View.GONE);

                    }
                });
    }

    public void posAdapter(int pos) {
        RestaurantMenuModel restaurantMenuModel=list.get(pos);
        Intent intent = new Intent(this,RestaurantMenuActivity.class);
        intent.putExtra("mainId",restaurantMenuModel.getId());
        intent.putExtra("restiD",restiD);
        intent.putExtra("rest_discount",rest_discount);
        intent.putExtra("image",restaurantMenuModel.getImg());
        intent.putExtra("name",restaurantMenuModel.getProduct_name());


        startActivity(intent);


    }

    private void getdataIntent() {

        Intent intent=getIntent();

        if (intent!=null){

            restiD=intent.getStringExtra("restiD");
            rest_discount=intent.getStringExtra("rest_discount");

            Log.d("asmmaaa", "getdataIntent: "+rest_discount);



        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                OrderItemsSingleTone orderItemsSingleTone=OrderItemsSingleTone.newInstance();

                numberorder=orderItemsSingleTone.getItemsCount();
//                Toast.makeText(this, "Mani : "+String.valueOf(orderItemsSingleTone.getItemsCount()), Toast.LENGTH_SHORT).show();

            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        // exitActivityTransition.exit(this);
//
////        Intent intent = new Intent();
////        intent.putExtra("numberorder",numberorder);
////        setResult(RESULT_OK, intent);
////        finish();
//
////        if (Integer.parseInt(numberorder)>0)
////        {
////            CreateCartAlertDialog();
////        }
//
//    }

    public void CreateCartAlertDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(this).inflate(R.layout.dailog_remove_cart,null);
        FrameLayout fl_delete = view.findViewById(R.id.fl_delete);
        FrameLayout fl_cancel = view.findViewById(R.id.fl_cancel);

        TextView tv_not_dialog = view.findViewById(R.id.tv_not_dialog);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_not_dialog.setText(String.valueOf(orderItemsSingleTone.getItemsCount()));
        tv_content.setText(getString(R.string.the_cart_contains)+" "+orderItemsSingleTone.getItemsCount()+" "+getString(R.string.item)+" "+getString(R.string.delete_items));

        fl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderItemsSingleTone.ClearCart();
                dialog.dismiss();
            }
        });
        fl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations=R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_lang_bg);
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onBackPressed() {

//        Toast.makeText(this, "Main"+orderItemsSingleTone.getItemsCount(), Toast.LENGTH_SHORT).show();


            if (orderItemsSingleTone.getItemsCount() > 0) {
                CreateCartAlertDialog();
            } else {

                Intent intent = new Intent();
                intent.putExtra("numberorder", numberorder);
                setResult(RESULT_OK, intent);
                finish();

            }

    }
}
