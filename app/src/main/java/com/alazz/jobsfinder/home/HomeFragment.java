package com.alazz.jobsfinder.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alazz.jobsfinder.R;
import com.alazz.jobsfinder.Utils.AppUtils;
import com.alazz.jobsfinder.network.Model.Filters;
import com.alazz.jobsfinder.network.Model.Job;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("WeakerAccess")
public class HomeFragment extends Fragment implements ListJobsContract.View,JobsAdapter.JobAdapterListener,FilterDialogFragment.FilterListener {

    private static final String TAG ="HomeFragment" ;
    @BindView(R.id.recyclerView_jobs_list)
    RecyclerView mListJobsRecyclerView;

    @BindView(R.id.view_empty_job_list)
    ConstraintLayout mEmptyView;

    @BindView(R.id.view_no_results)
    ConstraintLayout mEmptyResultView;

    @BindView(R.id.loading_progress)
    SpinKitView mProgressBar;

    @BindView(R.id.swipeRefreshLayout_jobs_list)
    SwipeRefreshLayout mJobListSwipeRefreshLayout;

    private JobsAdapter mJobsAdapter;

    private ListJobsContract.Presenter mPresenter;

    @BindView(R.id.cardView_filter)
    CardView mFilterBarCardView;


    @BindView(R.id.chip_filter_position)
    Chip mPositionFilterChip;
    @BindView(R.id.chip_filter_provider)
    Chip mProviderFilterChip;

    @BindView(R.id.chip_filter_location)
    Chip mLocationFilterChip;

    @BindView(R.id.textView_filter_bar_current_filter)
    TextView mDefaultFilterTextView;

    @BindView(R.id.imageView_filter_bar_rest_filter)
    ImageView mClearFilterImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mView);

        initView();



        return mView;

    }


    private void initView() {

        List<Job> mJobsList = new ArrayList<>();

        mJobListSwipeRefreshLayout.setOnRefreshListener(this::OnRefresh);

        mClearFilterImageView.setOnClickListener(v -> {onClearFilter();OnRefresh();});

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mListJobsRecyclerView.setLayoutManager(mLayoutManager);

        mListJobsRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        mListJobsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mJobsAdapter = new JobsAdapter(requireActivity(), mJobsList,this);

        mListJobsRecyclerView.setAdapter(mJobsAdapter);

        mFilterBarCardView.setOnClickListener(v -> showFilterDialog());

    }


    @Override
    public void setListJobs(List<Job> jobList) {

        mJobsAdapter.setJobsList(jobList);
    }

    @Override
    public void setListFilteredJobs(List<Job> jobList) {
        mJobsAdapter.setJobsList(jobList);
    }


    private void OnRefresh(){
        mPresenter.getJobsList();
        mJobListSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(ListJobsContract.Presenter presenter) {
       mPresenter=presenter;
    }

    private void initPresenter(){

        if (mPresenter == null) {
            mPresenter = new ListJobsPresenter(this,requireActivity());
        }

        mPresenter.subscribe();

    }

    private void showFilterDialog() {

        FilterDialogFragment mFilterDialog = new FilterDialogFragment(this);
        if (getFragmentManager() != null) {
            mFilterDialog.show(getFragmentManager(), TAG);
        }
        mFilterDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }


    @Override
    public void onFilter(Filters filters) {

        mPresenter.getListFilteredJobs(filters.getPosition(),filters.getLocation(),filters.getProvider());


        if (filters.hasPosition()&& filters.hasLocation() && filters.getProvider() !=-1){

            mPositionFilterChip.setVisibility(View.VISIBLE);
            mLocationFilterChip.setVisibility(View.VISIBLE);
            mProviderFilterChip.setVisibility(View.VISIBLE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mPositionFilterChip.setText(filters.getPosition());
            mLocationFilterChip.setText(filters.getLocation());
            mProviderFilterChip.setText(AppUtils.getProviderName(filters.getProvider()));



        } else if (filters.hasPosition()&& filters.hasLocation()){

            mPositionFilterChip.setVisibility(View.VISIBLE);
            mLocationFilterChip.setVisibility(View.VISIBLE);
            mProviderFilterChip.setVisibility(View.GONE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mPositionFilterChip.setText(filters.getPosition());
            mLocationFilterChip.setText(filters.getLocation());


        } else if (filters.hasPosition()&& filters.getProvider() !=-1){

            mPositionFilterChip.setVisibility(View.VISIBLE);
            mLocationFilterChip.setVisibility(View.GONE);
            mProviderFilterChip.setVisibility(View.VISIBLE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mPositionFilterChip.setText(filters.getPosition());
            mProviderFilterChip.setText(AppUtils.getProviderName(filters.getProvider()));

        } else if (filters.hasLocation()&& filters.getProvider() !=-1){

            mPositionFilterChip.setVisibility(View.GONE);
            mLocationFilterChip.setVisibility(View.VISIBLE);
            mProviderFilterChip.setVisibility(View.VISIBLE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mLocationFilterChip.setText(filters.getLocation());
            mProviderFilterChip.setText(AppUtils.getProviderName(filters.getProvider()));

        } else if (filters.hasPosition()){

            mPositionFilterChip.setVisibility(View.VISIBLE);
            mLocationFilterChip.setVisibility(View.GONE);
            mProviderFilterChip.setVisibility(View.GONE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mPositionFilterChip.setText(filters.getPosition());

        } else if (filters.hasLocation()){

            mLocationFilterChip.setVisibility(View.VISIBLE);
            mPositionFilterChip.setVisibility(View.GONE);
            mProviderFilterChip.setVisibility(View.GONE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mLocationFilterChip.setText(filters.getLocation());


        } else if (filters.getProvider() !=-1){

            mPositionFilterChip.setVisibility(View.GONE);
            mLocationFilterChip.setVisibility(View.GONE);
            mProviderFilterChip.setVisibility(View.VISIBLE);
            mDefaultFilterTextView.setVisibility(View.GONE);

            mProviderFilterChip.setText(AppUtils.getProviderName(filters.getProvider()));

        } else {

            mLocationFilterChip.setVisibility(View.VISIBLE);
            mPositionFilterChip.setVisibility(View.VISIBLE);
            mProviderFilterChip.setVisibility(View.VISIBLE);
            mDefaultFilterTextView.setVisibility(View.GONE);


            mPositionFilterChip.setText(getString(R.string.any_position));
            mLocationFilterChip.setText(getString(R.string.any_location));
            mProviderFilterChip.setText(getString(R.string.any_provider));

        }


        mPositionFilterChip.setEnabled(false);
        mProviderFilterChip.setEnabled(false);
        mLocationFilterChip.setEnabled(false);


    }


    @Override
    public void makeToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressIndicator(boolean show) {

        if (show){

            mProgressBar.setVisibility(View.VISIBLE);
            mListJobsRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mEmptyResultView.setVisibility(View.GONE);

        } else {

            mProgressBar.setVisibility(View.GONE);
            mListJobsRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            mEmptyResultView.setVisibility(View.GONE);


        }

    }

    @Override
    public void setEmptyView() {

        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyResultView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mListJobsRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void setResultEmptyView() {

        mEmptyResultView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mListJobsRecyclerView.setVisibility(View.GONE);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPresenter();

    }

    private void onClearFilter(){

        mPositionFilterChip.setVisibility(View.GONE);
        mLocationFilterChip.setVisibility(View.GONE);
        mProviderFilterChip.setVisibility(View.GONE);
        mDefaultFilterTextView.setVisibility(View.VISIBLE);

    }



    private HomeFragmentDirections.ActionHomeFragmentToJobDetailsFragment setJob(Job job) {

        HomeFragmentDirections.ActionHomeFragmentToJobDetailsFragment mActionGettingStartedFragmentToSignUpFragment
                =HomeFragmentDirections.actionHomeFragmentToJobDetailsFragment(job);


        mActionGettingStartedFragmentToSignUpFragment.setJob(job);



        return mActionGettingStartedFragmentToSignUpFragment;
    }

    @Override
    public void onJobSelected(Job job) {

        NavHostFragment.findNavController(this).navigate(setJob(job));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
