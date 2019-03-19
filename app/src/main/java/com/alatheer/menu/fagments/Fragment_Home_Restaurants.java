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

import com.alatheer.menu.R;
import com.alatheer.menu.activities.RestaurantMainMenuActivity;
import com.alatheer.menu.adapters.RestaurantAdapter;
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

public class Fragment_Home_Restaurants extends Fragment {

    private RestaurantAdapter restaurantAdapter;
    private ArrayList<RestaurantsModel> list;
    private RecyclerView recyclerView;
    private Preferences preferences;
    String country_id_fk, city_id, qeuryy;
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

        if (getArguments() != null) {

            city_id = getArguments().getString("id_city");
            qeuryy = getArguments().getString("qeuryy");


            Log.e("id_cityiiiiii", city_id + "");
            Log.e("qeuryyFrgment", qeuryy + "");
//            Toast.makeText(getActivity(), ""+city_id, Toast.LENGTH_SHORT).show();


        }
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static Fragment_Home_Restaurants getInstance() {
        Fragment_Home_Restaurants fragment = new Fragment_Home_Restaurants();
        return fragment;
    }

    private void initView(View view) {
        Log.e("gtgthy", "rwer");

        preferences = Preferences.getInstance();
        restSingleTone = RestSingleTone.getInstance();

        recyclerView = view.findViewById(R.id.recycle_home);
        progressBar = view.findViewById(R.id.progBar);
        txt_no = view.findViewById(R.id.tv_no);
//        txt_no.setText(R.string.No_home);

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        list = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(list, getContext(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false); // fast
        recyclerView.setAdapter(restaurantAdapter);


        country_id_fk = preferences.getCountry_Nationality(getActivity()).getCountryIdPk();


        getdata();


    }

    private void getdata() {


        Api.getService()
                .getRestaurant(city_id)
                .enqueue(new Callback<Restaurantdata>() {
                    @Override
                    public void onResponse(Call<Restaurantdata> call, Response<Restaurantdata> response) {

                        if (response.isSuccessful()) {

                            progressBar.setVisibility(View.GONE);

                            if (response.body().getRestaurants().size() > 0) {
                                list.addAll(response.body().getRestaurants());
                                restaurantAdapter.notifyDataSetChanged();
                                txt_no.setVisibility(View.GONE);

                            } else {

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


    private void getDataSearch(String query) {


        Api.getService().getSearchRestaurant(query)
                .enqueue(new Callback<Restaurantdata>() {
                    @Override
                    public void onResponse(Call<Restaurantdata> call, Response<Restaurantdata> response) {
                        if (response.isSuccessful()) {

                            progressBar.setVisibility(View.GONE);

                            if (response.body().getRestaurants().size() > 0) {
                                list.addAll(response.body().getRestaurants());
                                restaurantAdapter.notifyDataSetChanged();
                                txt_no.setVisibility(View.GONE);

                            } else {

                                txt_no.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurantdata> call, Throwable t) {

                    }
                });
    }


    public void setItem(RestaurantsModel restaurantsModel) {

//        Toast.makeText(getActivity(), ""+restaurantsModel.getRest_id_pk(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), RestaurantMainMenuActivity.class);
        restSingleTone.setRestModel(restaurantsModel);

        intent.putExtra("restiD", restaurantsModel.getRest_id_pk());
        intent.putExtra("restname", restaurantsModel.getRest_name());

        intent.putExtra("time", restaurantsModel.getRest_work_time());
        intent.putExtra("type", "1");
        intent.putExtra("rest_discount", String.valueOf(Integer.parseInt(restaurantsModel.getAdmin_discount())
                + Integer.parseInt(restaurantsModel.getRest_discount())));


        Log.d("asmaa",
                String.valueOf(Integer.parseInt(restaurantsModel.getAdmin_discount())
                        + Integer.parseInt(restaurantsModel.getRest_discount())));
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
