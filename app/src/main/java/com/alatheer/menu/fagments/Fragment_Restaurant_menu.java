package com.alatheer.menu.fagments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.activities.DetailsFoodActivity;
import com.alatheer.menu.adapters.RestaurantMenuAdapter;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.remote.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elashry on 28/08/2018.
 */

public class Fragment_Restaurant_menu extends Fragment {


    private static final String tag="tag";
    private RestaurantMenuAdapter adapter;
    private ArrayList<RestaurantMenuModel> list;
    private RecyclerView recyclerView;
    private String mainId,restiD,rest_discount;
    private TextView txt_no;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        if(getArguments()!=null){

            mainId=getArguments().getString("mainid");
            restiD=getArguments().getString("restiD");
            rest_discount=getArguments().getString("rest_discount");

            Log.e("mainID_menu",mainId);

        }

        initView(view);

        return view;
    }

    public static  Fragment_Restaurant_menu getInstance()
    {
        Fragment_Restaurant_menu fragment = new Fragment_Restaurant_menu();
//        Bundle bundle=new Bundle();
//      //  bundle.putSerializable(tag, (Serializable) list);
//        fragment.setArguments(bundle);
        return fragment;
    }
    private void initView(View view) {



        recyclerView = view.findViewById(R.id.recycle_home);

        list=new ArrayList<>();

        getSubProducts();
        adapter = new RestaurantMenuAdapter(getActivity(),list,this);

        progressBar=view.findViewById(R.id.progBar);
        txt_no=view.findViewById(R.id.tv_no);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorAccent), PorterDuff.Mode.SRC_IN);


        txt_no.setText(R.string.No_menu);


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false); // fast
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        enableSwipe(recyclerView);



//        Bundle bundle=getArguments();

//        if(bundle!=null){
//
//
//              list.addAll((Collection<? extends RestaurantMenuModel>) bundle.getSerializable(tag));
//            adapter.notifyDataSetChanged();
//
//        }





    }
    public void getSubProducts() {

        Api.getService()
                .getSubProducts(mainId)
                .enqueue(new Callback<List<RestaurantMenuModel>>() {
                    @Override
                    public void onResponse(Call<List<RestaurantMenuModel>> call, Response<List<RestaurantMenuModel>> response) {

                       if (response.isSuccessful()){
                           progressBar.setVisibility(View.GONE);
                           if (response.body().size()>0){
                               txt_no.setVisibility(View.GONE);

                               list.addAll(response.body());
                               adapter.notifyDataSetChanged();

                           }else {

                               txt_no.setVisibility(View.VISIBLE);

                           }
                       }


                    }

                    @Override
                    public void onFailure(Call<List<RestaurantMenuModel>> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    private void enableSwipe(RecyclerView recView){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemChanged(position);

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (viewHolder!=null)
                {
                    RestaurantMenuAdapter.MyHolder myHolder = (RestaurantMenuAdapter.MyHolder) viewHolder;
                    View foreground = myHolder.ll_foreground;
                    getDefaultUIUtil().onDraw(c,recyclerView,foreground,dX,dY,actionState,isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (viewHolder!=null)
                {
                    RestaurantMenuAdapter.MyHolder myHolder = (RestaurantMenuAdapter.MyHolder) viewHolder;
                    View foreground = myHolder.ll_foreground;
                    getDefaultUIUtil().onDrawOver(c,recyclerView,foreground,dX,dY,actionState,isCurrentlyActive);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

                if (viewHolder!=null)
                {
                    RestaurantMenuAdapter.MyHolder myHolder = (RestaurantMenuAdapter.MyHolder) viewHolder;
                    View foreground = myHolder.ll_foreground;
                    getDefaultUIUtil().onSelected(foreground);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder!=null)
                {
                    RestaurantMenuAdapter.MyHolder myHolder = (RestaurantMenuAdapter.MyHolder) viewHolder;
                    View foreground = myHolder.ll_foreground;
                    getDefaultUIUtil().clearView(foreground);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recView);
    }


/*
*   Paint p = new Paint();
                Paint p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
                p2.setColor(ContextCompat.getColor(getActivity(), R.color.white));
                p2.setTextSize(25f);
                Bitmap bitmap;
                bitmap  = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.white_cart);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    if(dX > 0){
                        p.setColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        c.drawText("ADD TO CART",background.width()/2,background.height()/2,p2);
                        c.drawBitmap(bitmap,40,background.height()/2,p);
                    } else {
                        p.setColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        c.drawText("ADD TO CART",background.width()/2,background.height()/2,p2);
                        c.drawBitmap(bitmap,40,background.height()/2,p);


                    }
                }
* */


    public void posAdapter(RestaurantMenuModel restaurantMenuModel) {


        Intent intent=new Intent(getActivity(),DetailsFoodActivity.class);
        getActivity().startActivityForResult(intent, 1);
        intent.putExtra("product_id",restaurantMenuModel.getId());
        intent.putExtra("product_name",restaurantMenuModel.getProduct_name());
        intent.putExtra("product_details",restaurantMenuModel.getDetails());
        intent.putExtra("product_img",restaurantMenuModel.getImg());
        intent.putExtra("restiD",restiD);
        intent.putExtra("rest_discount",rest_discount);


        startActivity(intent);

    }



}
