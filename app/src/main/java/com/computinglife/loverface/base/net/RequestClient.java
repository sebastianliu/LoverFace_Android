package com.computinglife.loverface.base.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import java.io.InputStream;

/**
 * Created by youngliu on 11/30/15.
 */
public class RequestClient {

    public static final String BASE_DATA_INTERFACE_URL = "http://loverfaceserver-youngliu.myalauda.cn/LoverFaceServer";
    public static final String BASE_IMG_INTERFACE_URL = "http://7xlamq.com2.z0.glb.qiniucdn.com/";

    public static void post(Context context, RequestParams params, String urlParams,
                            AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = AsyncClientSingleton.getInstance();
        client.post((urlParams), params, responseHandler);
    }

    public static void post(Context context, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        AsyncHttpClient client = AsyncClientSingleton.getInstance();
        client.post(context, getBaseDataInterfaceUrl(), entity, "application/json", responseHandler);
    }

    private static String getEntityString(HttpEntity entity) {
        try {
            InputStream instream = entity.getContent();
            String result = IOUtils.toString(instream, "UTF-8");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getBaseDataInterfaceUrl() {
        return BASE_DATA_INTERFACE_URL;
    }

    private static String getBaseImgInterfaceUrl(String urlParams) {
        return BASE_IMG_INTERFACE_URL + "?" + urlParams;
    }
}
