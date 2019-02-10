package com.alazz.jobsfinder.jobDetails;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alazz.jobsfinder.R;
import com.alazz.jobsfinder.Utils.ImageUtils;
import com.alazz.jobsfinder.Utils.TimeUtils;
import com.alazz.jobsfinder.Utils.WebEngine;
import com.alazz.jobsfinder.Utils.WebListener;
import com.alazz.jobsfinder.network.Model.Job;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alazz.jobsfinder.Utils.Constant.DATE_FORMAT_GITHUB;
import static com.alazz.jobsfinder.Utils.Constant.DATE_FORMAT_SEARCH;


@SuppressWarnings("WeakerAccess")
public class JobDetailsFragment extends Fragment {

    @BindView(R.id.webView_job_details)
    WebView JobDetailsWebView;

    @BindView(R.id.textView_job_details_title)
    TextView mTitleJobTextView;

    @BindView(R.id.textView_job_details_company)
    TextView mCompanyTextView;

    @BindView(R.id.textView_job_details_location)
    TextView mLocationJobTextView;

    @BindView(R.id.textView_job_details_created_at)
    TextView mCreateDataJobTextView;

    @BindView(R.id.imageView_job_details_company_logo)
    ImageView mCompanyLogoImageView;


    @BindView(R.id.imageView_job_details_bookmark)
    ImageView mBookMarkImageView;

    @BindView(R.id.button_job_details_apply_job)
    Button mApplyJobButton;


    @BindView(R.id.chip_textView_job_details_type)
    Chip mJobTypeChip;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_job_details, container, false);

        ButterKnife.bind(this, mView);

        initView();

    return mView;

    }


    private void initView() {

        mBookMarkImageView.setOnClickListener(v -> Toast.makeText(requireActivity(), "add Job to bookmark Page (For Testing )", Toast.LENGTH_SHORT).show());
        mApplyJobButton.setOnClickListener(v -> Toast.makeText(requireActivity(), "Apply Job  (For Testing )", Toast.LENGTH_SHORT).show());

        WebEngine webEngine = new WebEngine(JobDetailsWebView, this);
        webEngine.initWebView();
        webEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {
                JobDetailsWebView.setVisibility(View.GONE);
            }

            @Override
            public void onLoaded() {
                JobDetailsWebView.setVisibility(View.VISIBLE);
            }


        });

        Job mJob;

        if (getArguments() != null) {

            mJob =JobDetailsFragmentArgs.fromBundle(getArguments()).getJob();

            mTitleJobTextView.setText(mJob.getTitleJob());
            mCompanyTextView.setText(mJob.getCompanyJob());
            mLocationJobTextView.setText(mJob.getLocationJob());

            ImageUtils.onLoadImage(requireActivity(),mJob.getCompanyJobLogo(),mCompanyLogoImageView);

            if (mJob.getProvider()== Job.PROVIDER_GITHUB){

                webEngine.onLoadPageData(mJob.getDescriptionJob());
                mCreateDataJobTextView.setText(getString(R.string.created_at_job,TimeUtils.changeDateFormat(DATE_FORMAT_GITHUB,mJob.getCreatedDateJob())));


            } else {

                webEngine.onLoadPageURL(mJob.getUrl());
                mCreateDataJobTextView.setText(getString(R.string.created_at_job,TimeUtils.changeDateFormat(DATE_FORMAT_SEARCH,mJob.getCreatedDateJob())));

            }



        }

    }


}
