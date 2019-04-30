package com.eleganzit.amigo.api;

import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {

    @Multipart
    @POST("/addUser")
    Call<GetUserResponse> addUser(

            @Part("user_type") String user_type,
            @Part("email_id") String email_id,
            @Part("fullname") String fullname,
            @Part("mobile") String mobile,
            @Part("username") String username,
            @Part("password") String password,
            @Part("city") String city,
            @Part("state")  String state,
            @Part("pincode") String pincode,
            @Part("contact_person")  String contact_person,
            @Part("contact_person_number")   String contact_person_number,
            @Part MultipartBody.Part filePart ,
            @Part("lat")  String lat,
            @Part("lng") String lng,
            @Part("device_id") String device_id,
            @Part("device_token")  String device_token

                        );

}
