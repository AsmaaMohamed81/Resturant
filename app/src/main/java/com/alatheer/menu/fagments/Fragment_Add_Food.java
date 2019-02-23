package com.alatheer.menu.fagments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alatheer.menu.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elashry on 08/10/2018.
 */

public class Fragment_Add_Food extends Fragment {
    private ImageView image,image_upload,image_add_amount,image_arrow;
    private EditText edt_name,edt_price,edt_after_discount,edt_note,edt_extra_item_name,edt_extra_item_price,edt_amount;
    private TextView tv_price_text;
    private Button btn_add,btn_extra_add;
    private ExpandableLayout expand_layout_extra,expand_layout_amount,expand_layout_extra_rec;
    private RecyclerView recView_extra,recView_amount;
    private Spinner spinner_department,spinner_food_type,spinner_amount,spinner_discount;
    private LinearLayout ll_extra,ll_after_dis,ll_before_discount;
    private String [] discount_array,amount_array;
    private ArrayAdapter<String> spinner_discount_adapter,spinner_department_adapter,spinner_amount_adapter,spinner_food_type_adapter;
    private String discount_value="-1",department_value="-1",food_type_value="-1";
    private List<String> spinner_department_list,spinner_food_type_List;
    private String read_perm = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final int read_req=125;
    private final int img_req =126;
    private Bitmap bitmap=null;
    private Uri uri = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_add_item,container,false);
        initView(view);
        return view;
    }

    public static Fragment_Add_Food getInstance()
    {
        return new Fragment_Add_Food();
    }
    private void initView(View view) {

        image = view.findViewById(R.id.image);
        image_upload = view.findViewById(R.id.image_upload);
        image_add_amount = view.findViewById(R.id.image_add_amount);
        image_arrow = view.findViewById(R.id.image_arrow);
        edt_name = view.findViewById(R.id.edt_name);
        tv_price_text = view.findViewById(R.id.tv_price_text);
        edt_price = view.findViewById(R.id.edt_price);
        edt_after_discount = view.findViewById(R.id.edt_after_discount);
        edt_note = view.findViewById(R.id.edt_note);
        edt_extra_item_name = view.findViewById(R.id.edt_extra_item_name);
        edt_extra_item_price = view.findViewById(R.id.edt_extra_item_price);
        edt_amount = view.findViewById(R.id.edt_amount);
        btn_add = view.findViewById(R.id.btn_add);
        btn_extra_add = view.findViewById(R.id.btn_extra_add);
        expand_layout_extra = view.findViewById(R.id.expand_layout_extra);
        expand_layout_amount = view.findViewById(R.id.expand_layout_amount);
        expand_layout_extra_rec = view.findViewById(R.id.expand_layout_extra_rec);
        recView_extra = view.findViewById(R.id.recView_extra);
        recView_amount = view.findViewById(R.id.recView_amount);
        spinner_department = view.findViewById(R.id.spinner_department);
        spinner_food_type = view.findViewById(R.id.spinner_food_type);
        spinner_amount = view.findViewById(R.id.spinner_amount);
        spinner_discount = view.findViewById(R.id.spinner_discount);
        ll_extra = view.findViewById(R.id.ll_extra);
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
                    discount_value=String.valueOf(position);
                    tv_price_text.setText(R.string.price_befor_discount);
                    ll_after_dis.setVisibility(View.VISIBLE);


                }else if (position==2)
                    {
                        ll_before_discount.setVisibility(View.VISIBLE);
                        discount_value="-1";
                        tv_price_text.setText(getString(R.string.price));
                        ll_after_dis.setVisibility(View.INVISIBLE);


                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //////////////////////////////spinner department////////////////////////////////
        spinner_department_list = new ArrayList<>();
        spinner_department_list.add("إختر");
        spinner_department_list.add("لحوم");
        spinner_department_list.add("خضروات");
        spinner_department_list.add("سلطات");

        spinner_department_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_row,spinner_department_list);
        spinner_department.setAdapter(spinner_department_adapter);

        //////////////////////////////spinner amount////////////////////////////////////
        amount_array = getActivity().getResources().getStringArray(R.array.amount);
        spinner_amount_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_row,amount_array);
        spinner_amount.setAdapter(spinner_amount_adapter);
        spinner_amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    image_add_amount.setVisibility(View.INVISIBLE);
                    edt_amount.setVisibility(View.INVISIBLE);

                }if (position==1)
                {
                    image_add_amount.setVisibility(View.INVISIBLE);
                    edt_amount.setVisibility(View.INVISIBLE);


                }else if (position==2)
                {
                    image_add_amount.setVisibility(View.VISIBLE);
                    edt_amount.setHint(getString(R.string.pieces));
                    edt_amount.setVisibility(View.VISIBLE);


                }else if (position==3)
                {
                    image_add_amount.setVisibility(View.VISIBLE);
                    edt_amount.setHint(getString(R.string.boxes));
                    edt_amount.setVisibility(View.VISIBLE);


                }else if (position==4)
                {
                    image_add_amount.setVisibility(View.VISIBLE);
                    edt_amount.setVisibility(View.INVISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /////////////////////////////spinner food type//////////////////////////////////
        spinner_food_type_List = new ArrayList<>();
        spinner_food_type_List.add("إختر");
        spinner_food_type_List.add("مصري");
        spinner_food_type_List.add("سوري");
        spinner_food_type_List.add("كويتي");
        spinner_food_type_List.add("اماراتي");
        spinner_food_type_List.add("سعودي");

        spinner_food_type_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_row,spinner_food_type_List);
        spinner_food_type.setAdapter(spinner_food_type_adapter);



        /////////////////////////////////////////////////////////////////////////////////
        ll_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expand_layout_extra.toggle(true);
                if (expand_layout_extra.getState()==ExpandableLayout.State.EXPANDING)
                {
                    image_arrow.animate().rotation(180f).start();
                }else if (expand_layout_extra.getState()==ExpandableLayout.State.COLLAPSING)
                {
                    image_arrow.animate().rotation(0f).start();

                }
            }
        });
        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckData();
            }
        });
    }

    private void CheckData() {


        
    }


    private void CheckPermission()
    {
        if (ContextCompat.checkSelfPermission(getActivity(),read_perm)!= PackageManager.PERMISSION_GRANTED)
        {
            SelectImage();
        }else
            {
                String [] per = {read_perm};
                ActivityCompat.requestPermissions(getActivity(),per,read_req);
            }
    }
    private void SelectImage() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent.createChooser(intent,"Select image"),img_req);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==read_req)
        {
            if (grantResults.length>0)
            {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    SelectImage();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==img_req && resultCode == Activity.RESULT_OK && data!=null)
        {
            uri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                image.setImageBitmap(bitmap);
                Log.e("img","12525252");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
