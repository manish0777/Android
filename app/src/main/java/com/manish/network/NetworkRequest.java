package com.manish.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by manish
 */
public class NetworkRequest<T> {

    protected final Class<T> clazz;
    private ServerResponse<T> listener;
    private Context context;
    private Map<String, String> header = null;
    private int requestMethod = Request.Method.GET;
    private String url;
    private static final int REQUEST_TIMEOUT = 10000;
    private static final int MAX_RETRIES = 2;
    private static final int BACKOFF_MULTIPLIER = 2;

    public interface ServerResponse<T> {
        T parseData(String json);

        void onResponse(T response);

        void onError(VolleyError error);
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }
    public NetworkRequest(Context context, Class<T> clazz, ServerResponse<T> listener) {
        this.clazz = clazz;
        this.context = context;
        this.listener = listener;
    }


    public void execute(String url) {
        this.url = url;
        Log.d(this.getClass().getName(), "Request URL: " + url);
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        };

        Request<String> request = new Request<String>(requestMethod, url, errorListener) {

            @Override
            protected void deliverResponse(String response) {
//                if (listener != null) {
//                    listener.onResponse(response);
//                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Log.d("Network", "Response: " + json);


                    if (listener != null) {
                        final T parseData = listener.parseData(json);
//                        ((Activity) context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (listener != null) {
//                                    listener.onResponse(parseData);
//                                }
//                            }
//                        });

                        return Response.success("success", HttpHeaderParser.parseCacheHeaders(response));
                    }
                } catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    return Response.error(new VolleyError(response));
                }
                return Response.success("success", HttpHeaderParser.parseCacheHeaders(response));

            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT, MAX_RETRIES, BACKOFF_MULTIPLIER));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


}
