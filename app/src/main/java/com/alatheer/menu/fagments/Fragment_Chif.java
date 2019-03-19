package com.alatheer.menu.fagments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.RestaurantMainMenuActivity;
import com.alatheer.menu.adapters.ChifAdapter;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.models.Restaurantdata;
import com.alatheer.menu.models.RestaurantsModel;
import com.alatheer.menu.preferences.Preferences;
import com.alatheer.menu.remote.Api;
import com.alatheer.menu.singletone.RestSingleTone;
import com.alatheer.menu.tags.Tags;

import java.util.ArrayList;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by elashry on 28/08/2018.
 */

public class Fragment_Chif extends Fragment {

    private ChifAdapter chifAdapter;
    private ArrayList<RestaurantsModel> list;
    private RecyclerView recyclerView;
    private Preferences preferences;
    String country_id_fk, city_id;
    private ProgressBar progressBar;
    private TextView txt_no;
    private RestSingleTone restSingleTone;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        Toast.makeText(getActivity(), "fragment_chif", Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {

            city_id = getArguments().getString("id_city");
            Log.e("id_cityiiiiii", city_id + "");
//            Toast.makeText(getActivity(), ""+city_id, Toast.LENGTH_SHORT).show();



        }
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static Fragment_Chif getInstance() {
        Fragment_Chif fragment = new Fragment_Chif();
        return fragment;
    }

    private void initView(View view) {
        Log.e("gtgthy", "rwer");

        preferences = Preferences.getInstance();
        restSingleTone=restSingleTone.getInstance();

        recyclerView = view.findViewById(R.id.recycle_home);

        list = new ArrayList<>();
        chifAdapter = new ChifAdapter(list, getContext(),this);
        progressBar=view.findViewById(R.id.progBar);
        txt_no=view.findViewById(R.id.tv_no);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorAccent), PorterDuff.Mode.SRC_IN);


        txt_no.setText(R.string.No_chif);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false); // fast
        recyclerView.setAdapter(chifAdapter);


        country_id_fk = preferences.getCountry_Nationality(getActivity()).getCountryIdPk();


        getdata();

    }

    private void getdata() {


        Api.getService()
                .getRestaurant(city_id)
                .enqueue(new Callback<Restaurantdata>() {
                    @Override
                    public void onResponse(Call<Restaurantdata> call, Response<Restaurantdata> response) {

                        if (response.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            if (response.body().getChifes().size()>0){


                                txt_no.setVisibility(View.GONE);
                                list.addAll(response.body().getChifes());


                                //  Log.e("list",list.get(0).getRest_name());
                                chifAdapter.notifyDataSetChanged();
                            }else {

                                txt_no.setVisibility(View.VISIBLE);

                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<Restaurantdata> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    public void setItem(RestaurantsModel chifmodel) {

        Intent intent=new Intent(getActivity(), RestaurantMainMenuActivity.class);

        restSingleTone.setRestModel(chifmodel);

        intent.putExtra("restiD",chifmodel.getRest_id_pk());
        intent.putExtra("type","2");
        intent.putExtra("rest_discount",String.valueOf(Integer.parseInt(chifmodel.getAdmin_discount())
                +Integer.parseInt(chifmodel.getRest_discount())));


        Log.d("asmaa",
                String.valueOf(Integer.parseInt(chifmodel.getAdmin_discount())
                        +Integer.parseInt(chifmodel.getRest_discount())));
        startActivity(intent);

    }

/*    private void getdataproduct() {


        Api.getService().getLastProducts(country_id_fk)
                .enqueue(new Callback<List<LastProductsModel>>() {
                    @Override
                    public void onResponse(Call<List<LastProductsModel>> call, Response<List<LastProductsModel>> response) {

                        list.addAll(response.body());
                        homeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<LastProductsModel>> call, Throwable t) {

                    }
                });
    }*/


}
