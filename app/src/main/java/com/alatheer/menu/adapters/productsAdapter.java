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
import com.alatheer.menu.models.LastProductsModel;
import com.alatheer.menu.tags.Tags;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.Holder> {


    private List<LastProductsModel> list;
    private HomeActivity homeActivity;
    Context context;

    public productsAdapter(ArrayList<LastProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.homeActivity = (HomeActivity) context;
    }

    @NonNull
    @Override


    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itemhome,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        LastProductsModel lastProductsModel = list.get(position);
        holder.BindData(lastProductsModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastProductsModel lastProductsModel = list.get(holder.getAdapterPosition());

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
            txt_main=itemView.findViewById(R.id.txt_main);
            txt_details=itemView.findViewById(R.id.txt_details);

        }

        public void  BindData (LastProductsModel lastProductsModel){


            Picasso.with(context).load(Tags.image_url+lastProductsModel.getImg()).into(img);
            txt_product_name.setText(lastProductsModel.getProduct_name());
            txt_main.setText(lastProductsModel.getMain());
            txt_details.setText(lastProductsModel.getDetails());


        }


    }
}
