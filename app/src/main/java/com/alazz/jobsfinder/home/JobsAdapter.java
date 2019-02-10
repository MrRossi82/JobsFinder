package com.alazz.jobsfinder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alazz.jobsfinder.R;
import com.alazz.jobsfinder.Utils.ImageUtils;
import com.alazz.jobsfinder.Utils.TimeUtils;
import com.alazz.jobsfinder.network.Model.Job;
import com.google.android.material.chip.Chip;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alazz.jobsfinder.Utils.Constant.DATE_FORMAT_GITHUB;
import static com.alazz.jobsfinder.Utils.Constant.DATE_FORMAT_SEARCH;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {

    private final Context mContext;
    private List<Job> mJobsList;
    private final JobAdapterListener mJobAdapterListener;

    JobsAdapter(Context context,List<Job> jobList ,JobAdapterListener jobAdapterListener) {
        mContext = context;
        mJobsList=jobList;
        mJobAdapterListener = jobAdapterListener;
    }

     class JobViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_item_job_title)
        TextView mTitleJobTextView;

        @BindView(R.id.textView_item_job_company)
        TextView mCompanyTextView;

        @BindView(R.id.textView_item_job_location)
        TextView mLocationJobTextView;

        @BindView(R.id.textView_item_job_created_at)
        TextView mCreateDataJobTextView;

        @BindView(R.id.imageView_item_job_company_logo)
        ImageView mCompanyLogoImageView;

        @BindView(R.id.chip_textView_item_job_type)
        Chip mJobTypeChip;

       JobViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(view1 -> mJobAdapterListener.onJobSelected(mJobsList.get(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jobs, parent, false);

        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, final int position) {

        final Job mJob = mJobsList.get(position);

        if (mJob !=null){

            ImageUtils.onLoadImage(mContext,mJob.getCompanyJobLogo(),holder.mCompanyLogoImageView);

            holder.mTitleJobTextView.setText(mJob.getTitleJob());
            holder.mCompanyTextView.setText(mJob.getCompanyJob());
            holder.mLocationJobTextView.setText(mJob.getLocationJob());

            if (mJob.getProvider()==Job.PROVIDER_GITHUB){
                holder.mCreateDataJobTextView.setText(mContext.getResources().getString(R.string.created_at_job,TimeUtils.changeDateFormat(DATE_FORMAT_GITHUB,mJob.getCreatedDateJob())));
            } else {
                holder.mCreateDataJobTextView.setText(mContext.getResources().getString(R.string.created_at_job,TimeUtils.changeDateFormat(DATE_FORMAT_SEARCH,mJob.getCreatedDateJob())));
            }
        }

    }

    @Override
    public int getItemCount() {

        if (mJobsList.size()!=0){
            return mJobsList.size();

        } else {

           return 0;
        }

    }

    void setJobsList(List<Job> jobsList) {
        mJobsList = jobsList;
        notifyDataSetChanged();
    }

    public interface JobAdapterListener {
        void onJobSelected(Job job);
    }


}