package com.alatheer.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.DetailsAmountAdapter;
import com.alatheer.menu.adapters.DetailsIngredientAdapter;
import com.alatheer.menu.models.AmountModel;
import com.alatheer.menu.models.IngredientsModel;
import com.alatheer.menu.models.productitemModel;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFoodActivity extends AppCompatActivity {

    RecyclerView recyclerView1, recyclerView2;
    ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3;

    private DetailsAmountAdapter detailsAmountAdapter;
    private DetailsIngredientAdapter detailsIngredientAdapter;
    private List<AmountModel> listamount;
    private List<IngredientsModel> listingredient;

    private RelativeLayout relative1, relative2, relative3;

    private String name, details, img, id,restiD,rest_discount;
    private ImageView image_detail, img_cart;
    private TextView txt_name, txt_details, txt_price, txt_amount,tv_not_budget;

    private double allpriceingredient, allpriceamount, allprice, allll;
    private int amount = 1;
    private ImageButton plus, minus;
    private Button add_cart;

    private ArrayList<String> ingListall;
    private String unit_id_fk, nameAmount;
    private productitemModel productitemModel;
    private OrderItemsSingleTone orderItemsSingleTone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_food);

        getDataIntent();
        initView();
    }

    private void getDataIntent() {

        Intent intent = getIntent();
        if (intent != null) {

            name = intent.getStringExtra("product_name");
            details = intent.getStringExtra("product_details");
            img = intent.getStringExtra("product_img");
            id = intent.getStringExtra("product_id");
            restiD = intent.getStringExtra("restiD");
            rest_discount = intent.getStringExtra("rest_discount");





        }
    }

    private void initView() {

//        Toast.makeText(this, ""+restiD, Toast.LENGTH_SHORT).show();

        listamount = new ArrayList<>();
        listingredient = new ArrayList<>();
        ingListall = new ArrayList<>();

        orderItemsSingleTone = OrderItemsSingleTone.newInstance();

        img_cart = findViewById(R.id.img_cart);

        recyclerView1 = findViewById(R.id.recView1);
        recyclerView2 = findViewById(R.id.recView2);
        relative1 = findViewById(R.id.relativ1);
        relative2 = findViewById(R.id.relativ2);
        relative3 = findViewById(R.id.relativ3);

        ////////////////
        image_detail = findViewById(R.id.image_detail);
        txt_name = findViewById(R.id.txt_name);
        txt_details = findViewById(R.id.txt_details);
        txt_price = findViewById(R.id.txt_price);
        txt_amount = findViewById(R.id.txt_amount);

        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        add_cart = findViewById(R.id.add_cart);
        tv_not_budget=findViewById(R.id.tv_not_budget);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.press_anim);


        Picasso.with(this).load(Tags.image_url + img).into(image_detail);
        txt_name.setText(name);
        txt_details.setText(details);

        //////////////////

        expandableLayout1 = findViewById(R.id.expand_layout1);
        expandableLayout2 = findViewById(R.id.expand_layout2);
        expandableLayout3 = findViewById(R.id.expand_layout3);

        txt_amount.setText(Double.toString(amount));

        getAmounts();
        getIngredients();

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setHasFixedSize(true);
        detailsAmountAdapter = new DetailsAmountAdapter(this, listamount, this);
        recyclerView1.setAdapter(detailsAmountAdapter);


        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setHasFixedSize(true);
        detailsIngredientAdapter = new DetailsIngredientAdapter(this, listingredient, this);
        recyclerView2.setAdapter(detailsIngredientAdapter);


        expandableLayout1.expand(true);
        expandableLayout2.expand(true);
        expandableLayout3.expand(true);






        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout1.toggle(true);
            }
        });

        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout2.toggle(true);
            }
        });


        relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout3.toggle(true);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += 1;
                txt_amount.setText(Double.toString(amount));

                allll = amount * allprice;
                txt_price.setText(Double.toString(allll));

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount -= 1;

                if (amount <1)
                {
                    amount = 1;
                }


                txt_amount.setText(Double.toString(amount));
                allll = amount * allprice;
                txt_price.setText(Double.toString(allll));


            }
        });


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                for (int i = 0; i < detailsIngredientAdapter.getIngIDlist().size(); i++) {
//                    //Log.d("listing1", inglist.get(i));
//
//                    Log.d("listing2", detailsIngredientAdapter.getIngIDlist().get(i));
//
//                }

//
//                unit_id_fk = detailsAmountAdapter.getUnit_id_fkAdp();
//                nameAmount =detailsAmountAdapter.getNameamount();
//                Log.d("listing", unit_id_fk);

                if (detailsAmountAdapter.getUnit_id_fkAdp() == null) {

                    Toast.makeText(DetailsFoodActivity.this, "يجب اختيار اولا حجم الوجبه", Toast.LENGTH_SHORT).show();
                } else {

                    productitemModel = new productitemModel(id
                            , unit_id_fk
                            , detailsIngredientAdapter.getIngIDlist()
                            , txt_name.getText().toString()
                            , detailsAmountAdapter.getNameamount()
                            , detailsAmountAdapter.getUnit_id_fkAdp()
                            , img
                            , Double.toString(allprice)
                            , detailsIngredientAdapter.getIngIDlistname()
                            , Double.toString(amount));


                    orderItemsSingleTone.AddProduct(productitemModel);


                    if (orderItemsSingleTone.getOrderItemList().size() > 0) {
                        tv_not_budget.setText(String.valueOf(orderItemsSingleTone.getItemsCount()));
                    } else {
                        tv_not_budget.setText("0");

                    }

                }
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailsFoodActivity.this, Cart_activity.class);
//                Toast.makeText(DetailsFoodActivity.this, ""+restiD, Toast.LENGTH_SHORT).show();
                intent.putExtra("restiD",restiD);
                intent.putExtra("rest_discount",rest_discount);

                startActivity(intent);


            }
        });



    }


    private void getAmounts() {

        Api.getService()
                .getAmounts(id)
                .enqueue(new Callback<List<AmountModel>>() {
                    @Override
                    public void onResponse(Call<List<AmountModel>> call, Response<List<AmountModel>> response) {

                        if (response.isSuccessful()) {

                            if (response.body().size() > 0) {

                                listamount.addAll(response.body());
                                detailsAmountAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AmountModel>> call, Throwable t) {

                    }
                });
    }

    private void getIngredients() {

        Api.getService()
                .getIngredients(id)
                .enqueue(new Callback<List<IngredientsModel>>() {
                    @Override
                    public void onResponse(Call<List<IngredientsModel>> call, Response<List<IngredientsModel>> response) {
                        if (response.isSuccessful()) {

                            if (response.body().size() > 0) {

                                listingredient.addAll(response.body());
                                detailsIngredientAdapter.notifyDataSetChanged();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<IngredientsModel>> call, Throwable t) {

                    }
                });
    }

    public void ingredientPos(Double all, ArrayList<String> inglist) {


        allpriceingredient = all;

        allprice = allpriceingredient + allpriceamount;
        //txt_price.setText(Integer.toString(allprice));

        allll = amount * allprice;
        txt_price.setText(Double.toString(allll));

//        Toast.makeText(this, ""+ingredientsModel.getIng_name(), Toast.LENGTH_SHORT).show();


//        for (int i = 0; i < inglist.size(); i++) {
//            //Log.d("listing1", inglist.get(i));
//
//            ingListall.add(inglist.get(i));
//
//        }


    }

    public void amountPos(double amoumt, int lastSelectedPosition) {

        allpriceamount = amoumt;

        allprice = allpriceamount + allpriceingredient;
        txt_price.setText(Double.toString(allprice));

        allll = amount * allprice;
        txt_price.setText(Double.toString(allll));


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("numberorder",String.valueOf( orderItemsSingleTone.getItemsCount()));
        setResult(RESULT_OK, intent);
        finish();


    }
}
