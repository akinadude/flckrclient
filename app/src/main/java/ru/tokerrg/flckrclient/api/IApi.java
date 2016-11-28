package ru.tokerrg.flckrclient.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.tokerrg.flckrclient.api.model.TestLoginUser;

/**
 * API interface
 */
public interface IApi {

    @GET("https://api.flickr.com/services/rest")
    Call<TestLoginUser> testLogin(@Query("nojsoncallback") int njc,
                                  @Query("format") String format,
                                  @Query("method") String method);
}