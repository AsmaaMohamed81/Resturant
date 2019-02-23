package com.alatheer.menu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.CartAdapter;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.models.productitemModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.singletone.UserSingleTone;

import java.util.List;

public class Cart_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<productitemModel> list;
    FrameLayout fl_continue;
    LinearLayout ll_empty_cart;
    CardView card_bill;
    private List<productitemModel> productitemModelList;
    private String restiD,rest_discount;



    OrderItemsSingleTone orderItemsSingleTone;
    private UserSingleTone userSingleTone;
    private UserModel userModel;
    private Preferences preferences;
    private TextView tv_products_cost,discount,tv_total;
    private double total_order_cost_after_tax=0.0;
    private int tax;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getDataIntent();
        initView();

    }

    private void getDataIntent() {

        Intent intent=getIntent();

        if (intent!=null){

            restiD = intent.getStringExtra("restiD");
            rest_discount = intent.getStringExtra("rest_discount");


        }
    }

    private void initView() {
//        Toast.makeText(this, ""+restiD, Toast.LENGTH_SHORT).show();

        tax=Integer.parseInt(rest_discount);
        userSingleTone =  UserSingleTone.getInstance();
        preferences=Preferences.getInstance();

        userModel = preferences.getUserModel(this);



        tv_total=findViewById(R.id.tv_total);
        discount=findViewById(R.id.tv_discount);
        card_bill=findViewById(R.id.card_bill);
        tv_products_cost=findViewById(R.id.tv_products_cost);

        orderItemsSingleTone=OrderItemsSingleTone.newInstance();
        //list=new ArrayList<>();
        recyclerView=findViewById(R.id.recyc_cart);
        ll_empty_cart=findViewById(R.id.ll_empty_cart);

//        this.list=orderItemsSingleTone.getOrderItemList();
        this.productitemModelList=orderItemsSingleTone.getOrderItemList();


        cartAdapter = new CartAdapter(this,productitemModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartAdapter);

        fl_continue=findViewById(R.id.fl_continue);




        Animation animation= AnimationUtils.loadAnimation(this,R.anim.press_anim);


        fl_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl_continue.clearAnimation();
                fl_continue.startAnimation(animation);

                if (userModel!=null){
//                    Toast.makeText(Cart_activity.this, ""
//                            +orderItemsSingleTone.getOrderItemList().get(0).getPrice(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Cart_activity.this,Personal_Info_Activity.class);
                    intent.putExtra("restiD",restiD);
                    intent.putExtra("products_cost",tv_products_cost.getText().toString());
                    intent.putExtra("total",tv_total.getText().toString());

                    startActivity(intent);

                }else {

                   CreateUserNotSignInAlertDialog(Cart_activity.this, getString(R.string.si_su));


                }


            }
        });
        UpdateUI(orderItemsSingleTone.getOrderItemList());
        UpdateTaxUI(tax);



    }

    public void Increment_Decrement(productitemModel productitemModel, double counter) {

        double total_price =  Double.parseDouble(productitemModel.getPrice())*counter;
        productitemModel.setCount(String.valueOf(counter));

//        Toast.makeText(this, ""+productitemModel.getPrice(), Toast.LENGTH_SHORT).show();

        orderItemsSingleTone.UpdateProduct(productitemModel);

        updateProductCost(getTotalPrice(productitemModelList));
        UpdateTaxUI(tax);





    }

    private void updateProductCost(double totalPrice) {

        tv_products_cost.setText(String.valueOf(totalPrice));


    }

    public void RemoveItem(productitemModel productitemModel) {

        orderItemsSingleTone.RemoveProduct(productitemModel);


        cartAdapter.notifyDataSetChanged();


        if (productitemModelList.size()>0){
            UpdateTaxUI(tax);

            ll_empty_cart.setVisibility(View.GONE);
            updateCard_ContinueUI(true);

            updateProductCost(getTotalPrice(productitemModelList));



        }else {

            ll_empty_cart.setVisibility(View.VISIBLE);
            updateCard_ContinueUI(false);


        }

    }

    private void UpdateUI(List<productitemModel> orderItemList)
    {


        if (orderItemList!=null&&orderItemList.size()>0)
        {

//            getTax();
            double total = getTotalPrice(orderItemList);
            updateProductCost(total);
//            UpdateAdapter(this.orderItemList);
            updateCard_ContinueUI(true);

        }else
        {

            updateCard_ContinueUI(false);


        }


    }

    private void updateCard_ContinueUI(boolean visible)
    {
        if (visible)
        {
            card_bill.setVisibility(View.VISIBLE);
            fl_continue.setVisibility(View.VISIBLE);
            ll_empty_cart.setVisibility(View.GONE);
//            activity.updateUIToolBarFragmentCart(1);

        }else
        {
            ll_empty_cart.setVisibility(View.VISIBLE);
            card_bill.setVisibility(View.GONE);
            fl_continue.setVisibility(View.GONE);
//            activity.updateUIToolBarFragmentCart(0);
        }
    }


    public static void CreateUserNotSignInAlertDialog(Context context, String msg)
    {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog,null);
        Button doneBtn = view.findViewById(R.id.doneBtn);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations=R.style.custom_dialog_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }

    private double getTotalPrice(List<productitemModel> orderItemList)
    {
        double total = 0.0;
        for (productitemModel productitemModel :productitemModelList)
        {
            total+=  Double.parseDouble( productitemModel.getPrice())*Double.parseDouble(productitemModel.getCount());
        }

        return total;
    }

    private void UpdateTaxUI(int tax)
    {
        discount.setText(String.valueOf(tax)+" %");

        double total_items_prices = getTotalPrice(productitemModelList);
        double price_after_tax = total_items_prices*((double) tax/100.0);

        total_order_cost_after_tax = getTotalPrice(productitemModelList) - price_after_tax;

        tv_total.setText(String.valueOf(total_order_cost_after_tax));

    }
}
