package ru.tokerrg.flckrclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import retrofit2.Call;
import retrofit2.Response;
import ru.tokerrg.flckrclient.api.Api;
import ru.tokerrg.flckrclient.api.Constants;
import ru.tokerrg.flckrclient.api.model.TestLoginUser;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

public class MainActivity extends Activity {

    /*TODO
    1. Make normal splash screen like in Titanium
    2. Make a request using rxjava to get some photos
    */
    private Api mApi;
    private OAuthProvider provider;
    private OAuthConsumer consumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApi = Api.getInstance();
        provider = mApi.getOAuthProvider();
        consumer = mApi.getOAuthConsumer();
        String oauthVerifier = "";

        Log.i("MainActivity", "onCreate called");

        final Uri uri = getIntent().getData();
        if (uri != null && uri.getScheme().equals(Constants.CALLBACK_SCHEME)) {
            oauthVerifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

            Log.i("MainActivity", String.format("Redirected uri: %s", uri.toString()));
            Log.i("MainActivity", String.format("Extracted OAuth verifier: %s", oauthVerifier));
            //bus.post(new OAuthVerifierFetchedEvent(oauthVerifier));

            new RequestAccessTokenTask().execute(oauthVerifier);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i("MainActivity", "onNewIntent called");

        /*final Uri uri = getIntent().getData();
        if (uri != null && uri.getScheme().equals(Constants.CALLBACK_SCHEME)) {
            final String oauthVerifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

            Log.i("MainActivity", String.format("redirected url: %s", uri.toString()));
            Log.i("MainActivity", String.format("Extracted OAuth verifier: %s", oauthVerifier));
            //bus.post(new OAuthVerifierFetchedEvent(oauthVerifier));
        }*/
    }

    private class RequestAccessTokenTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.i("MainActivity", "onPreExecute called");
        }

        @Override
        protected Void doInBackground(String... strings) {
            String oauthVerifier = strings[0];
            try {
                provider.retrieveAccessToken(consumer, oauthVerifier);
            } catch (Exception e) {
                Log.i("MainActivity", "Exception with message: " + e.getMessage());
                e.printStackTrace();
            }

            OkHttpOAuthConsumer okHttpOAuthConsumer =
                    new OkHttpOAuthConsumer(consumer.getConsumerKey(), consumer.getConsumerSecret());
            okHttpOAuthConsumer.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());

            mApi.buildRestAdapter(okHttpOAuthConsumer);
            Call<TestLoginUser> call = mApi.testLogin();
            try {
                Response<TestLoginUser> response = call.execute();
                int statusCode = response.code();
                TestLoginUser tlu = response.body();
                Log.i("MainActivity", "statusCode: " + statusCode + ", response: " + tlu.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            Log.i("MainActivity", "onPostExecute called");
            Log.i("MainActivity", "access token and secret: " + consumer.getToken() + ", " +
                    consumer.getTokenSecret());
        }
    }
}