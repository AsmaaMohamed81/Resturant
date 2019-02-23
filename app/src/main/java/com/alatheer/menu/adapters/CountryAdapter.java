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
import com.alatheer.menu.activities.CountryLanguageActivity;
import com.alatheer.menu.models.Country;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyHolder> {

    Context context;

    List<Country> CountryList;

    CountryLanguageActivity countryLanguageActivity;

    public CountryAdapter(Context context, List<Country> CountryList) {
        this.context = context;
        this.CountryList = CountryList;
        countryLanguageActivity= (CountryLanguageActivity) context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Country Country=CountryList.get(position);
        holder.Bind(Country);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                countryLanguageActivity.pos(pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return CountryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private ImageView image_flag;
        private TextView tv_name;

        public MyHolder(View itemView) {
            super(itemView);

            image_flag=itemView.findViewById(R.id.image_flag);
            tv_name=itemView.findViewById(R.id.tv_name);

        }

        public void Bind (Country Country){
            Picasso.with(context).load(Tags.image_url+Country.getCountryFlag()).into(image_flag);
            tv_name.setText(Country.getCountryName());
        }
    }
}
