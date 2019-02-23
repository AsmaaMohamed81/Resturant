package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_Home_Restaurants;
import com.alatheer.menu.models.RestaurantsModel;
import com.alatheer.menu.tags.Tags;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.Holder> {


    private List<RestaurantsModel> list;
    private Fragment_Home_Restaurants fragment;
    Context context;

    public RestaurantAdapter(ArrayList<RestaurantsModel> list, Context context,Fragment_Home_Restaurants fragment) {
        this.list = list;
        this.context = context;
        this.fragment=(Fragment_Home_Restaurants)fragment;
    }

    @NonNull
    @Override


    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itemhome,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        RestaurantsModel restaurants = list.get(position);
        holder.BindData(restaurants);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                RestaurantsModel restaurantsModel=list.get(pos);
                fragment.setItem(restaurantsModel);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        RoundedImageView img;
        TextView txt_product_name,txt_main,txt_details;

        public Holder(View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            txt_product_name=itemView.findViewById(R.id.txt_product_name);
            txt_details=itemView.findViewById(R.id.txt_details);

        }

        public void  BindData (RestaurantsModel restaurants){


            Picasso.with(context).load(Tags.image_url+restaurants.getRest_logo()).into(img);
            txt_product_name.setText(restaurants.getRest_name());
            txt_details.setText(restaurants.getRest_address());


        }


    }
}
