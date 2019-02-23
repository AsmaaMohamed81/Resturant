package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.DetailsFoodActivity;
import com.alatheer.menu.models.AmountModel;

import java.util.List;

public class DetailsAmountAdapter extends RecyclerView.Adapter<DetailsAmountAdapter.MyHolder> {

    Context context;

    private List<AmountModel> list;
    private int lastSelectedPosition = -1;

    public static double  amoumt;

    DetailsFoodActivity detailsFoodActivity;

    String unit_id_fkAdp;

    String nameamount;


    public String getUnit_id_fkAdp() {
        return unit_id_fkAdp;
    }

    public String getNameamount() {
        return nameamount;
    }

    public DetailsAmountAdapter(Context context, List<AmountModel> list, DetailsFoodActivity detailsFoodActivity) {
        this.context = context;
        this.list = list;
        this.detailsFoodActivity =detailsFoodActivity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(context).inflate(R.layout.itemradio,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        AmountModel amountModel=list.get(position);

        holder.Bind(amountModel);

        holder.radioButton.setChecked(lastSelectedPosition == position);



        String pri=holder.price.getText().toString();
        double pric=Double.parseDouble(pri);
        amoumt=pric;




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView price,name,coin;
        RadioButton radioButton;

        public MyHolder(View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.txt_price_item);
            name=itemView.findViewById(R.id.txt_name_item);
            coin=itemView.findViewById(R.id.txt_price_item2);
            radioButton=itemView.findViewById(R.id.radio_item);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

//                    Toast.makeText(DetailsAmountAdapter.this.context,
//                            "selected offer is " + price.getText(),
//                            Toast.LENGTH_LONG).show();

                    amoumt=Double.parseDouble(price.getText().toString());


                    detailsFoodActivity.amountPos(amoumt,lastSelectedPosition);



                    AmountModel amountModel2=list.get(lastSelectedPosition);

                    unit_id_fkAdp=amountModel2.getUnit_id_fk();
                    nameamount=amountModel2.getUnit_name();
                }
            });


        }

        public void Bind(AmountModel amountModel) {

            price.setText(amountModel.getPrice());
            name.setText(amountModel.getUnit_name());
            coin.setText(amountModel.getCurrency_symbol());

        }
    }
}
