package com.alatheer.menu.fagments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.HomeActivity;
import com.alatheer.menu.adapters.FilterAdapter;
import com.alatheer.menu.models.Filter_Model;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by elashry on 28/08/2018.
 */

public class Fragment_Filter extends Fragment {

    private ExpandableLayout expand_layout;
    private Button apply_btn;
    private RecyclerView recViewFilter;
    private LinearLayoutManager manager;
    private FilterAdapter adapter;
    private List<Filter_Model> filter_modelList;
    private HomeActivity homeActivity;
    int i=0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter,container,false);
       /* Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.FONT_NAME,true);*/
        initView(view);
        return view;

    }

    public static Fragment_Filter getInstance()
    {
        Fragment_Filter fragment = new Fragment_Filter();
        return fragment;
    }
    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        filter_modelList = new ArrayList<>();
        expand_layout =view.findViewById(R.id.expand_layout);
        apply_btn = view.findViewById(R.id.apply_btn);
        recViewFilter = view.findViewById(R.id.recViewFilter);
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
        manager.setStackFromEnd(true);
        recViewFilter.setLayoutManager(manager);
        adapter = new FilterAdapter(getActivity(),filter_modelList,this);
        recViewFilter.setAdapter(adapter);


        apply_btn.setOnClickListener(v -> AddItem());




    }

    @Override
    public void onResume() {
        super.onResume();
        StartAnim();
    }

    public void removeItem(int pos)
    {
        filter_modelList.remove(pos);
        adapter.notifyItemRemoved(filter_modelList.size());
        manager.scrollToPosition(filter_modelList.size()-1);
        if (filter_modelList.size()==0)
        {
            expand_layout.collapse(true);
        }
        homeActivity.IncreaseNotification(filter_modelList.size());


    }
    private void AddItem() {
        if (!expand_layout.isExpanded())
        {
            expand_layout.setExpanded(true,true);

        }
        i++;
        filter_modelList.add(new Filter_Model("Emad"+i));
        adapter.notifyItemInserted(filter_modelList.size()-1);
        manager.scrollToPosition(filter_modelList.size()-1);

        homeActivity.IncreaseNotification(filter_modelList.size());
    }


    private void StartAnim()
    {
        apply_btn.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_up_from_bottom);
        apply_btn.clearAnimation();
        apply_btn.startAnimation(animation);
    }
}
