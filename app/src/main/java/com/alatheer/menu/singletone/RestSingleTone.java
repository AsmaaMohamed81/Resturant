package com.alatheer.menu.singletone;

import com.alatheer.menu.models.RestaurantsModel;

public class RestSingleTone {

    private static RestSingleTone instance=null;
    private RestaurantsModel restaurantsModel=null;
    private RestSingleTone() {
    }

    public static synchronized RestSingleTone getInstance()
    {
        if (instance==null)
        {
            instance = new RestSingleTone();
        }
        return instance;
    }

    public void setRestModel(RestaurantsModel restaurantsModel)
    {
        this.restaurantsModel = restaurantsModel;
    }

    public RestaurantsModel getRestModel()
    {
        return this.restaurantsModel;
    }


    public void clearData()
    {
        this.restaurantsModel=null;
    }

}
