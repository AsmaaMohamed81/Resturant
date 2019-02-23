package com.alatheer.menu.models;

import java.io.Serializable;

public class AmountModel implements Serializable {


    String id;
    String main_product_id_fk;
    String sub_product_id_fk;
    String rest_id_fk;
    String user_id_fk;
    String unit_id_fk;
    String price;
    String currency_id_fk;
    String unit_name;
    String unit_id_pk;
    String currency_symbol;

    public String getId() {
        return id;
    }

    public String getMain_product_id_fk() {
        return main_product_id_fk;
    }

    public String getSub_product_id_fk() {
        return sub_product_id_fk;
    }

    public String getRest_id_fk() {
        return rest_id_fk;
    }

    public String getUser_id_fk() {
        return user_id_fk;
    }

    public String getUnit_id_fk() {
        return unit_id_fk;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency_id_fk() {
        return currency_id_fk;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public String getUnit_id_pk() {
        return unit_id_pk;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }
}
