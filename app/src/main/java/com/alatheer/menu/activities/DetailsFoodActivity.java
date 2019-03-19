package com.alatheer.menu.activities;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.DetailsAmountAdapter;
import com.alatheer.menu.adapters.DetailsIngredientAdapter;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.AmountModel;
import com.alatheer.menu.models.IngredientsModel;
import com.alatheer.menu.models.productitemModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.tags.Tags;
import com.alatheer.menu.util.CircleAnimationUtil;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

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

public class DetailsFoodActivity extends AppCompatActivity {

    RecyclerView recyclerView1, recyclerView2;
    ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3;

    private DetailsAmountAdapter detailsAmountAdapter;
    private DetailsIngredientAdapter detailsIngredientAdapter;
    private List<AmountModel> listamount;
    private List<IngredientsModel> listingredient;

    private RelativeLayout relative1, relative2, relative3;

    private String name, details, img, id, restiD, rest_discount,restname;
    private ImageView image_detail, img_cart;
    private TextView txt_name, txt_details, txt_price, txt_amount, tv_not_budget;

    private double allpriceingredient, allpriceamount, allprice, allll;
    private int amount = 1;
    private ImageButton plus, minus;
    private Button add_cart;

    private ArrayList<String> ingListall;
    private String unit_id_fk, nameAmount;
    //    private productitemModel productitemModel;
    private OrderItemsSingleTone orderItemsSingleTone;
    private Preferences preferences;
    private String TAG = "DetailsFoodActivity";

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
        setContentView(R.layout.activity_details_food);

        preferences = Preferences.getInstance();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ggg.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        if (isFirstTime()) {
            ShowIntro("سله الطلبات", Html.fromHtml("<font color='red'>سله الطلبات عند الضغط عليها تعرض لك عدد الطلبات والحساب بالكامل  لكن اولا قم بملأها</p>"), R.id.cartframe, 1);
        }

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
            restname = intent.getStringExtra("restname");
            Log.d("rest_name", "getDataIntent: "+restname);

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
        relative3=findViewById(R.id.relative3);

        ////////////////
        image_detail = findViewById(R.id.image_detail);
        txt_name = findViewById(R.id.txt_name);
        txt_details = findViewById(R.id.txt_details);
        txt_price = findViewById(R.id.txt_price);
        txt_amount = findViewById(R.id.txt_amount);

        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        add_cart = findViewById(R.id.add_cart);
        tv_not_budget = findViewById(R.id.tv_not_budget);

        if (orderItemsSingleTone.getOrderItemList().size() > 0) {
            tv_not_budget.setText(String.valueOf(orderItemsSingleTone.getItemsCount()));
        } else {
            tv_not_budget.setText("0");

        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);


        Picasso.with(this).load(Tags.image_url + img).into(image_detail);
        txt_name.setText(name);
        txt_details.setText(details);

        //////////////////

        expandableLayout1 = findViewById(R.id.expand_layout1);
        expandableLayout2 = findViewById(R.id.expand_layout2);
        expandableLayout3 = findViewById(R.id.expand_layout3);


        txt_amount.setText(Integer.toString((int) amount));

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

        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);


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


                txt_amount.setText(String.valueOf(amount));

                allll = amount * allprice;
                txt_price.setText(Double.toString(allll));

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount -= 1;

                if (amount < 1) {
                    amount = 1;
                }


                txt_amount.setText(Integer.toString((int) amount));
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

                add_cart.clearAnimation();
                add_cart.setAnimation(animation);


                if (detailsAmountAdapter.getUnit_id_fkAdp() == null) {

                    Toast.makeText(DetailsFoodActivity.this, "يجب اختيار اولا حجم الوجبه", Toast.LENGTH_SHORT).show();
                } else {

                    if (image_detail != null) {
                        makeFlyAnimation(image_detail);


                    }
                    productitemModel productitemodel = new productitemModel(id
                            , unit_id_fk
                            , detailsIngredientAdapter.getIngIDlist()
                            , txt_name.getText().toString()
                            , detailsAmountAdapter.getNameamount()
                            , detailsAmountAdapter.getUnit_id_fkAdp()
                            , img
                            , Double.toString(allprice)
                            , detailsIngredientAdapter.getIngIDlistname()
                            , Double.toString(amount));


                    orderItemsSingleTone.AddProduct(productitemodel);


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
                intent.putExtra("restiD", restiD);
                intent.putExtra("restname",restname);
                intent.putExtra("rest_discount", rest_discount);

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
        Log.d(TAG, "ingredientPos: allpriceingredient " + allpriceingredient);
        Log.d(TAG, "ingredientPos: allpriceamount" + allpriceamount);


        allll = amount * allprice;

        Log.d(TAG, "ingredientPos: amount " + amount);
        Log.d(TAG, "ingredientPos: allprice" + allprice);
        Log.d(TAG, "ingredientPos: allll " + allll);


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

    private void makeFlyAnimation(ImageView targetView) {

        FrameLayout destView = (FrameLayout) findViewById(R.id.cartframe);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(1000).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                CreateUserNotSignInAlertDialog(DetailsFoodActivity.this,getString(R.string.go_cart));
//                Toast.makeText(DetailsFoodActivity.this, "تم اضافه الاكله للسله بنجاح ...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();


    }

    private void customtoast() {


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        Toast toast = new Toast(DetailsFoodActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(android.view.Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);


        LayoutInflater ly = getLayoutInflater();

        View v = ly.inflate(R.layout.activity_toast__image_item, (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = v.findViewById(R.id.image);


        TextView txt1 = v.findViewById(R.id.text1);

        txt1.setText("لقد ارسلت طلبك للسله اذهب للسله لاكمال طلبك ");

        toast.setView(v);
        toast.show();
    }

    public void CreateUserNotSignInAlertDialog(Context context, String msg) {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog2, null);
        Button doneBtn = view.findViewById(R.id.doneBtn);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
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
                            ShowIntro("زر االزياده", Html.fromHtml("<font color='red'>اضغط لطلب اكثر من وجبه من هذا المصنف </p>"), R.id.plus, 6);
                        } else if (type == 6) {
                            ShowIntro("زر التقليل", Html.fromHtml("<font color='red'>اضغط لتقليل عدد الوجبات </p>"), R.id.minus, 2);
                        } else if (type == 2) {
                            ShowIntro("زر اضافه للسله ", Html.fromHtml("<font color='red'>اضغط لحفظ طلبك ف السله </p>"), R.id.add_cart, 4);
                        }
//                        else if (type == 4) {
//                            ShowIntro("Add Song", "Add your selected song on your video ", R.id.button_tool_music, 3);
//                        } else if (type == 3) {
//                            ShowIntro("Overlay", "Add your selected overlay effect on your video ", R.id.button_tool_overlay, 5);
//
//                        }
                        else if (type == 4) {
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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("numberorder", String.valueOf(orderItemsSingleTone.getItemsCount()));
        setResult(RESULT_OK, intent);
        finish();


    }


}
