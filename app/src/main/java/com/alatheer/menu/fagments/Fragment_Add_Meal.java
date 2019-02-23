package com.alatheer.menu.fagments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alatheer.menu.R;

/**
 * Created by elashry on 08/10/2018.
 */

public class Fragment_Add_Meal extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_add_item,container,false);
        initView(view);
        return view;
    }

    public static Fragment_Add_Meal getInstance()
    {
        return new Fragment_Add_Meal();
    }
    private void initView(View view) {

    }
}
