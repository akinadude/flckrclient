package ru.tokerrg.flckrclient.api.auth;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

public class SigningOkClient extends OkHttpClient {
    private final OkHttpOAuthConsumer consumer;

    public SigningOkClient(OkHttpOAuthConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public Call newCall(Request request) {
        Request signedRequest = null;
        try {
            signedRequest = (Request) consumer.sign(request).unwrap();
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }

        return super.newCall(signedRequest);
    }
}