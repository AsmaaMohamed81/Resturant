package com.alatheer.menu.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.adapters.MyPagerAdapter;
import com.alatheer.menu.fagments.Fragment_Restaurant_menu;
import com.alatheer.menu.fagments.Fragment_Sales_menu;
import com.alatheer.menu.fagments.Fragment_offer_menu;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.models.productitemModel;
import com.alatheer.menu.singletone.OrderItemsSingleTone;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RestaurantMenuActivity extends AppCompatActivity {
    private ImageView img;
    private ImageView image_back, img_cart;
    private TextView name, tv_not_budget;
    private ViewPager pager;
    private TabLayout tab;
    private MyPagerAdapter myPagerAdapter;
  /*  private Spinner spinner;
    private ExpandableLayout expand_layout;*/

    private List<RestaurantMenuModel> list;
    private List<RestaurantMenuModel> listoffer;
    private List<RestaurantMenuModel> listsales;


    private String mainId, image, namee, numberorder, restiD, rest_discount, restname;

    private OrderItemsSingleTone orderItemsSingleTone;

    private productitemModel productitemModel;

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
        setContentView(R.layout.activity_main_menu_details);

        if (isFirstTime()) {
            ShowIntro("خصائص قائمه المطعم ", Html.fromHtml("<font color='red'> هذا المحتوي يقدم لك عروض المطعم من وجباته وعدد مرات البيع لهذه الوجبه حتي يسهل عليك التميز بين افضل الوجبات </p>"), R.id.tab, 1);
        }


        getDataIntent();
        initView();

    }

    private void getDataIntent() {


        Intent intent = getIntent();

        if (intent != null) {
            mainId = intent.getStringExtra("mainId");
            restiD = intent.getStringExtra("restiD");
            restname = intent.getStringExtra("restname");
            rest_discount = intent.getStringExtra("rest_discount");
            image = intent.getStringExtra("image");
            namee = intent.getStringExtra("name");


        }
    }


    private void initView() {

        list = new ArrayList<>();
        listoffer = new ArrayList<>();
        listsales = new ArrayList<>();

        orderItemsSingleTone = OrderItemsSingleTone.newInstance();


        //expand_layout = findViewById(R.id.expand_layout);
        img = findViewById(R.id.image_main);
        name = findViewById(R.id.name);
        img_cart = findViewById(R.id.img_cart);
        tv_not_budget = findViewById(R.id.tv_not_budget);
        image_back = findViewById(R.id.image_back);
        pager = findViewById(R.id.pager);
        tab = findViewById(R.id.tab);
        tab.setupWithViewPager(pager);

        Picasso.with(this).load(Tags.image_url + image).into(img);
        name.setText(namee);

        if (orderItemsSingleTone.getOrderItemList().size() > 0) {
            tv_not_budget.setText(String.valueOf(orderItemsSingleTone.getItemsCount()));
        } else {
            tv_not_budget.setText("0");

        }
//        Fragment_Restaurant_menu fragment1 = Fragment_Restaurant_menu.getInstance(list);
//        Fragment_Restaurant_menu fragment2 = Fragment_Restaurant_menu.getInstance(listoffer);
//        Fragment_Restaurant_menu fragment3 = Fragment_Restaurant_menu.getInstance(listsales);


        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getFragment(), gettitle());
//        myPagerAdapter.addFragment(fragment1);
//        myPagerAdapter.addFragment(fragment2);
//        myPagerAdapter.addFragment(fragment3);


//        myPagerAdapter.addTitle("القائمة");
//        myPagerAdapter.addTitle("الخصومات");
//        myPagerAdapter.addTitle("الاكثر مبيعا");

        pager.setAdapter(myPagerAdapter);
        // pager.beginFakeDrag();


        // spinner=findViewById(R.id.spinner);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                supportFinishAfterTransition();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.details));


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RestaurantMenuActivity.this, Cart_activity.class);
                startActivity(intent);


            }
        });


    }


    private List<Fragment> getFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("mainid", mainId);
        bundle.putString("restiD", restiD);
        bundle.putString("restname", restname);


        bundle.putString("rest_discount", rest_discount);

        Fragment_Restaurant_menu fragment1 = Fragment_Restaurant_menu.getInstance();
        fragment1.setArguments(bundle);
        Fragment_offer_menu fragment2 = Fragment_offer_menu.getInstance();
        fragment2.setArguments(bundle);

        Fragment_Sales_menu fragment3 = Fragment_Sales_menu.getInstance();
        fragment3.setArguments(bundle);


        List<Fragment> list1 = new ArrayList<>();
        list1.add(fragment1);
        list1.add(fragment2);
        list1.add(fragment3);


        return list1;
    }

    private List<String> gettitle() {

        List<String> list1 = new ArrayList<>();
        list1.add(getString(R.string.Menu));
        list1.add(getString(R.string.offer));
        list1.add(getString(R.string.Sales));


        return list1;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                numberorder = data.getStringExtra("numberorder");

                tv_not_budget.setText(numberorder);

            }
        }
    }

    //
//    private List<RestaurantMenuModel> getListoffer() {
//        List<RestaurantMenuModel> list1=new ArrayList<>();
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer6","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer6","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"offer5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"offer6","2999"));
//
//        return list1;
//    }
//
//    private List<RestaurantMenuModel> getListmenu() {
//        List<RestaurantMenuModel> list1=new ArrayList<>();
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu6","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu6","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu1","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu2","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu3","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu4","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food1,"menu5","2999"));
//        list1.add(new RestaurantMenuModel(R.drawable.food2,"menu6","2999"));
//
//
//
//        return list1;
//    }
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
        // exitActivityTransition.exit(this);

//        Toast.makeText(this, "menu"+numberorder, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("numberorder", numberorder);
        setResult(RESULT_OK, intent);
        finish();

    }

}
