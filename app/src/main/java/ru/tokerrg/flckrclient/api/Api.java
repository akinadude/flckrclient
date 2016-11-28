package ru.tokerrg.flckrclient.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tokerrg.flckrclient.api.auth.SigningOkClient;
import ru.tokerrg.flckrclient.api.model.TestLoginUser;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

public class Api {

    private static Api mInstance;
    private IApi mApiInterface;
    private Gson mGson;

    private OAuthConsumer mOauthConsumer;
    private OAuthProvider mOauthProvider;

    public static Api getInstance() {
        if (mInstance == null) mInstance = new Api();
        return mInstance;
    }

    public Api() {
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        mOauthProvider = new DefaultOAuthProvider(
                Constants.API_BASE_URL + Constants.OAUTH_REQUEST_TOKEN,
                Constants.API_BASE_URL + Constants.OAUTH_ACCESS_TOKEN,
                Constants.API_BASE_URL + Constants.OAUTH_AUTHORIZE);
        mOauthConsumer = new DefaultOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
    }


    private SigningOkClient getSigningOkHttpClient(OkHttpOAuthConsumer consumer) {
        SigningOkClient client = new SigningOkClient(consumer);
        return client;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        /*client.setConnectTimeout(20, TimeUnit.SECONDS);
        client.setReadTimeout(20, TimeUnit.SECONDS);*/
        return client;
    }

    public OAuthConsumer getOAuthConsumer() {
        return mOauthConsumer;
    }

    public OAuthProvider getOAuthProvider() {
        return mOauthProvider;
    }

    public void setOAuthConsumer(OAuthConsumer c) {
        mOauthConsumer = c;
    }

    public void setOAuthProvider(OAuthProvider  p) {
        mOauthProvider = p;
    }

    public void buildRestAdapter(OkHttpOAuthConsumer consumer) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_REQUESTS_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(getSigningOkHttpClient(consumer))
                .build();
        mApiInterface = retrofit.create(IApi.class);
    }

    public Call<TestLoginUser> testLogin() {
        return mApiInterface.testLogin(1, "json", "flickr.test.login");
    }
}