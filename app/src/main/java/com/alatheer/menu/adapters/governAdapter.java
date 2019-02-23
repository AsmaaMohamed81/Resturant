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
import com.alatheer.menu.models.Govern;

import java.util.List;

public class governAdapter extends RecyclerView.Adapter<governAdapter.Holder>{

    Context context;

    List<Govern> governsList;

    ChooseLocationActivity chooseLocationActivity;

    public governAdapter(Context context, List<Govern> governsList) {
        this.context = context;
        this.governsList = governsList;
        chooseLocationActivity= (ChooseLocationActivity) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_txt,parent,false);
        return new governAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Govern govern=governsList.get(position);
        holder.Bind(govern);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                chooseLocationActivity.pos(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return governsList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
        }

        public void Bind(Govern govern) {
            textView.setText(govern.getGovernName());
        }
    }
    }
