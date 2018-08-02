package com.manish.network;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private Context mContext;

    private VolleySingleton(Context context) {
        this.mContext = context;
        this.requestQueue = Volley.newRequestQueue(context);

    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }


    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024 * 16); // 16mb
            // cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag("App");
        getRequestQueue().add(req);
    }

}
