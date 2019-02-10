package com.alazz.jobsfinder.home;



import com.alazz.jobsfinder.base.BasePresenter;
import com.alazz.jobsfinder.base.BaseView;
import com.alazz.jobsfinder.network.Model.Job;

import java.util.List;


public interface ListJobsContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(ListJobsContract.Presenter presenter);

        void setListJobs(List<Job> jobList);

        void setListFilteredJobs(List<Job> jobList);

        void showProgressIndicator(boolean show);

        void setEmptyView();

        void setResultEmptyView();


    }

    interface Presenter extends BasePresenter {

        void getJobsList();

        void getListFilteredJobs(String position,String location,int provider);

        void subscribe();

        void unsubscribe();
    }
}
