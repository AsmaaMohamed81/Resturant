package com.alatheer.menu.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.alatheer.menu.models.City;
import com.alatheer.menu.models.Country;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.tags.Tags;
import com.google.gson.Gson;

/**
 * Created by elashry on 16/09/2018.
 */

public class Preferences {
    private static Preferences instance=null;

    private Preferences() {
    }

    public static  Preferences getInstance()
    {
        if (instance==null)
        {
            instance = new Preferences();
        }
        return instance;
    }


    public void Create_Update_UserData(Context context, UserModel userModel)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userData = gson.toJson(userModel);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_data",userData);
        editor.apply();
        Create_Update_Session(context, Tags.session_login);

    }

    public UserModel getUserModel(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = sharedPreferences.getString("user_data","");
        UserModel userModel = gson.fromJson(user_data,UserModel.class);
        return userModel;
    }



    public void ClearData(Context context)
    {
        Gson gson = new Gson();
        String user_data = gson.toJson("0");
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_data",user_data);
        editor.apply();
        Create_Update_Session(context,"LOG_OUT");
    }

    public void Create_Update_Session(Context context,String session)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state",session);
        editor.apply();
    }

    public String getSession(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        String session = sharedPreferences.getString("state",Tags.session_logout);
        return session;
    }

    public boolean isFirstTime(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("first_time",Context.MODE_PRIVATE);

        boolean first_time = sharedPreferences.getBoolean("first",true);
        return first_time;
    }

    public void UpdateFirstTime(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("first_time",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first",false);
        editor.apply();
    }

    public boolean isChooseLang(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("choose_lang",Context.MODE_PRIVATE);

        boolean selected = sharedPreferences.getBoolean("lang",false);
        return selected;
    }

    public void UpdateChooseLang(Context context, boolean selected)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("choose_lang",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("lang",selected);
        editor.apply();
    }

    public void createUpdateLocation(Context context ,String deliveredLocation)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loc",deliveredLocation);
        editor.apply();
    }

    public String getDeliveredLocation(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location",Context.MODE_PRIVATE);

        String deliveredLocation = sharedPreferences.getString("loc","");
        return deliveredLocation;

    }

    public void setCountry_NaionalityData(Context context , Country country_nationality)
    {
        Gson gson = new Gson();
        String data = gson.toJson(country_nationality);

        SharedPreferences preferences = context.getSharedPreferences("country_nationality",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data",data);
        editor.apply();

    }

    public Country getCountry_Nationality(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("country_nationality",Context.MODE_PRIVATE);
        String data = preferences.getString("data","");
        Gson gson = new Gson();
        return gson.fromJson(data,Country.class);
    }






    public void setCITYData(Context context , City cities)
    {
        Gson gson = new Gson();
        String data = gson.toJson(cities);

        SharedPreferences preferences = context.getSharedPreferences("CITIES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("city_data",data);
        editor.apply();

    }

    public City getCITYData(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("CITIES",Context.MODE_PRIVATE);
        String data = preferences.getString("city_data","");
        Gson gson = new Gson();
        return gson.fromJson(data, City.class);
    }


    public boolean iCITYData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CITIES_ask",Context.MODE_PRIVATE);

        boolean selected = sharedPreferences.getBoolean("city_datas",false);
        return selected;
    }

    public void UpdatCITYData(Context context, boolean selected)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CITIES_ask",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("city_datas",selected);
        editor.apply();
    }

}
