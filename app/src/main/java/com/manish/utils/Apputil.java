package com.manish.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manish.ApplicationLoader;

import java.io.IOException;
import java.io.InputStream;

public class Apputil {

    public static int parseInt(String string) {
        try {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return Math.round(Float.parseFloat(string));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean checkInternetConnection() {
        try {
            ConnectivityManager e = (ConnectivityManager) ApplicationLoader.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (e == null) {
                return false;
            } else {
                NetworkInfo netInfo = e.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnectedOrConnecting();
            }
        } catch (Exception var2) {
            return false;
        }
    }
    public static String readText() {
        String json = null;
        try {
            InputStream is = ApplicationLoader.getInstance().getAssets().open("homepageconfig.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
