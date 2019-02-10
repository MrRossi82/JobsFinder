package com.alazz.jobsfinder.base;

import android.app.Application;

public class JobsFinderApp extends Application {

    private static JobsFinderApp mJobsFinderApp;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static JobsFinderApp getInstance()
    {
        if (mJobsFinderApp== null) {
            synchronized(JobsFinderApp.class) {
                if (mJobsFinderApp == null)
                    mJobsFinderApp = new JobsFinderApp();
            }
        }
        return mJobsFinderApp;
    }


}
