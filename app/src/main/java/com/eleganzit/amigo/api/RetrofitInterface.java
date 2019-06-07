package com.eleganzit.amigo.api;

import com.eleganzit.amigo.model.GetOpportunitiesEventsResponse;
import com.eleganzit.amigo.model.donation.AddDonationResponse;
import com.eleganzit.amigo.model.SearchDataResponse;
import com.eleganzit.amigo.model.AddCommentResponse;
import com.eleganzit.amigo.model.AddEventResponse;
import com.eleganzit.amigo.model.AppliedListResponse;
import com.eleganzit.amigo.model.DeleteEventResponse;
import com.eleganzit.amigo.model.GetEventsResponse;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.GetOpportunitiesResponse;
import com.eleganzit.amigo.model.GetOtherUserResponse;
import com.eleganzit.amigo.model.GetSingleEventResponse;
import com.eleganzit.amigo.model.GetSingleOpportunityResponse;
import com.eleganzit.amigo.model.GetStateResponse;
import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.GetWorkEduResponse;
import com.eleganzit.amigo.model.GetWorkResponse;
import com.eleganzit.amigo.model.ListPostCommentResponse;
import com.eleganzit.amigo.model.PostLikesResponse;
import com.eleganzit.amigo.model.RejectedListResponse;
import com.eleganzit.amigo.model.SaveLikeResponse;
import com.eleganzit.amigo.model.SendRequestDataResponse;
import com.eleganzit.amigo.model.UpdateUserResponse;
import com.eleganzit.amigo.model.addedu.AddEduResponse;
import com.eleganzit.amigo.model.donation.DeleteDonationResponse;
import com.eleganzit.amigo.model.donation.DonarListResponse;
import com.eleganzit.amigo.model.donation.GetDonationResponse;
import com.eleganzit.amigo.model.donation.SingleDonationResponse;
import com.eleganzit.amigo.model.friendlist.FriendListResponse;
import com.eleganzit.amigo.model.getfriendrequest.GetRequestDataResponse;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.opportunity.OpportuntyResponse;
import com.eleganzit.amigo.model.searchDataClasses.SearchAllDataResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {


    @FormUrlEncoded
    @POST("/Volunteerify-API/requestStatus")
    Call<SendRequestDataResponse> cancelFollowRequest(@Field("request_id") String request_id, @Field("status") String status);
 @FormUrlEncoded
    @POST("/Volunteerify-API/getRequests")
    Call<GetRequestDataResponse> getRequests(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/Volunteerify-API/sendRequest")
    Call<SendRequestDataResponse> sendFollowRequest(@Field("user_id") String user_id, @Field("request_user_id") String request_user_id);


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
            @Part("country")  RequestBody country,
            @Part("pincode") RequestBody pincode,
            @Part("contact_person")  RequestBody contact_person,
            @Part("contact_person_number")   RequestBody contact_person_number,
            @Part MultipartBody.Part proof ,
            @Part("lat")  RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("device_id") RequestBody device_id,
            @Part("device_token")  RequestBody device_token




                        );

    @FormUrlEncoded
    @POST("/Volunteerify-API/getUser")
    Call<GetOtherUserResponse> getOtherUser(
            @Field("user_id") String user_id,
            @Field("request_user_id") String request_user_id




    );

/*

    @Multipart
    @POST("/Volunteerify-API/addUserpost")
    Call<CreatePostResponse> createPost(

            @Part("type") RequestBody type,
            @Part("user_id") RequestBody user_id,
            @Part List<MultipartBody.Part> photo_url,
            @Part("content") RequestBody content,
            @Part("tag_user_id") RequestBody tag_user_id


    );


*/

    @FormUrlEncoded
    @POST("/Volunteerify-API/sendRequest")
    Call<SendRequestDataResponse> sendFriendRequest(@Field("user_id") String user_id, @Field("request_user_id") String request_user_id);


    @FormUrlEncoded
    @POST("/Volunteerify-API/allUserlist")
    Call<SearchDataResponse> searchAll(@Field("user_id") String user_id,
                                       @Field("search") String search,
                                       @Field("from_limit") String from_limit);

    @FormUrlEncoded
    @POST("/Volunteerify-API/searchData")
    Call<SearchAllDataResponse> searchData(
            @Field("type") String type,
            @Field("user_id") String user_id,
                                           @Field("search") String search,
                                           @Field("from_limit") String from_limit,
                                           @Field("to_limit") String to_limit

    );



    @FormUrlEncoded
    @POST("/Volunteerify-API/loginUser")
    Call<GetLoginResponse> getLogin(
            @Field("username") String username,
            @Field("password") String password

    );

@FormUrlEncoded
    @POST("/Volunteerify-API/user_newsfeedData")
    Call<NewsFeedDataResponse> user_newsfeedData(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit,
            @Field("type") String type

    );
@FormUrlEncoded
    @POST("/Volunteerify-API/getUserpost")
    Call<NewsFeedDataResponse> getUserpost(
            @Field("post_id") String post_id

    );

@FormUrlEncoded
    @POST("/Volunteerify-API/amigoEventByDate")
    Call<GetOpportunitiesEventsResponse> amigoEventByDate(
            @Field("user_id") String user_id,
            @Field("date") String date

    );


  @FormUrlEncoded
    @POST("/Volunteerify-API/getAllstate")
    Call<GetStateResponse> getAllstate(
            @Field("country_id") String country_id
    );



  @FormUrlEncoded
    @POST("/Volunteerify-API/getUser")
    Call<GetLoginResponse> getUserData(
          @Field("user_id") String user_id

  );

  @FormUrlEncoded
    @POST("/Volunteerify-API/listuserWork")
    Call<GetWorkEduResponse> getWorkLoad(
          @Field("user_id") String user_id

  );


  @FormUrlEncoded
    @POST("/Volunteerify-API/adduserWork")
    Call<GetWorkResponse> addWorkData(
            @Field("user_id") String user_id,
            @Field("place_name") String place_name,
            @Field("position") String position,
            @Field("physical_place") String physical_place,
            @Field("place") String place,
            @Field("description") String description,
            @Field("date_from") String date_from,
            @Field("date_to") String date_to,
            @Field("work_here") String work_here




  );



@FormUrlEncoded
    @POST("/Volunteerify-API/updateuserWork")
    Call<GetWorkResponse> updateuserWork(
            @Field("user_id") String user_id,
            @Field("work_id") String work_id,
            @Field("place_name") String place_name,
            @Field("position") String position,
            @Field("physical_place") String physical_place,
            @Field("place") String place,
            @Field("description") String description,
            @Field("date_from") String date_from,
            @Field("date_to") String date_to,
            @Field("work_here") String work_here




  );


@FormUrlEncoded
@POST("/Volunteerify-API/deleteuserWork")
Call<GetWorkResponse> deleteuserwork(
        @Field("work_id") String work_id
);

@FormUrlEncoded
@POST("/Volunteerify-API/deleteuserEducation")
Call<AddEduResponse> deleteuserEducation(
        @Field("id") String id
);

@FormUrlEncoded
    @POST("/Volunteerify-API/adduserEducation")
    Call<AddEduResponse> addEducation(
            @Field("user_id") String user_id,
            @Field("place") String place,
            @Field("description") String description,
            @Field("date_from") String date_from,
            @Field("date_to") String date_to,
            @Field("physical_place") String physical_place




);
@FormUrlEncoded
    @POST("Volunteerify-API/updateuserEducation")
    Call<AddEduResponse> updateuserEducation(
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("place") String place,
            @Field("description") String description,
            @Field("date_from") String date_from,
            @Field("date_to") String date_to,
            @Field("physical_place") String physical_place




);




@FormUrlEncoded
    @POST("/Volunteerify-API/updateProfile")
    Call<GetLoginResponse> updateProfile(
            @Field("user_id") String user_id,
            @Field("city") String city,
            @Field("hometown") String hometown

);


@Multipart
    @POST("/Volunteerify-API/updateOpportunity")
    Call<OpportuntyResponse> updatewithOpportunity(
        @Part("opportunity_id") RequestBody opportunity_id,
        @Part("opportunity_name") RequestBody opportunity_name,

        @Part("opportunity_date") RequestBody opportunity_date,
        @Part("user_id") RequestBody user_id,
        @Part MultipartBody.Part opportunity_image,
        @Part("opportunity_time") RequestBody opportunity_time,
        @Part("address") RequestBody address,
        @Part("details") RequestBody details,
        @Part("positions") RequestBody positions,
        @Part("looking_for") RequestBody looking_for,
        @Part("lat") RequestBody lat,
        @Part("lng") RequestBody lng

);
@FormUrlEncoded
    @POST("/Volunteerify-API/updateOpportunity")
    Call<OpportuntyResponse> updateOpportunity(
        @Field("opportunity_id") String opportunity_id,
        @Field("opportunity_name") String opportunity_name,

        @Field("opportunity_date") String opportunity_date,
        @Field("user_id") String user_id,
        @Field("opportunity_time") String opportunity_time,
        @Field("address") String address,
        @Field("details") String details,
        @Field("positions") String positions,
        @Field("looking_for") String looking_for,
        @Field("lat") String lat,
        @Field("lng") String lng

);

@FormUrlEncoded
    @POST("/Volunteerify-API/deleteOpportunity")
    Call<GetSingleOpportunityResponse> deleteOpportunity(
            @Field("opportunity_id") String opportunity_id


);

@FormUrlEncoded
    @POST("/Volunteerify-API/getuserOpportunity")
    Call<GetOpportunitiesResponse> getuserOpportunity(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit


);

@FormUrlEncoded
    @POST("/Volunteerify-API/getsingleOpportunity")
    Call<GetSingleOpportunityResponse> getsingleOpportunity(
            @Field("opportunity_id") String opportunity_id



);

@FormUrlEncoded
    @POST("/Volunteerify-API/getsingleEvent")
    Call<GetSingleEventResponse> getsingleEvent(
            @Field("event_id") String event_id



);


    @Multipart
    @POST("/Volunteerify-API/updateProfile")
    Call<UpdateUserResponse> updateProfile(

            @Part("user_id") RequestBody user_id,

            @Part MultipartBody.Part photo
    );



    @Multipart
    @POST("/Volunteerify-API/addOpportunity")
    Call<OpportuntyResponse> addOpportunity(
            @Part("opportunity_date") RequestBody opportunity_date,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part opportunity_image,
            @Part("opportunity_name") RequestBody opportunity_name,
            @Part("opportunity_time") RequestBody opportunity_time,
            @Part("address") RequestBody address,
            @Part("details") RequestBody details,
            @Part("positions") RequestBody positions,
            @Part("looking_for") RequestBody looking_for,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng

    );

 @Multipart
    @POST("/Volunteerify-API/addEventpost")
    Call<AddEventResponse> addEventpost(
            @Part("event_date") RequestBody event_date,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part opportunity_image,
            @Part("event_time") RequestBody event_time,
            @Part("event_address") RequestBody event_address,
            @Part("event_details") RequestBody event_details,
            @Part("co_host") RequestBody co_host,
            @Part("event_status") RequestBody event_status,
            @Part("event_name") RequestBody event_name,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng

    );
@Multipart
    @POST("/Volunteerify-API/updateEvent")
    Call<AddEventResponse> updateEventpostwithimage(
            @Part("event_date") RequestBody event_date,
            @Part("event_id") RequestBody event_id,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part opportunity_image,
            @Part("event_time") RequestBody event_time,
            @Part("event_address") RequestBody event_address,
            @Part("event_details") RequestBody event_details,
            @Part("co_host") RequestBody co_host,
            @Part("event_status") RequestBody event_status,
            @Part("event_name") RequestBody event_name,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng

    );

@FormUrlEncoded
    @POST("/Volunteerify-API/updateEvent")
    Call<AddEventResponse> updateEventpost(
            @Field("event_date") String event_date,
            @Field("event_id") String event_id,
            @Field("user_id") String user_id,
            @Field("event_time") String event_time,
            @Field("event_address") String event_address,
            @Field("event_details") String event_details,
            @Field("co_host") String co_host,
            @Field("event_status") String event_status,
            @Field("event_name") String event_name,
            @Field("lat") String lat,
            @Field("lng") String lng

    );



@FormUrlEncoded
@POST("/Volunteerify-API/deleteEvent")
Call<DeleteEventResponse> deleteEvent(
        @Field("event_id") String event_id
);

    @FormUrlEncoded
    @POST("/Volunteerify-API/getRequestedUserOpportunity")
    Call<AppliedListResponse> getRequestedUserOpportunity(
            @Field("opportunity_id") String opportunity_id,
            @Field("from_limit") String from_limit
    );

@FormUrlEncoded
    @POST("/Volunteerify-API/getuserEvent")
    Call<GetEventsResponse> getuserEvent(
            @Field("user_id") String opportunity_id,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded
    @POST("Volunteerify-API/getRejectedUserOpportunity")
    Call<RejectedListResponse> getRejectedUserOpportunity(
            @Field("opportunity_id") String opportunity_id,
            @Field("from_limit") String from_limit,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST("Volunteerify-API/updateApplyUserStatus")
    Call<ResponseBody> updateApplyUserStatus(
            @Field("apply_id") String apply_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("Volunteerify-API/savePostComment")
    Call<AddCommentResponse> savePostComment(
            @Field("post_id") String post_id,
            @Field("user_id") String user_id,
            @Field("comment_by") String comment_by,
            @Field("comment") String comment
    );
    @FormUrlEncoded
    @POST("Volunteerify-API/listPostComment")
    Call<ListPostCommentResponse> listPostComment(
            @Field("post_id") String post_id

    );

    @FormUrlEncoded
    @POST("/Volunteerify-API/savePostLike")
    Call<SaveLikeResponse> savePostLike(
            @Field("post_id") String post_id,
            @Field("user_id") String user_id,
            @Field("liked_by") String liked_by

    );



    @FormUrlEncoded
    @POST("Volunteerify-API/getPostLike")
    Call<PostLikesResponse> getPostLike(
            @Field("post_id") String post_id

    );


    //Add Donation
    @Multipart
    @POST("/Volunteerify-API/addDonations")
    Call<AddDonationResponse> addDonations(

            @Part("user_id") RequestBody user_id,
            @Part("title") RequestBody title,
            @Part("need_amount") RequestBody need_amount,
            @Part("description") RequestBody description,
            @Part("donation_start_date") RequestBody donation_start_date,
            @Part("donation_end_date") RequestBody donation_end_date,
            @Part("status") RequestBody status,

            @Part MultipartBody.Part donation_image




    );
    @Multipart
    @POST("/Volunteerify-API/updateDonation")
    Call<AddDonationResponse> updateDonation(

            @Part("donation_id") RequestBody donation_id,
            @Part("title") RequestBody title,
            @Part("need_amount") RequestBody need_amount,
            @Part("description") RequestBody description,
            @Part("donation_start_date") RequestBody donation_start_date,
            @Part("donation_end_date") RequestBody donation_end_date,
            @Part("status") RequestBody status,

            @Part MultipartBody.Part donation_image




    );


@FormUrlEncoded
@POST("/Volunteerify-API/getSingleDonation")
Call<SingleDonationResponse> getSingleDonation(
        @Field("donation_id") String donation_id
);


    @FormUrlEncoded
    @POST("/Volunteerify-API/getAllDonations")
    Call<GetDonationResponse> getAllDonations(


            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit,
            @Field("donation_user") String donation_user

    );


    @FormUrlEncoded
    @POST("/Volunteerify-API/delateDonation")
    Call<DeleteDonationResponse> delateDonation(
            @Field("donation_id") String donation_id
    );


    @FormUrlEncoded
    @POST("/Volunteerify-API/getDonorlist")
    Call<DonarListResponse> getDonorlist(
            @Field("donation_id") String donation_id
    );


    @FormUrlEncoded
    @POST("/Volunteerify-API/updateDonation")
    Call<AddDonationResponse> updateDonationWithoutImage(

            @Field("donation_id") String donation_id,
            @Field("title") String title,
            @Field("need_amount") String need_amount,
            @Field("description") String description,
            @Field("donation_start_date") String donation_start_date,
            @Field("donation_end_date") String donation_end_date,
            @Field("status") String status





    );



    @FormUrlEncoded
    @POST("/Volunteerify-API/myFriends")
    Call<FriendListResponse> getmyFriendsList(
            @Field("user_id") String user_id

    );
}
