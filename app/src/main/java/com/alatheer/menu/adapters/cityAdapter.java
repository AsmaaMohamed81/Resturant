package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.ChooseLocationActivity;
import com.alatheer.menu.models.City;

import java.util.List;

public class cityAdapter extends RecyclerView.Adapter<cityAdapter.Holder>{

    Context context;

    List<City> cityList;

    ChooseLocationActivity chooseLocationActivity;

    public cityAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
        chooseLocationActivity= (ChooseLocationActivity) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_txt,parent,false);
        return new cityAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        City city=cityList.get(position);
        holder.Bind(city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                chooseLocationActivity.poscity(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
        }

        public void Bind(City city) {
            textView.setText(city.getCityName());
        }
    }
    }
