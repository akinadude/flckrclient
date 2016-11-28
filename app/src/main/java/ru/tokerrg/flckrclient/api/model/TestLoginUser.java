package ru.tokerrg.flckrclient.api.model;

import com.google.gson.annotations.SerializedName;

public class TestLoginUser {

    @SerializedName("user")
    UserShort user;

    @SerializedName("stat")
    String stat;

    public String toString() {
        return "TestLoginUser: " + user + " " + stat;
    }
}
