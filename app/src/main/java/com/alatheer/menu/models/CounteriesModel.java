package com.alatheer.menu.models;

import java.io.Serializable;

public class CounteriesModel implements Serializable {

    private String country_id_pk;
    private String country_name;
    private String country_code;
    private String currency_id_fk;
    private String country_flag;


    public String getcountry_flag() {
        return country_flag;
    }

    public void setCountry_img_id(String country_img_id) {
        this.country_flag = country_img_id;
    }

    public String getCountry_id_pk() {
        return country_id_pk;
    }

    public void setCountry_id_pk(String country_id_pk) {
        this.country_id_pk = country_id_pk;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCurrency_id_fk() {
        return currency_id_fk;
    }

    public void setCurrency_id_fk(String currency_id_fk) {
        this.currency_id_fk = currency_id_fk;
    }
}
