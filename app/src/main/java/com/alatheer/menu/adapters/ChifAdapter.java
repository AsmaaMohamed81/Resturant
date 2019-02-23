package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.HomeActivity;
import com.alatheer.menu.fagments.Fragment_Chif;
import com.alatheer.menu.models.RestaurantsModel;
import com.alatheer.menu.models.chif;
import com.alatheer.menu.tags.Tags;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChifAdapter extends RecyclerView.Adapter<ChifAdapter.Holder> {


    private List<RestaurantsModel> list;
    private Fragment_Chif fragment_chif;
    Context context;

    public ChifAdapter(ArrayList<RestaurantsModel> list, Context context,Fragment_Chif fragment_chif) {
        this.list = list;
        this.context = context;
        this.fragment_chif=fragment_chif;
    }

    @NonNull
    @Override


    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemhome, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        RestaurantsModel chif = list.get(position);
        holder.BindData(chif);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                RestaurantsModel chifmodel=list.get(pos);
                fragment_chif.setItem(chifmodel);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        TextView txt_product_name, txt_main, txt_details;

        public Holder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_details = itemView.findViewById(R.id.txt_details);

        }

        public void BindData(RestaurantsModel chif) {


            Picasso.with(context).load(Tags.image_url + chif.getRest_logo()).into(img);
            txt_product_name.setText(chif.getRest_name());
            txt_details.setText(chif.getRest_address());


        }


    }
}
