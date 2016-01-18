package com.coder.nosandroid.niceosandroid.pm25volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by saberhao on 2016/1/18.
 */
public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Gson mGson = new Gson();
    private Class<T> mClass;
    private Map<String,String> mHeaders;

    // supprot Method and header modication
    public GsonRequest(int method, String url,Response.Listener<T> mListener,
                       Response.ErrorListener errorlistener, Map<String, String> mHeaders, Class<T> mClass) {
        super(method, url, errorlistener);
        this.mListener = mListener;
        this.mHeaders = mHeaders;
        this.mClass = mClass;
    }

    // do not modfiy the header
    public GsonRequest(int method, String url, Response.Listener<T> mListener,
                       Response.ErrorListener errorlistener,Class<T> mClass) {
        super(method, url, errorlistener);
        this.mListener = mListener;
        this.mHeaders = null;
        this.mClass = mClass;
    }

    //Get Method and Do not modify the header
    public GsonRequest(String url, Response.Listener<T> mListener,
                       Response.ErrorListener errorlistener,Class<T> mClass) {
        super(Method.GET, url, errorlistener);
        this.mListener = mListener;
        this.mHeaders = null;
        this.mClass = mClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders == null? super.getHeaders():mHeaders;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


}
