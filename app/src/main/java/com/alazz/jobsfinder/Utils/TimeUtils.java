package com.alazz.jobsfinder.Utils;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.alazz.jobsfinder.Utils.Constant.DATE_FORMAT;

public class TimeUtils {


    public static String changeDateFormat(String currentFormat, String dateString){
        String result="";
        if (TextUtils.isEmpty(dateString)){
            return result;
        }
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date=null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }

}
