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
    //线上
    //public static final String BASE_DATA_INTERFACE_URL = "http://loverface-youngliu.myalauda.cn/LoverFaceServer/";

    //测试
    public static final String BASE_DATA_INTERFACE_URL = "http://192.168.199.117:8080/LoverFace-Server/";

    public static final String BASE_IMG_INTERFACE_URL = "http://7xonka.com1.z0.glb.clouddn.com/";

    public static void post(Context context, String url, HttpEntity entity,
                               ResponseHandlerInterface responseHandler) {
        AsyncHttpClient client = AsyncClientSingleton.getInstance();
        client.addHeader("Accept", "application/json");
        client.setTimeout(20 * 1000); // FIXME
        client.post(context, getBaseDataInterfaceUrl() + url, entity, "application/json;charset=UTF-8",
                responseHandler);
    }

    public static void get(Context context, String url, RequestParams parameters, ResponseHandlerInterface responseHandler) {
        AsyncHttpClient client = AsyncClientSingleton.getInstance();
        client.addHeader("Accept", "application/json");
        client.get(context, getBaseDataInterfaceUrl() + url, parameters, responseHandler);
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
