package com.alatheer.menu.singletone;

import com.alatheer.menu.models.Country;

/**
 * Created by Emad on 2018-04-04.
 */

public class Countries {
    private static Countries instance;
    private Country country;
    private Countries.onCompleteListener completeListener;
    private Countries(){}

    public static synchronized Countries getInstance()
    {
        if (instance==null)
        {
            instance = new Countries();
        }
        return instance;
    }

    public interface onCompleteListener
    {
        void OnDataSuccess(Country country);
    }

    public void setCountrydata(Country country)
    {
        this.country = country;
        //completeListener.OnDataSuccess(userModel);


    }
    public Country getCountry()
    {
        return this.country;
    }

    public void getData(Countries.onCompleteListener listener)
    {
        completeListener = listener;
        completeListener.OnDataSuccess(country);
    }



}
