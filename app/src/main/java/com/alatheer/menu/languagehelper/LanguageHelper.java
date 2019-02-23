package com.alatheer.menu.languagehelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by elashry on 27/08/2018.
 */

public class LanguageHelper {
    private static final String SELECTED_LANGUAGE = "SELECTED_LANGUAGE";


    public static Context onAttach(Context context)
    {
        String lang = getLanguage(context, Locale.getDefault().getLanguage());
        return setLocality(context,lang);
    }

    public static Context onAttach(Context context,String defaultLang)
    {
        String lang = getLanguage(context, defaultLang);

        return setLocality(context,lang);
    }

    public static Context setLocality(Context context, String lang) {
        SaveLang(context,lang);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            return updateResource(context,lang);
        }
        return updateLegacy(context,lang);
    }

    @SuppressWarnings("deprecation")
    private static Context updateLegacy(Context context, String lang) {

        Locale locale = new Locale(lang);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            config.setLayoutDirection(locale);
        }

        resources.updateConfiguration(config,resources.getDisplayMetrics());
        return context;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResource(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        return context.createConfigurationContext(config);
    }

    private static String getLanguage(Context context, String language) {
        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = spref.getString(SELECTED_LANGUAGE,language);
        return lang;
    }

    private static void SaveLang(Context context, String language) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SELECTED_LANGUAGE,language);
        editor.apply();
    }
}
