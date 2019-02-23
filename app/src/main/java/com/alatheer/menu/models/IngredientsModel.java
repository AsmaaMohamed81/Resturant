package com.alatheer.menu.models;

import java.io.Serializable;

public class IngredientsModel implements Serializable {


    String id ;
    String main_product_id_fk ;
    String sub_product_id_fk ;
    String rest_id_fk ;
    String user_id_fk ;
    String ing_id_fk ;
    String ing_name ;
    String ing_price ;
    String ing_id_pk ;
    String currency_symbol ;

    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    public String getIng_id_fk() {
        return ing_id_fk;
    }

    public String getIng_name() {
        return ing_name;
    }

    public String getIng_price() {
        return ing_price;
    }

    public String getIng_id_pk() {
        return ing_id_pk;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }
}
