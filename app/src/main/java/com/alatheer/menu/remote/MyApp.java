package com.alatheer.menu.remote;

import android.app.Application;
import android.content.Context;

import com.alatheer.menu.languagehelper.LanguageHelper;

import java.util.Locale;

import io.paperdb.Paper;


/**
 * Created by elashry on 25/08/2018.
 */

public class MyApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base,"ar"));

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        String lang = Paper.book().read("language",Locale.getDefault().getLanguage());
        if (lang==null)
        {
            Paper.book().write("language","ar");
        }else
        {
            Paper.book().write("language","en");

        }

    }
}