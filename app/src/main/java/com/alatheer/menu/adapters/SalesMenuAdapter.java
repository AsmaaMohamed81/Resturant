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
import com.alatheer.menu.fagments.Fragment_Sales_menu;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SalesMenuAdapter extends RecyclerView.Adapter<SalesMenuAdapter.Holder> {


    Context context;
    private List<RestaurantMenuModel> list;
    private Fragment_Sales_menu fragment;

    public SalesMenuAdapter(Context context, List<RestaurantMenuModel> list, Fragment_Sales_menu fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_menu_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        RestaurantMenuModel restaurantMenuModel = list.get(position);

        holder.Bind(restaurantMenuModel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment.posAdapter(restaurantMenuModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, amount,txt_time;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.txt_name);
            amount = itemView.findViewById(R.id.txt_price);
            txt_time=itemView.findViewById(R.id.txt_time);
        }

        public void Bind(RestaurantMenuModel restaurantMenuModel) {

            Picasso.with(context).load(Tags.image_url + restaurantMenuModel.getImg()).into(imageView);
            name.setText(restaurantMenuModel.getProduct_name());
            amount.setText(restaurantMenuModel.getMostSale()+" Num");

            if (restaurantMenuModel.getProduct_time()!=null){

                txt_time.setText("وقت تنفيذ الوجبة : " + restaurantMenuModel.getProduct_time());

            }
        }
    }
}
