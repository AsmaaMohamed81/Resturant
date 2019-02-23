package com.alatheer.menu.singletone;



import com.alatheer.menu.models.productitemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsSingleTone {

    private static OrderItemsSingleTone instance = null;
    private List<productitemModel> orderItemList = new ArrayList<>();

    private OrderItemsSingleTone(){}

    public static synchronized OrderItemsSingleTone newInstance()
    {
        if (instance == null)
        {
            instance = new OrderItemsSingleTone();
        }
        return instance;
    }

    public void AddProduct(productitemModel orderItem)
    {
        int pos = getItemPosition(orderItem);
        if (pos !=-1)
        {
            productitemModel item = orderItemList.get(pos);
//            int product_new_quantity = orderItem.getProduct_quantity() + item.getProduct_quantity();
//            item.setProduct_quantity(product_new_quantity);
//            double product_total_price = item.getProduct_price() * product_new_quantity;
//            item.setProduct_total_price(product_total_price);
            orderItemList.set(pos,item);

        }else
        {

            orderItemList.add(orderItem);

        }
    }

    public void UpdateProduct(productitemModel orderItem)
    {
        int pos = getItemPosition(orderItem);

//        AlternativeProductItem alternativeProductItem = orderItem.getAlternativeProductItem();
//        alternativeProductItem.setProduct_quantity(orderItem.getProduct_quantity());
//        orderItem.setAlternativeProductItem(alternativeProductItem);
        orderItemList.set(pos,orderItem);
    }

    public void RemoveProduct (productitemModel orderItem)
    {
        int pos = getItemPosition(orderItem);
        orderItemList.remove(pos);
        if (orderItemList.size()==0)
        {
            ClearCart();
        }
    }

    private int getItemPosition(productitemModel orderItem)
    {
        int pos = -1;

        for (int i = 0 ; i< orderItemList.size() ; i++)
        {

            productitemModel item = orderItemList.get(i);

            if (item.getId()==orderItem.getId())
            {

                pos = i;
                break;
            }
        }

        return pos;
    }


    public List<productitemModel> getOrderItemList() {
        return orderItemList;
    }

    public int getItemsCount ()
    {
        return orderItemList.size();
    }

    public void ClearCart()
    {
        orderItemList.clear();
    }

}

