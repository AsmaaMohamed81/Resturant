package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.DetailsFoodActivity;
import com.alatheer.menu.models.IngredientsModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsIngredientAdapter extends RecyclerView.Adapter<DetailsIngredientAdapter.MyHolder> {

    Context context;

    private List<IngredientsModel> list;

    public  double all;

    DetailsFoodActivity detailsFoodActivity;

    private   ArrayList<String> ingIDlist;
    private   ArrayList<String> ingIDlistname;
    private String TAG="adpter";


    public ArrayList<String> getIngIDlist() {
        return ingIDlist;
    }

    public ArrayList<String> getIngIDlistname() {
        return ingIDlistname;
    }

    public DetailsIngredientAdapter(Context context, List<IngredientsModel> list, DetailsFoodActivity detailsFoodActivity) {
        this.context = context;
        this.list = list;
        this.detailsFoodActivity = detailsFoodActivity;
        ingIDlist = new ArrayList<>();
        ingIDlistname = new ArrayList<>();

    }

    @NonNull
    @Override
    public DetailsIngredientAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemcheck, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        IngredientsModel ingredientsModel = list.get(position);

        holder.Bind(ingredientsModel);


        String pri = holder.price.getText().toString();

        String iD = holder.id_ing.getText().toString();

        String name =holder.name.getText().toString();

        double pric =  Double.parseDouble(pri);

        Log.d(TAG, "onBindViewHolder: pric "+pric);


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    ingredientsModel.setSelected(true);

                    Log.d(TAG, "onCheckedChanged: all"+all);
                    all += pric;


                    ingIDlist.add(iD);
                    ingIDlistname.add(name);

                    Log.d(TAG, "onCheckedChanged: "+all);


                } else {

                    all -= pric;
                    ingIDlist.remove(iD);
                    ingIDlistname.remove(name);


                }

                detailsFoodActivity.ingredientPos(all, ingIDlist);


//                Toast.makeText(DetailsIngredientAdapter.this.context,
//                        "all  =  " + all,
//                        Toast.LENGTH_LONG).show();





            }
        });

        holder.checkBox.setChecked(ingredientsModel.isSelected());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView price, name, coin, id_ing;
        CheckBox checkBox;

        public MyHolder(View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.txt_price_item);
            name = itemView.findViewById(R.id.txt_name_item);
            coin = itemView.findViewById(R.id.txt_price_item2);
            checkBox = itemView.findViewById(R.id.check_item);
            id_ing = itemView.findViewById(R.id.id_item);

        }

        public void Bind(IngredientsModel ingredientsModel) {

            price.setText(ingredientsModel.getIng_price());
            name.setText(ingredientsModel.getIng_name());
            coin.setText(ingredientsModel.getCurrency_symbol());
            id_ing.setText(ingredientsModel.getIng_id_fk());
        }
    }

}
