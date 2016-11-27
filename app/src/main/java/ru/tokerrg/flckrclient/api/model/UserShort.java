package ru.tokerrg.flckrclient.api.model;

import com.google.gson.annotations.SerializedName;

public class UserShort {

    @SerializedName("id")
    String id;

    @SerializedName("username")
    Username username;
}
