package com.alazz.jobsfinder.home;


import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alazz.jobsfinder.network.ApiClient;
import com.alazz.jobsfinder.network.ApiService;
import com.alazz.jobsfinder.network.Model.Job;
import com.alazz.jobsfinder.network.Model.JobGithub;
import com.alazz.jobsfinder.network.Model.JobSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.alazz.jobsfinder.Utils.Constant.TEXT_EMPTY;
import static com.alazz.jobsfinder.Utils.Constant.URL_API_JOB_FILTER_SEARCH;
import static com.alazz.jobsfinder.Utils.Constant.URL_API_JOB_FILTER_SEARCH_BY_LOCATION;
import static com.alazz.jobsfinder.Utils.Constant.URL_API_JOB_SEARCH;

public class ListJobsPresenter implements ListJobsContract.Presenter{

    private final ListJobsContract.View mView;
    private final CompositeDisposable mCompositeDisposable;
    private final ApiService mApiService;
    private final String TAG="ListJobsPresenter";

    ListJobsPresenter(ListJobsContract.View view, Activity activity) {

        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
        mApiService = ApiClient.getClient().create(ApiService.class);

    }




    @Override
    public void getJobsList () {


        ///  Method for all get jobs from many provider like github % search ///

        mView.showProgressIndicator(true);

        ArrayList<Job> mJobList = new ArrayList<>();

      getJobListObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Job>, Observable<Job>>) Observable::fromIterable)
                .subscribe(new Observer<Job>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Subscribed");

                    }

                    @Override
                    public void onNext(Job job) {

                        ///// get job model from api and add this to list jobs then it is set list in view (adapter )   ///

                        mView.showProgressIndicator(false);

                        Log.d("Jobs List Title ",job.getTitleJob());

                        mJobList.add(job);
                        mView.setListJobs(mJobList);

                    }

                    @Override
                    public void onComplete() {

                        ///// after get jobs check is list jobs is empty so show Empty View   ///

                        if (mJobList.size()==0){
                            mView.setEmptyView();
                        }

                    }
                    @Override
                    public void onError(Throwable e) {

                        ///// If any error occurs, show message(Toast ) will appear to the user and show empty view or error view    ///

                        mView.setEmptyView();
                        mView.makeToast(e.getLocalizedMessage());

                    }

                });



    }

    @Override
    public void getListFilteredJobs(String position, String location, int provider) {


        ///  this Method for filter list jobs by position & location & provider  ///


        mView.showProgressIndicator(true);

        ArrayList<Job> mListFilteredJobs = new ArrayList<>();

        getListFilteredJobsObservable(position,location).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Job>, Observable<Job>>) Observable::fromIterable)
                .filter(job -> job.getProvider() == provider || provider ==-1) /// filter jobs by provider if user not select provider so show jobs from all provider  ///
                .subscribe(new Observer<Job>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Subscribed");

                    }

                    @Override
                    public void onNext(Job job) {

                        mView.showProgressIndicator(false);

                        Log.d("getListFilteredJobs ",job.getTitleJob());

                        mListFilteredJobs.add(job);
                        mView.setListFilteredJobs(mListFilteredJobs);


                    }

                    @Override
                    public void onComplete() {

                        if (mListFilteredJobs.size()==0){

                            mView.setResultEmptyView();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.setEmptyView();
                        mView.makeToast(e.getLocalizedMessage());
                    }

                });


    }

    private Observable<List<Job>> getJobListObservable(){


        ///  this Method using Rx Zip function combine the emissions of multiple Observables ,used for  call many api service and combination results    ///


        ArrayList<Job> mListFilteredJobs = new ArrayList<>();

        Observable<List<JobGithub>> mGetAllJobsGithub =mApiService.getAllJobsByGithub();
        Observable<List<JobSearch>> mGetAllJobsSearch =mApiService.getAllJobsBySearch(URL_API_JOB_SEARCH);

        return Observable.zip(mGetAllJobsGithub.subscribeOn(Schedulers.io()), mGetAllJobsSearch.subscribeOn(Schedulers.io()), (JobGithubList, JobSearchList) -> {


            ///  create list model github job and add all model to list    ///


            for (JobGithub JobGithub : JobGithubList) {

                Job mJob = new Job(JobGithub.getTitle(),JobGithub.getCompany(),JobGithub.getCreatedAt(),JobGithub.getLocation(),
                        JobGithub.getDescription(),JobGithub.getUrl(),String.valueOf(JobGithub.getCompanyUrl()),JobGithub.getType(),String.valueOf(JobGithub.getCompanyLogo()),Job.PROVIDER_GITHUB);

                mListFilteredJobs.add(mJob);
            }

            ///  create list model search job and add all model to list    ///

            for (JobSearch JobSearch : JobSearchList) {

                Job mJob = new Job(JobSearch.getPositionTitle(),JobSearch.getOrganizationName(),JobSearch.getStartDate(),JobSearch.getLocations().get(0),
                        JobSearch.getUrl(),TEXT_EMPTY,TEXT_EMPTY,TEXT_EMPTY,TEXT_EMPTY,Job.PROVIDER_SEARCH);

                mListFilteredJobs.add(mJob);

            }


            return mListFilteredJobs;
        });
    }


    private Observable<List<Job>> getListFilteredJobsObservable(String position, String location){

        String url;

         if (!TextUtils.isEmpty(position)){
             url=URL_API_JOB_FILTER_SEARCH_BY_LOCATION+position;
         } else if (!TextUtils.isEmpty(location)){
             url=URL_API_JOB_FILTER_SEARCH+location;

         } else {
             url=URL_API_JOB_SEARCH;
         }

        ArrayList<Job> mListFilteredJobs = new ArrayList<>();


        Observable<List<JobGithub>> mGetAllJobsGithub =mApiService.getFilteredJobsByGithub(position,location);
        Observable<List<JobSearch>> mGetAllJobsSearch =mApiService.getFilteredJobsBySearch(url);

        return Observable.zip(mGetAllJobsGithub.subscribeOn(Schedulers.io()), mGetAllJobsSearch.subscribeOn(Schedulers.io()), (JobGithubList, JobSearchList) -> {


            for (JobGithub JobGithub : JobGithubList) {

                Job mJob = new Job(JobGithub.getTitle(),JobGithub.getCompany(),JobGithub.getCreatedAt(),JobGithub.getLocation(),
                        JobGithub.getDescription(),JobGithub.getUrl(),String.valueOf(JobGithub.getCompanyUrl()),JobGithub.getType(),String.valueOf(JobGithub.getCompanyLogo()),Job.PROVIDER_GITHUB);

                mListFilteredJobs.add(mJob);
            }

            for (JobSearch JobSearch : JobSearchList) {

                Job mJob = new Job(JobSearch.getPositionTitle(),JobSearch.getOrganizationName(),JobSearch.getStartDate(),JobSearch.getLocations().get(0),
                        JobSearch.getUrl(),TEXT_EMPTY,TEXT_EMPTY,TEXT_EMPTY,TEXT_EMPTY,Job.PROVIDER_SEARCH);

                mListFilteredJobs.add(mJob);

            }


            return mListFilteredJobs;
        });
    }



    @Override
    public void subscribe() {
        getJobsList(); // this method run when calling onActivityCreated in Home Fragment  ///

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear(); // clear CompositeDisposable  when calling onDestroy in Home Fragment  ///

    }


}