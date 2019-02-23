package com.alatheer.menu.models;

import java.io.Serializable;

public class RestaurantMenuModel implements Serializable {

    private String id;
    private String product_name;
    private String from_id;
    private String img;
    private String details;
    private String rest_id_fk;
    private String type;
    private String user_id_fk;
    private String country_id_fk;
    private String date_s;
    private String offer_id_fk;
    private String product_id_fk;
    private String discount;
    private String from_date;
    private String to_date;
    private String mostSale;
    private String offer_name;


    public RestaurantMenuModel(String product_name, String img, String discount) {
        this.product_name = product_name;
        this.img = img;
        this.discount = discount;
    }

    public String getMostSale() {
        return mostSale;
    }

    public void setMostSale(String mostSale) {
        this.mostSale = mostSale;
    }

    public String getOffer_id_fk() {
        return offer_id_fk;
    }

    public void setOffer_id_fk(String offer_id_fk) {
        this.offer_id_fk = offer_id_fk;
    }

    public String getProduct_id_fk() {
        return product_id_fk;
    }

    public void setProduct_id_fk(String product_id_fk) {
        this.product_id_fk = product_id_fk;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRest_id_fk() {
        return rest_id_fk;
    }

    public void setRest_id_fk(String rest_id_fk) {
        this.rest_id_fk = rest_id_fk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(String user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getCountry_id_fk() {
        return country_id_fk;
    }

    public void setCountry_id_fk(String country_id_fk) {
        this.country_id_fk = country_id_fk;
    }

    public String getDate_s() {
        return date_s;
    }

    public void setDate_s(String date_s) {
        this.date_s = date_s;
    }
}
