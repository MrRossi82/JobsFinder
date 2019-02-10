package com.alazz.jobsfinder.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;


import com.alazz.jobsfinder.R;
import com.alazz.jobsfinder.Utils.ActivityUtils;
import com.alazz.jobsfinder.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import static com.alazz.jobsfinder.Utils.Constant.DELAY_SPLASH_SCREEN;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        startHomeActivity();

    }


    private void startHomeActivity() {

        new Handler().postDelayed(() -> ActivityUtils.onNavigateToActivity(this, MainActivity.class, true), DELAY_SPLASH_SCREEN);
    }



}
