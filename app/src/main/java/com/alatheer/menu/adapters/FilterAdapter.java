package com.alatheer.menu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_Filter;
import com.alatheer.menu.models.Filter_Model;

import java.util.List;

/**
 * Created by elashry on 29/08/2018.
 */

public class FilterAdapter  extends RecyclerView.Adapter<FilterAdapter.MyHolder>{

    private Context context;
    private List<Filter_Model> filter_modelList;
    private Fragment_Filter fragment_filter;
    public FilterAdapter(Context context, List<Filter_Model> filter_modelList,Fragment_Filter fragment_filter) {
        this.context = context;
        this.filter_modelList = filter_modelList;
        this.fragment_filter = fragment_filter;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Filter_Model filter_model = filter_modelList.get(position);
        holder.BindData(filter_model);
        holder.img_close.setOnClickListener(v -> fragment_filter.removeItem(holder.getAdapterPosition()));
        StartAnim(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return filter_modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView  tv_title;
        private ImageView img_close;
        public MyHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            img_close = itemView.findViewById(R.id.img_cancel);
        }

        public void BindData(Filter_Model filter_model)
        {
            tv_title.setText(filter_model.getName());
        }
    }


    private void StartAnim(View view)
    {
        ScaleAnimation animation = new ScaleAnimation(0f,1f,0f,1f,50f,50f);
        animation.setDuration(1500);
        view.clearAnimation();
        view.startAnimation(animation);

    }
}
