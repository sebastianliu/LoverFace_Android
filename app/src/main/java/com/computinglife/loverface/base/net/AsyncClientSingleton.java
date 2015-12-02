package com.computinglife.loverface.base.net;

import com.loopj.android.http.AsyncHttpClient;

/**
 * AsyncHttpClient单例类
 * Created by youngliu on 11/30/15.
 */
public class AsyncClientSingleton {
    private AsyncHttpClient client;

    private AsyncClientSingleton(){
        client = new AsyncHttpClient();
    }

    private static class SingletonHolder {
        private final static AsyncClientSingleton INSTANCE = new AsyncClientSingleton();
    }
    public static AsyncHttpClient getInstance() {
        return SingletonHolder.INSTANCE.client;
    }
}
