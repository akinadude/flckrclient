package ru.tokerrg.flckrclient.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.tokerrg.flckrclient.api.model.TestLoginUser;

/**
 * API interface
 */
public interface IApi {

    /**
     * Auth step one
     * <p/>
     * Request description goes here
     */
    //@FormUrlEncoded
    @GET(Constants.OAUTH_REQUEST_TOKEN)
    Void authStepOne(@Query("oauth_nonce") int nonce,
                     @Query("oauth_timestamp") long timestamp,
                     @Query("oauth_consumer_key") String consumerKey,
                     @Query("oauth_signature_method") String signatureMethod,
                     @Query("oauth_version") String version,
                     @Query("oauth_callback") String callbackUrl);
    //@Query("oauth_signature") String signature);
    /*
    https://www.flickr.com/services/oauth/request_token
    ?oauth_nonce=95613465 //в двух послед-ных запросах не должен повториться
    &oauth_timestamp=1305586162
    &oauth_consumer_key=653e7a6ecc1d528c516cc8f92cf98611
    &oauth_signature_method=HMAC-SHA1
    &oauth_version=1.0
    &oauth_signature=7w18YS2bONDPL%2FzgyzP5XTr5af4%3D //придется вычислять
    &oauth_callback=http%3A%2F%2Fwww.example.com
    */


    /**
     * Auth step two
     * <p/>
     * Request description goes here
     */
    @GET(Constants.OAUTH_AUTHORIZE)
    public Void authStepTwo(@Query("oauth_token") String authToken);
    /*
    https://www.flickr.com/services/oauth/authorize
    ?oauth_token=72157626737672178-022bbd2f4c2f3432
    */

    /**
     * Auth step three
     * <p/>
     * Request description goes here
     */
    @GET(Constants.OAUTH_ACCESS_TOKEN)
    public Void authStepThree(@Query("oauth_nonce") int nonce,
                              @Query("oauth_timestamp") long timestamp,
                              @Query("oauth_verifier") String verifier,
                              @Query("oauth_consumer_key") String consumerKey,
                              @Query("oauth_signature_method") String signatureMethod,
                              @Query("oauth_version") String version,
                              @Query("oauth_token") String callbackUrl,
                              @Query("oauth_signature") String signature);
    /*
    https://www.flickr.com/services/oauth/access_token
    ?oauth_nonce=37026218
    &oauth_timestamp=1305586309
    &oauth_verifier=5d1b96a26b494074
    &oauth_consumer_key=653e7a6ecc1d528c516cc8f92cf98611
    &oauth_signature_method=HMAC-SHA1
    &oauth_version=1.0
    &oauth_token=72157626737672178-022bbd2f4c2f3432
    &oauth_signature=UD9TGXzrvLIb0Ar5ynqvzatM58U%3D
    */

    @GET("/")
    public TestLoginUser testLogin(@Query("nojsoncallback") int njc,
                                   @Query("format") String format,
                                   @Query("method") String method);
}