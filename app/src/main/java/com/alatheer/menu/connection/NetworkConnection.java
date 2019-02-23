package com.alatheer.menu.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by elashry on 27/09/2018.
 */

public class NetworkConnection {
    private static  NetworkConnection instance=null;
    private NetworkConnection() {
    }

    public static synchronized NetworkConnection  getInstance()
    {
        if (instance==null)
        {
            instance = new NetworkConnection();

        }

        return instance;
    }

    public static boolean getConnection(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!=null)
        {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isConnected()&&networkInfo.isAvailable())
            {
                return true;
            }
        }

        return false;
    }
}
