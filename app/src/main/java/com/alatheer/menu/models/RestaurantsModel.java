package com.alatheer.menu.models;

public class RestaurantsModel {


    private String rest_id_pk;
    private String user_id_fk;
    private String rest_name;
    private String rest_logo;
    private String rest_address;
    private String rest_phone;
    private String rest_hotline;
    private String country_id_fk;
    private String govern_id_fk;
    private String city_id_fk;
    private String currency_id_fk;
    private String rest_discount;
    private String admin_discount;
    private String date;
    private String date_s;
    private String rest_approved;
    private String readed;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRest_id_pk() {
        return rest_id_pk;
    }

    public void setRest_id_pk(String rest_id_pk) {
        this.rest_id_pk = rest_id_pk;
    }

    public String getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(String user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_logo() {
        return rest_logo;
    }

    public void setRest_logo(String rest_logo) {
        this.rest_logo = rest_logo;
    }

    public String getRest_address() {
        return rest_address;
    }

    public void setRest_address(String rest_address) {
        this.rest_address = rest_address;
    }

    public String getRest_phone() {
        return rest_phone;
    }

    public void setRest_phone(String rest_phone) {
        this.rest_phone = rest_phone;
    }

    public String getRest_hotline() {
        return rest_hotline;
    }

    public void setRest_hotline(String rest_hotline) {
        this.rest_hotline = rest_hotline;
    }

    public String getCountry_id_fk() {
        return country_id_fk;
    }

    public void setCountry_id_fk(String country_id_fk) {
        this.country_id_fk = country_id_fk;
    }

    public String getGovern_id_fk() {
        return govern_id_fk;
    }

    public void setGovern_id_fk(String govern_id_fk) {
        this.govern_id_fk = govern_id_fk;
    }

    public String getCity_id_fk() {
        return city_id_fk;
    }

    public void setCity_id_fk(String city_id_fk) {
        this.city_id_fk = city_id_fk;
    }

    public String getCurrency_id_fk() {
        return currency_id_fk;
    }

    public void setCurrency_id_fk(String currency_id_fk) {
        this.currency_id_fk = currency_id_fk;
    }

    public String getRest_discount() {
        return rest_discount;
    }

    public void setRest_discount(String rest_discount) {
        this.rest_discount = rest_discount;
    }

    public String getAdmin_discount() {
        return admin_discount;
    }

    public void setAdmin_discount(String admin_discount) {
        this.admin_discount = admin_discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_s() {
        return date_s;
    }

    public void setDate_s(String date_s) {
        this.date_s = date_s;
    }

    public String getRest_approved() {
        return rest_approved;
    }

    public void setRest_approved(String rest_approved) {
        this.rest_approved = rest_approved;
    }

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }
}
