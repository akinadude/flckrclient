package ru.tokerrg.flckrclient.api;

/**
 * Created by toker-rg on 07.05.15.
 */
public class Constants {
    public static final String CONSUMER_KEY = "e75fad848561df5128448ceb15fa2786";
    public static final String CONSUMER_SECRET = "63e7401b1edfbf3f";

    public static final String API_BASE_URL = "https://www.flickr.com/services";
    public static final String API_VERSION = "1.0";

    public static final String API_REQUESTS_URL = "https://api.flickr.com/services/rest";

    public static final String OAUTH_REQUEST_TOKEN = "/oauth/request_token";
    public static final String OAUTH_AUTHORIZE = "/oauth/authorize";
    public static final String OAUTH_ACCESS_TOKEN = "/oauth/access_token";
    //public static final String OAUTH_URL_CALLBACK = "http://www.tokerrg.ru";
    public static final String CALLBACK_SCHEME = "ru.tokerrg.flckrclient.android";
    public static final String OAUTH_URL_CALLBACK = CALLBACK_SCHEME + "://oauth_callback";

}