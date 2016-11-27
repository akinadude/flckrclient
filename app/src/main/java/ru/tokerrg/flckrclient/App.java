package ru.tokerrg.flckrclient;

import android.app.Application;
import ru.tokerrg.flckrclient.api.Api;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Api.getInstance();
    }
}
