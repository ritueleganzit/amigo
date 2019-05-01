package com.eleganzit.amigo.api;

import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {

    @Multipart
    @POST("/Volunteerify-API/addUser")
    Call<GetUserResponse> addUser(

            @Part("user_type") RequestBody user_type,
            @Part("email_id") RequestBody email_id,
            @Part("fullname") RequestBody fullname,
            @Part("mobile") RequestBody mobile,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("city") RequestBody city,
            @Part("state")  RequestBody state,
            @Part("pincode") RequestBody pincode,
            @Part("contact_person")  RequestBody contact_person,
            @Part("contact_person_number")   RequestBody contact_person_number,
            @Part MultipartBody.Part proof ,
            @Part("lat")  RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("device_id") RequestBody device_id,
            @Part("device_token")  RequestBody device_token

                        );

}
