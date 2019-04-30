package com.eleganzit.amigo.api;

/**
 * Created by eleganz on 30/4/19.
 */

public class ApiUtil {
    private static final String BASE_URL = "http://itechgaints.com/Volunteerify-API/";

    public static RetrofitInterface getServiceClass(){
        return RetrofitAPI.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
