package com.alatheer.menu.models;

import java.util.ArrayList;

public class Restaurantdata {

    private int success;
    private ArrayList<RestaurantsModel> restaurants;
    private ArrayList<RestaurantsModel> chifes;


    public ArrayList<RestaurantsModel> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<RestaurantsModel> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<RestaurantsModel> getChifes() {
        return chifes;
    }

    public void setChifes(ArrayList<RestaurantsModel> chifes) {
        this.chifes = chifes;
    }

    public int getSuccess() {
        return success;
    }
}
