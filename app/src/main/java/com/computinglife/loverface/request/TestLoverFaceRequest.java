package com.computinglife.loverface.request;

/**
 * Created by youngliu on 12/6/15.
 */
public class TestLoverFaceRequest {

    private String keyMale;

    private String keyFemale;

    public TestLoverFaceRequest(String keyMale, String keyFemale) {
        this.keyMale = keyMale;
        this.keyFemale = keyFemale;
    }

    public String getKeyMale() {
        return keyMale;
    }

    public void setKeyMale(String keyMale) {
        this.keyMale = keyMale;
    }

    public String getKeyFemale() {
        return keyFemale;
    }

    public void setKeyFemale(String keyFemale) {
        this.keyFemale = keyFemale;
    }
}
