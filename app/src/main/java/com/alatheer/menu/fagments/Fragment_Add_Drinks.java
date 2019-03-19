package com.alatheer.menu.fagments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.tags.Tags;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by elashry on 08/10/2018.
 */

public class Fragment_Add_Drinks extends Fragment {
    private ArrayAdapter<String> spinner_discount_adapter,spinner_drink_type_adapter;
    private Spinner spinner_discount,spinner_drink_type;
    private String [] discount_array,drink_type_array;
    private TextView tv_price_text;
    private LinearLayout ll_after_dis,ll_before_discount;

    @Override
    public void onAttach(Context context) {

        Paper.init(context);
        String lang = Paper.book().read("language");

        if (Paper.book().read("language").equals("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }
        super.onAttach(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(context, lang)));
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks_add_item,container,false);
        initView(view);
        return view;
    }

    public static Fragment_Add_Drinks getInstance()
    {
        return new Fragment_Add_Drinks();
    }
    private void initView(View view) {
        spinner_discount = view.findViewById(R.id.spinner_discount);
        spinner_drink_type = view.findViewById(R.id.spinner_drink_type);

        tv_price_text = view.findViewById(R.id.tv_price_text);
        ll_after_dis = view.findViewById(R.id.ll_after_dis);
        ll_before_discount =  view.findViewById(R.id.ll_before_discount);




        ///////////////////////////////spinner discount////////////////////////////
        discount_array = getActivity().getResources().getStringArray(R.array.discount);
        spinner_discount_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_row,discount_array);
        spinner_discount.setAdapter(spinner_discount_adapter);

        spinner_discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0)
                {
                    ll_before_discount.setVisibility(View.INVISIBLE);
                    ll_after_dis.setVisibility(View.INVISIBLE);

                }
                if (position==1)
                {
                    ll_before_discount.setVisibility(View.VISIBLE);
                    tv_price_text.setText(R.string.price_befor_discount);
                    ll_after_dis.setVisibility(View.VISIBLE);


                }else if (position==2)
                {
                    ll_before_discount.setVisibility(View.VISIBLE);
                    tv_price_text.setText(getString(R.string.price));
                    ll_after_dis.setVisibility(View.INVISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////////////////////spinner drink type////////////////////////////
        drink_type_array = getActivity().getResources().getStringArray(R.array.drink_type);
        spinner_drink_type_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_row,drink_type_array);
        spinner_drink_type.setAdapter(spinner_drink_type_adapter);
        


    }


}
