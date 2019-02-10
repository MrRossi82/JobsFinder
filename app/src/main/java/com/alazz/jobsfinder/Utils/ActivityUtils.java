package com.alazz.jobsfinder.Utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtils {

    public static void onNavigateToActivity(Activity activity, Class<?> tClass, boolean activityFinish) {

        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (activityFinish) {
            activity.finish();

        }

    }



        }



