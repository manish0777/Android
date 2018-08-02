package com.manish.utils;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.manish.ApplicationLoader;
import com.manish.model.APIParserBean;
import com.manish.network.NetworkRequest;

public class DataUtils {

private static String URL="https://stark-spire-93433.herokuapp.com/json";
    public static void getResult() {
        NetworkRequest.ServerResponse serverResponse = new NetworkRequest.ServerResponse<String>() {
            @Override
            public String parseData(String json) {
                try {
                    APIParserBean.parseData(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onResponse(String response) {


            }

            @Override
            public void onError(VolleyError error) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        NetworkRequest<String> request = new NetworkRequest<>(ApplicationLoader.getInstance(), String.class, serverResponse);
        request.setRequestMethod(Request.Method.GET);
        request.execute(URL);
    }
}
