package com.register.me.APIs;

import com.google.gson.JsonObject;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.ApplicationRRESubmission;
import com.register.me.model.data.model.AuctionWon;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.CRREResponse;
import com.register.me.model.data.model.CertificateStatus;
import com.register.me.model.data.model.CertifiedRREList;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.ChargeTokenResponse;
import com.register.me.model.data.model.Client;
import com.register.me.model.data.model.ClientProductList;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.CurrencyCode;
import com.register.me.model.data.model.CurrencyConversion;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LibraryFiles;
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.MasterDash;
import com.register.me.model.data.model.McrreList;
import com.register.me.model.data.model.McrreProductDetails;
import com.register.me.model.data.model.MyActiveAuction;
import com.register.me.model.data.model.MyAuctionInProgress;
import com.register.me.model.data.model.PolicyTraining;
import com.register.me.model.data.model.PostReply;
import com.register.me.model.data.model.ProjectModel;
import com.register.me.model.data.model.RRE;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.RREComments;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.model.ReqGeoRegion;
import com.register.me.model.data.model.RequestRegion;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.ScheduleList;
import com.register.me.model.data.model.Steps;
import com.register.me.model.data.model.SuccessCertificate;
import com.register.me.model.data.model.TimeSchedule;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.model.UploadDoc;
import com.register.me.model.data.model.UserInfo;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 10:33.
 */
public interface ApiInterface {
    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> getUserLogin(@Field("userName") String username,
                                  @Field("password") String password,
                                  @Field("grant_type") String type);

    @POST("user")
    Call<RegisterModel> getUserSignUp(@Body JsonObject data);

    @GET("forgotpassword")
    Call<ResponseBody> forgotPassword(@Query("email") String emailAddress);

    @GET("changepassword")
    Call<ChangePasswordModel> changePassword(@Header("Authorization") String token,
                                             @Query("oldpwd") String old,
                                             @Query("newpwd") String newPass);

    @POST("logout")
    Call<LogoutModel> logout(@Header("Authorization") String token);

    @POST("useravatar")
    Call<AvatarModel> updateProfileImage(@Header("Authorization") String token,
                                         @Body JsonObject encoded);

    @GET("user")
    Call<GetUserInfoModel> getUserDetails(@Header("Authorization") String token);

    @POST("userprofile")
    Call<UpdateProfileModel> updateUserProfile(@Header("Authorization") String token,
                                               @Body JsonObject data);

    @GET("product")
    Call<GetProductModel> getProductList(@Header("Authorization") String token);

    @POST("product")
    Call<AddProductModel> addProduct(@Header("Authorization") String token,
                                     @Body JsonObject data);


    @PUT("product")
    Call<AddProductModel> editProduct(@Header("Authorization") String token,
                                      @Query("productid") int id,
                                      @Body JsonObject data);

    @GET("productdetails")
    Call<ViewDetails> viewDetails(@Header("Authorization") String token,
                                  @QueryMap HashMap<String, String> data);

    @GET("location")
    Call<LocationModel> getLocation(@Header("Authorization") String token);

    @POST("initiatebidding")
    Call<AddProductModel> initiateBid(@Header("Authorization") String token,
                                      @Body JsonObject data);


    @POST("directassignment")
    Call<AddProductModel> directAssign(@Header("Authorization") String token,
                                       @Body JsonObject data);

    @POST("projectcancel")
    Call<ResponseBody> cancelProject(@Header("Authorization") String token,
                                     @Body JsonObject data);

    @GET("countrylist")
    Call<ProjectModel> getProjectList(@Header("Authorization") String token,
                                      @Query("productid") String id);

    @GET("activeprojectauctions")
    Call<ActiveAuction> getAuctionList(@Header("Authorization") String token);

    @GET("activecompletedprojects")
    Call<ActiveCompProject> getACList(@Header("Authorization") String token);

    @GET("activecompleteviews")
    Call<ViewActCompProject> getACDetail(@Header("Authorization") String token,
                                         @Query("projectid") String id);

    @POST("regionrequest")
    Call<RequestRegion> requestRegion(@Header("Authorization") String token,
                                      @Body JsonObject obj);

    @POST("getcrrelist")
    Call<CrreList> getCrreList(@Header("Authorization") String token,
                               @Body JsonObject obj);

    @POST("postreplymail")
    Call<PostReply> postReply(@Header("Authorization") String token,
                              @Body JsonObject obj);

    /*************************************************     RRE PROCESS    ****************************************/
    @GET("rrprocesssteps")
    Observable<Response<Steps>> getStepStatus(@Header("Authorization") String token);

    @GET("rreviewapplication")
    Observable<Response<RREApplication>> getRREApplication(@Header("Authorization") String token);

    /*Try using ResponseData*/
    @POST("rreaddapplicationform")
    Observable<Response<ApplicationRRESubmission>> submitRREApplicationForm(@Header("Authorization") String token,
                                                                            @Body JsonObject obj);

    /*Try using ResponseData*/
    @POST("rreuploaddocument")
    Observable<Response<UploadDoc>> uploadDoc(@Header("Authorization") String token,
                                              @Body JsonObject obj);

    /*Try using ResponseData*/
    @POST("rrepostreplymail")
    Observable<Response<RREComments>> rrePostReply(@Header("Authorization") String token,
                                                   @Body JsonObject obj);

    @GET("rreavailabletimeschedule")
    Observable<Response<TimeSchedule>> getAvailableTime(@Header("Authorization") String token);

    @POST("rresubmitcancelschedule")
    Observable<Response<ResponseData>> submitCancelSchedule(@Header("Authorization") String token,
                                                            @Body JsonObject obj);

    @GET("rreapplicationvideoquestions")
    Observable<Response<PolicyTraining>> getTrainingQuetions(@Header("Authorization") String token);

    @POST("rretrainingstatus")
    Observable<Response<ResponseData>> postTrainingStatus(@Header("Authorization") String token,
                                                          @Body JsonObject obj);

    @POST("rresubmitcertificate")
    Observable<Response<ResponseData>> submitCertificate(@Header("Authorization") String token);

    /*************************************************     CRRE PROCESS    ****************************************/

    @GET("cractivebidding")
    Observable<Response<MyActiveAuction>> getActiveAuctionList(@Header("Authorization") String token);

    @GET("crbiddingprogress")
    Observable<Response<MyAuctionInProgress>> getAuctionsInProgress(@Header("Authorization") String token);

    @POST("crsubmitbid")
    Observable<Response<ResponseData>> submitBid(@Header("Authorization") String token,
                                                 @Body JsonObject jsonObject);

    @GET("crdeclinebid")
    Observable<Response<ResponseData>> declineBid(@Header("Authorization") String token,
                                                  @Query("projectid") String projectID);

    @GET("crcurrencylist")
    Observable<Response<CurrencyCode>> getCurrencyCode(@Header("Authorization") String token);

    @GET
    Observable<Response<CurrencyConversion>> getCurrency(@Url String url);

    @GET("crcertifyandstory")
    Observable<Response<SuccessCertificate>> getcertifyAndStory(@Header("Authorization") String token);

    @POST("crcancelbid")
    Observable<Response<ResponseData>> cancelBid(@Header("Authorization") String token,
                                                 @Body JsonObject jsonObject);

    @POST("crnewcountry")
    Observable<Response<ResponseData>> requestNewCountry(@Header("Authorization") String token,
                                                         @Body JsonObject jsonObject);

    @GET("crlibrary")
    Observable<Response<LibraryFiles>> getLibraryFiles(@Header("Authorization") String token,
                                                       @Query("locationid") int id);

    @POST("crfiledelete")
    Observable<Response<CRREResponse>> crreDeleteFiles(@Header("Authorization") String token,
                                                       @Body JsonObject jsonObject);

    @POST("crfileupload")
    Observable<Response<CRREResponse>> crreUploadFiles(@Header("Authorization") String token,
                                                       @Body JsonObject jsonObject);

    /*************************************************     MASTER CRRE PROCESS    ****************************************/
    @GET("mcrdashboard")
    Observable<Response<MasterDash>> getMasterDashboard(@Header("Authorization") String token);

    @GET("mcrclientdetails")
    Observable<Response<Client>> getclientdetails(@Header("Authorization") String token);

    @GET("mcrrredetails")
    Observable<Response<RRE>> getRREDetails(@Header("Authorization") String token);

    @GET("mcrcertifieddetails")
    Observable<Response<CertifiedRREList>> getCrreDetails(@Header("Authorization") String token);
    @POST("mcruserinfo")
    Observable<Response<UserInfo>> getUserInfo(@Header("Authorization") String token,
                                               @Body JsonObject jsonObject);

    @GET("mcrproductdetails")
    Observable<Response<ClientProductList>> getClientProductDetails(@Header("Authorization") String token,
                                                                    @Query("id") String id);

    @GET("mcrapplicationapprove")
    Observable<Response<ResponseData>> approveApplication(@Header("Authorization") String token,
                                                          @Query("id") String id);

    @POST("mcrinterviewapprove")
    Observable<Response<ResponseData>> approveInterview(@Header("Authorization") String token,
                                                        @Body JsonObject jsonObject);


    @GET("mcrtrainingapprove")
    Observable<Response<ResponseData>> approveTraining(@Header("Authorization") String token,
                                                       @Query("id") String id);

    @GET("mcrcertificateapprove")
    Observable<Response<ResponseData>> approveCertificate(@Header("Authorization") String token,
                                                          @Query("id") String id);
   /* @Multipart
    @POST("Create")
    Call<ResponseBody> getpdf(@PartMap() Map<String, RequestBody> partmap, @Part MultipartBody.Part file);

    @Multipart
    @POST("Create")
    Call<ResponseBody> getpdf1(@Part("Rotate") String rotate, @Part("Deskew") String deskew, @Part("Clear")String clear,
                               @Part("TrustLevel") int trustlevel, @Part("CompactationLevel") int compactationlevel,
                               @Part("LanguageValue")String languagevalue,
                               @Part("ResolutionLevel") int resolutionlevel,
                               @Part MultipartBody.Part imageFileBody);*/

    @GET("mcrcertificateprocess")
    Observable<Response<CertificateStatus>> getCertificateStatus(@Header("Authorization") String token,
                                                                 @Query("id") String id);


    @GET("mcrinterviewschedule")
    Observable<Response<ScheduleList>> getInterviewSchedule(@Header("Authorization") String token);

    @GET("mcrapproveschedule")
    Observable<Response<ResponseData>> approveSchedule(@Header("Authorization") String token,
                                                       @Query("id") String id);

    @GET("mcrcancelschedule")
    Observable<Response<ResponseData>> cancelSchedule(@Header("Authorization") String token,
                                                      @Query("id") String id);

    @POST("mcraddavailability")
    Observable<Response<ResponseData>> addAvailability(@Header("Authorization") String token,
                                                       @Body JsonObject jsonObject);

    @GET("mcrgeographiclocations")
    Observable<Response<GeographicLocation>> getGeoLocationList(@Header("Authorization") String token);

    @GET("mcrrequestregions")
    Observable<Response<ReqGeoRegion>> getRequestedGeoList(@Header("Authorization") String token);

    @GET("mcrregionaccept")
    Observable<Response<ResponseData>> acceptRequestedRegion(@Header("Authorization") String token,
                                                             @Query("id") String id);

    @POST("mcrregioncancel")
    Observable<Response<ResponseData>> cancelRequestedRegion(@Header("Authorization") String token, @Body JsonObject object);

    @GET("mcrredetails")
    Observable<Response<McrreList>> getMcrreList(@Header("Authorization") String token);

    @POST("mcrregionedit")
    Observable<Response<ResponseData>> editGeoRegion(@Header("Authorization") String token, @Body JsonObject object);

    @GET("mcrremoveregion")
    Observable<Response<ResponseData>> removeGeoRegion(@Header("Authorization") String token, @Query("id") String id);

    @GET("location")
    Observable<Response<LocationModel>> getMasterLocation(@Header("Authorization") String token);

    @GET("mcrproductview")
    Observable<Response<ViewDetails>> getMcrreProductDetails(@Header("Authorization") String token, @Query("productid") String producID);

    @GET("mcrrreviewapplication")
    Observable<Response<RREApplication>> getDocCommentsList(@Header("Authorization") String token, @Query("id") String producID);

    @POST("mcrnewregion")
    Observable<Response<ResponseData>> addNewGeoLocation(@Header("Authorization") String token, @Query("region") String producID);

    @POST("mcrrrepostreplymail")
    Observable<Response<ResponseData>> mcrrePostReply(@Header("Authorization") String token, @Body JsonObject object);

    @GET("mcrauctionwon")
    Observable<Response<AuctionWon>> getAuctionsWon(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("v1/charges")
    Observable<Response<ChargeTokenResponse>> chargeToken(
            @Header("Authorization") String apiToken,
            @Field("amount") String amount,
            @Field("currency") String currency,
            @Field("description") String description,
            @Field("source") String source
    );
}
