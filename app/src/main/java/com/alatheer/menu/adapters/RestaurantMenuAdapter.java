package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_Restaurant_menu;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.tags.Tags;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.MyHolder> {

    Context context;

    List<RestaurantMenuModel> list;

    Fragment_Restaurant_menu fragment_restaurant_menu;

    public RestaurantMenuAdapter(Context context, List<RestaurantMenuModel> list,Fragment_Restaurant_menu fragment) {
        this.context = context;
        this.list = list;
        this.fragment_restaurant_menu=fragment;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_menu_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RestaurantMenuModel restaurantMenuModel=list.get(position);
        holder.Bind(restaurantMenuModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_restaurant_menu.posAdapter(restaurantMenuModel);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;
        private TextView name,price;
        public LinearLayout ll_foreground;

        public MyHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.txt_name);
            price=itemView.findViewById(R.id.txt_price);
        }

        public void Bind (RestaurantMenuModel restaurantMenuModel){

            Picasso.with(context).load(Tags.image_url+restaurantMenuModel.getImg()).into(imageView);
            name.setText(restaurantMenuModel.getProduct_name());
//            price.setText(restaurantMenuModel.getPrice());
        }
    }
}
