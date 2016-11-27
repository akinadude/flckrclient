package ru.tokerrg.flckrclient.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ru.tokerrg.flckrclient.api.auth.RetrofitHttpOAuthConsumer;
import ru.tokerrg.flckrclient.api.auth.SigningOkClient;
import ru.tokerrg.flckrclient.api.model.TestLoginUser;

public class Api {

    private static Api mInstance;
    private IApi mApiInterface;
    private Gson mGson;
    private RestAdapter restAdapter;

    private OAuthConsumer mOauthConsumer;
    private OAuthProvider mOauthProvider;
    private RetrofitHttpOAuthConsumer mRetrofitOauthConsumer;

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
        mRetrofitOauthConsumer = new RetrofitHttpOAuthConsumer(mOauthConsumer.getConsumerKey(),
                mOauthConsumer.getConsumerSecret());

        /*RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API_REQUESTS_URL)
                .setConverter(new GsonConverter(mGson))
                .setClient(getSigningOkHttpClient(mRetrofitOauthConsumer))
                .build();
        mApiInterface = restAdapter.create(IApi.class);*/
    }


    private SigningOkClient getSigningOkHttpClient(RetrofitHttpOAuthConsumer consumer) {
        SigningOkClient client = new SigningOkClient(consumer);
        return client;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        client.setReadTimeout(20, TimeUnit.SECONDS);
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

    public void buildRestAdapter(RetrofitHttpOAuthConsumer consumer) {
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API_REQUESTS_URL)
                .setConverter(new GsonConverter(mGson))
                .setClient(getSigningOkHttpClient(consumer))
                .build();
        mApiInterface = restAdapter.create(IApi.class);
    }

    public Void authStepOne() {
        int nonce = new Random().nextInt();
        long timestamp = System.currentTimeMillis()/1000;

        return mApiInterface.authStepOne(nonce, timestamp, Constants.CONSUMER_KEY, "HMAC-SHA1",
                Constants.API_VERSION, Constants.OAUTH_URL_CALLBACK);
    }

    public TestLoginUser testLogin() {
        return mApiInterface.testLogin(1, "json", "flickr.test.login");
    }
}