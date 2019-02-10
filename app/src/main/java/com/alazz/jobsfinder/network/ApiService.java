package com.alazz.jobsfinder.network;

import com.alazz.jobsfinder.network.Model.JobGithub;
import com.alazz.jobsfinder.network.Model.JobSearch;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiService {

    @GET("positions.json")
    Observable<List<JobGithub>> getAllJobsByGithub();

    @GET
    Observable<List<JobSearch>> getAllJobsBySearch(@Url String jobsUrl);

    @GET("positions.json")
    Observable<List<JobGithub>> getFilteredJobsByGithub(@Query("description") String description,
                                        @Query("location") String location);

    @GET
    Observable<List<JobSearch>> getFilteredJobsBySearch(@Url String jobsUrl);


}