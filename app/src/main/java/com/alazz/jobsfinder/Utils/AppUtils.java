package com.alazz.jobsfinder.Utils;


import static com.alazz.jobsfinder.Utils.Constant.GITHUB;
import static com.alazz.jobsfinder.Utils.Constant.SEARCH;

public class AppUtils {



    public static String getProviderName(int providerId){

        switch (providerId){

            case 0:
                return GITHUB;

            case 1:
                return SEARCH;

            default:
                return "All Jobs";

        }
    }



}


