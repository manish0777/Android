package com.manish.helper;

import com.android.volley.VolleyError;
import com.manish.model.ApiResponse;

public interface ApiResponseLisners {
    void onResponse(ApiResponse response);
    void onError(VolleyError error);
}
