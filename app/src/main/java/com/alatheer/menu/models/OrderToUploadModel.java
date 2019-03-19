package com.alatheer.menu.models;

import java.io.Serializable;
import java.util.List;

public class OrderToUploadModel implements Serializable {

    private String user_id;
    private String user_phone;
    private String user_address;
    private String rest_iD;
    private String products_cost;
    private String totale;
    private String type;
    private String token;
    private int deliver_by;

    private double order_total_price;


    private List<productitemModel> orderItemList;

    public void setDeliver_by(int deliver_by) {
        this.deliver_by = deliver_by;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProducts_cost(String products_cost) {
        this.products_cost = products_cost;
    }


    public void setTotale(String totale) {
        this.totale = totale;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public void setOrder_total_price(double order_total_price) {
        this.order_total_price = order_total_price;
    }

    public void setOrderItemList(List<productitemModel> orderItemList) {
        this.orderItemList = orderItemList;
    }



    public void setRest_iD(String rest_iD) {
        this.rest_iD = rest_iD;
    }

}
