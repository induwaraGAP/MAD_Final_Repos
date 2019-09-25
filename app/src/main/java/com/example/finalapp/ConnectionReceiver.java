package com.example.finalapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ConnectionReceiver extends BroadcastReceiver  {


    private static boolean networkStatus;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();

        networkStatus = networkInfo != null && networkInfo.isConnected();


    }



    public static boolean getNetConnectionStatus() {
        return networkStatus;
    }

    public static boolean getIntConnectionStatus() {


        try {
            return new AsynchronousTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

}
