package com.alazz.jobsfinder.network.Model;

import android.text.TextUtils;


public class Filters {

    private String mPosition = null;
    private String mLocation = null;
    private int mProvider = 0;

    public Filters() {

    }

    public boolean hasPosition() {
      return !(TextUtils.isEmpty(mPosition));
    }

    public boolean hasLocation() {
        return !(TextUtils.isEmpty(mLocation));
    }


    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public int getProvider() {
        return mProvider;
    }

    public void setProvider(int mProvider) {
        this.mProvider = mProvider;
    }
}
