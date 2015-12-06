package com.computinglife.loverface.request;

/**
 * Created by youngliu on 12/6/15.
 */
public class TestFaceRequest {
    private String picKey;

    private Integer uid;

    public TestFaceRequest(String picKey, Integer uid) {
        this.picKey = picKey;
        this.uid = uid;
    }

    public String getPicKey() {
        return picKey;
    }

    public void setPicKey(String picKey) {
        this.picKey = picKey;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}
