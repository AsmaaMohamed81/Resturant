package com.alatheer.menu.fagments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.DetailsFoodActivity;
import com.alatheer.menu.adapters.OfferMenuAdapter;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Fragment_offer_menu extends Fragment {
    private List<RestaurantMenuModel> listoffer;

    private RecyclerView recyclerView;

    private OfferMenuAdapter offerMenuAdapter;
    private String mainId,restiD,rest_discount,restname;

    private ProgressBar progressBar;
    private TextView tv_no;


    @Override
    public void onAttach(Context context) {

        Paper.init(context);
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
        super.onAttach(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(context, lang)));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);


        if(getArguments()!=null){

            mainId=getArguments().getString("mainid");
            restiD=getArguments().getString("restiD");
            restname=getArguments().getString("restname");
            rest_discount=getArguments().getString("rest_discount");

            Log.e("mainID_offer",mainId);


        }

        initView(view);

        return view;
    }

    public static Fragment_offer_menu getInstance() {
        Fragment_offer_menu fragment_offer_menu=new Fragment_offer_menu();
        return fragment_offer_menu;

    }

    private void initView(View view) {


        listoffer=new ArrayList<>();

        getOfferDetails();

        recyclerView=view.findViewById(R.id.recycle_home);
        progressBar=view.findViewById(R.id.progBar);
        tv_no=view.findViewById(R.id.tv_no);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        tv_no.setText(R.string.No_offer);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        offerMenuAdapter=new OfferMenuAdapter(getActivity(),listoffer,this);
        recyclerView.setAdapter(offerMenuAdapter);




    }


    public void getOfferDetails() {

        Api.getService()
                .getOfferDetails(mainId)
                .enqueue(new Callback<List<RestaurantMenuModel>>() {
                    @Override
                    public void onResponse(Call<List<RestaurantMenuModel>> call, Response<List<RestaurantMenuModel>> response) {

                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);

                            if (response.body().size()>0){

                                tv_no.setVisibility(View.GONE);
                                listoffer.addAll(response.body());
                                offerMenuAdapter.notifyDataSetChanged();

                            }else
                            {

                                tv_no.setVisibility(View.VISIBLE);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<List<RestaurantMenuModel>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    public void posAdapter(RestaurantMenuModel restaurantMenuModel) {


        Intent intent=new Intent(getActivity(),DetailsFoodActivity.class);
        intent.putExtra("product_id",restaurantMenuModel.getProduct_id_fk());
        intent.putExtra("product_name",restaurantMenuModel.getProduct_name());
        intent.putExtra("product_details",restaurantMenuModel.getDetails());
        intent.putExtra("product_img",restaurantMenuModel.getImg());
        intent.putExtra("restiD",restiD);
        intent.putExtra("restname", restname);
        intent.putExtra("rest_discount",rest_discount);


        startActivity(intent);
    }
}
