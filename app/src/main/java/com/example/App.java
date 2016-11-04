package com.example;

import com.example.utils.Foreground;

/**
 * Created by Vlad on 04.11.2016.
 */

public class App extends android.app.Application {
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
    }
}
