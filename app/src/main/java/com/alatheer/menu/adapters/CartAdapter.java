package com.alatheer.menu.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.Cart_activity;
import com.alatheer.menu.models.productitemModel;
import com.alatheer.menu.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.paperdb.Paper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    private Context context;
    private List<productitemModel> productitemModelList;
    ScaleAnimation animation;
    Cart_activity cart_activity;

    public CartAdapter(Context context, List<productitemModel> productitemModelList ) {
        this.context = context;
        this.productitemModelList = productitemModelList;
        animation = new ScaleAnimation(.7f, 1.0f, .7f, 1.0f, 1.0f, 1.0f);
        animation.setDuration(300);

        cart_activity= (Cart_activity) context;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        productitemModel productitemModel = productitemModelList.get(position);
        holder.BindData(productitemModel);
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productitemModel productitemModel = productitemModelList.get(holder.getAdapterPosition());

                cart_activity.RemoveItem(productitemModel);
            }
        });
        holder.image_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productitemModel productitemModel = productitemModelList.get(holder.getAdapterPosition());

                holder.image_increment.clearAnimation();
                holder.image_decrement.clearAnimation();
                holder.image_increment.startAnimation(animation);
                Integer counter = Integer.parseInt(holder.tv_counter.getText().toString().trim()) + 1;
                holder.tv_counter.setText(String.valueOf(counter));


                double total = counter * Double.parseDouble(productitemModel.getPrice());
                holder.tv_price.setText(String.valueOf(total));
                cart_activity.Increment_Decrement(productitemModel,counter);
            }
        });
        holder.image_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productitemModel productitemModel = productitemModelList.get(holder.getAdapterPosition());

                holder.image_decrement.clearAnimation();
                holder.image_increment.clearAnimation();
                holder.image_decrement.startAnimation(animation);

                Integer counter = Integer.parseInt(holder.tv_counter.getText().toString().trim()) - 1;

                if (counter < 1) {
                    counter = 1;
                }
                double total = counter * Double.parseDouble(productitemModel.getPrice());
                holder.tv_price.setText(String.valueOf(total));

                holder.tv_counter.setText(String.valueOf(counter));
                cart_activity.Increment_Decrement(productitemModel,counter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productitemModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView image_delete, image, image_increment, image_decrement;
        private TextView tv_name, tv_price, tv_amount, tv_ing, tv_counter;

        public MyHolder(View itemView) {
            super(itemView);
            image_delete = itemView.findViewById(R.id.image_delete);
            image = itemView.findViewById(R.id.image);
            image_increment = itemView.findViewById(R.id.image_increment);
            image_decrement = itemView.findViewById(R.id.image_decrement);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_ing = itemView.findViewById(R.id.tv_ing);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_counter = itemView.findViewById(R.id.tv_counter);

        }

        public void BindData(productitemModel productitemModel) {
            Paper.init(context);

            tv_name.setText(productitemModel.getName());
            tv_amount.setText(productitemModel.getSize_name());



            StringBuilder builder = new StringBuilder();
            for (String details : productitemModel.getIngnameist()) {
                builder.append(details + " , ");
            }

            tv_ing.setText(builder.toString());



            String count =String.valueOf(productitemModel.getCount());
            Double doublcount=Double.parseDouble(count);
            int intcount=doublcount.intValue();
            tv_counter.setText(String.valueOf(intcount));

            double price = Double.parseDouble(productitemModel.getCount()) * Double.parseDouble(productitemModel.getPrice());
            tv_price.setText(String.valueOf(price));
            if (!TextUtils.isEmpty(productitemModel.getImage())) {
                Picasso.with(context).load(Uri.parse(Tags.image_url + productitemModel.getImage())).priority(Picasso.Priority.HIGH).fit().into(image);
            }
        }
    }
}

