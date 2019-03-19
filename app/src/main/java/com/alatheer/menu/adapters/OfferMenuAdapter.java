package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_offer_menu;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferMenuAdapter extends RecyclerView.Adapter<OfferMenuAdapter.MyHolder>{
    Context context;

    private List<RestaurantMenuModel> list;

    Fragment_offer_menu fragment_offer_menu;

    public OfferMenuAdapter(Context context, List<RestaurantMenuModel> list,Fragment_offer_menu fragment) {

        this.context = context;
        this.list = list;
        this.fragment_offer_menu=fragment;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.restaurant_menu_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        RestaurantMenuModel restaurantMenuModel =list.get(position);
        holder.Bind(restaurantMenuModel);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantMenuModel Model1=list.get(holder.getAdapterPosition());

                fragment_offer_menu.posAdapter(Model1);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,discount,txt_time;
        public MyHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.txt_name);
            discount=itemView.findViewById(R.id.txt_price);
            txt_time=itemView.findViewById(R.id.txt_time);
        }

        public void Bind(RestaurantMenuModel restaurantMenuModel) {

            Picasso.with(context).load(Tags.image_url+restaurantMenuModel.getImg()).into(imageView);
            name.setText(restaurantMenuModel.getProduct_name());
            discount.setText(restaurantMenuModel.getDiscount()+ " %");

            if (restaurantMenuModel.getProduct_time()!=null){

                txt_time.setText("وقت تنفيذ الوجبة : " + restaurantMenuModel.getProduct_time());

            }



        }
    }
}
