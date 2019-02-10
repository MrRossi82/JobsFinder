package com.alazz.jobsfinder.network.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {

    public static final int PROVIDER_GITHUB= 0;
    public static final int PROVIDER_SEARCH= 1;

    private String mTitleJob;
    private String mCompanyJob;
    private String mCreatedDateJob;
    private String mLocationJob;
    private String mDescriptionJob;
    private String mUrl;
    private String mCompanyUrlJob;
    private String mType;
    private String mCompanyJobLogo;
    private int mProvider;

    public Job(String mTitleJob, String mCompanyJob, String mCreatedDateJob, String mLocationJob, String mDescriptionJob, String mUrl, String mCompanyUrlJob, String mType, String mCompanyJobLogo, int mProvider) {
        this.mTitleJob = mTitleJob;
        this.mCompanyJob = mCompanyJob;
        this.mCreatedDateJob = mCreatedDateJob;
        this.mLocationJob = mLocationJob;
        this.mDescriptionJob = mDescriptionJob;
        this.mUrl = mUrl;
        this.mCompanyUrlJob = mCompanyUrlJob;
        this.mType = mType;
        this.mCompanyJobLogo = mCompanyJobLogo;
        this.mProvider = mProvider;
    }

    private Job(Parcel in) {
        mTitleJob = in.readString();
        mCompanyJob = in.readString();
        mCreatedDateJob = in.readString();
        mLocationJob = in.readString();
        mDescriptionJob = in.readString();
        mUrl = in.readString();
        mCompanyUrlJob = in.readString();
        mType = in.readString();
        mCompanyJobLogo = in.readString();
        mProvider = in.readInt();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getTitleJob() {
        return mTitleJob;
    }

    public void setTitleJob(String mTitleJob) {
        this.mTitleJob = mTitleJob;
    }

    public String getCompanyJob() {
        return mCompanyJob;
    }

    public void setCompanyJob(String mCompanyJob) {
        this.mCompanyJob = mCompanyJob;
    }

    public String getCreatedDateJob() {
        return mCreatedDateJob;
    }

    public void setCreatedDateJob(String mCreatedJob) {
        this.mCreatedDateJob = mCreatedJob;
    }

    public String getLocationJob() {
        return mLocationJob;
    }

    public void setLocationJob(String mLocationJob) {
        this.mLocationJob = mLocationJob;
    }

    public String getDescriptionJob() {
        return mDescriptionJob;
    }

    public void setDescriptionJob(String mDescriptionJob) {
        this.mDescriptionJob = mDescriptionJob;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getCompanyUrlJob() {
        return mCompanyUrlJob;
    }

    public void setCompanyUrlJob(String mCompanyUrlJob) {
        this.mCompanyUrlJob = mCompanyUrlJob;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getProvider() {
        return mProvider;
    }

    public void setProvider(int mProvider) {
        this.mProvider = mProvider;
    }

    public String getCompanyJobLogo() {
        return mCompanyJobLogo;
    }

    public void setCompanyJobLogo(String mCompanyJobLogo) {
        this.mCompanyJobLogo = mCompanyJobLogo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitleJob);
        dest.writeString(mCompanyJob);
        dest.writeString(mCreatedDateJob);
        dest.writeString(mLocationJob);
        dest.writeString(mDescriptionJob);
        dest.writeString(mUrl);
        dest.writeString(mCompanyUrlJob);
        dest.writeString(mType);
        dest.writeString(mCompanyJobLogo);
        dest.writeInt(mProvider);
    }
}
