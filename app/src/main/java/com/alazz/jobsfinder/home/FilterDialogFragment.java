package com.alazz.jobsfinder.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alazz.jobsfinder.R;
import com.alazz.jobsfinder.network.Model.Filters;
import com.alazz.jobsfinder.network.Model.Job;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



@SuppressWarnings("WeakerAccess")
public class FilterDialogFragment extends DialogFragment implements PlaceSelectionListener {

    private View mRootView;

    @BindView(R.id.editText_filter_dialog_position)
    TextInputEditText mPositionTextInputEditText;

    @BindView(R.id.chipGroup_filter_dialog_provider)
    ChipGroup mProviderChipGroup;

    private int mProviderText=0;
    private String mLocationText="";

    PlaceAutocompleteFragment mPlaceAutocompleteFragment;

    public FilterDialogFragment() {

    }

    @SuppressLint("ValidFragment")
    FilterDialogFragment(FilterListener mFilterListener) {
        this.mFilterListener = mFilterListener;
    }

    @Override
    public void onPlaceSelected(Place place) {
        mLocationText=place.getName().toString();

    }

    @Override
    public void onError(Status status) {

    }

    interface FilterListener {

        void onFilter(Filters filters);

    }



    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.dialog_fragment_filter, container, false);

        ButterKnife.bind(this, mRootView);

        initView();

        return mRootView;
    }


    public void initView(){

        mPlaceAutocompleteFragment = (PlaceAutocompleteFragment)requireActivity().getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        mPlaceAutocompleteFragment.setOnPlaceSelectedListener(this);

        mProviderChipGroup.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {

                case R.id.chip_filter_dialog_github:
                    mProviderText=Job.PROVIDER_GITHUB;
                    break;
                case R.id.chip_filter_dialog_search:
                    mProviderText=Job.PROVIDER_SEARCH;
                    break;
            }
        });

    }

    private String getSelectedPosition() {

        if (!TextUtils.isEmpty(mPositionTextInputEditText.getText())){
            return mPositionTextInputEditText.getText().toString();
        } else{
            return "";
        }

    }


    private int getSelectedProvider() {

        if (mProviderChipGroup.getCheckedChipId()==-1){
            return -1;
        } else{
            return mProviderText;
        }

    }


    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {

            filters.setPosition(getSelectedPosition());
            filters.setLocation(mLocationText);
            filters.setProvider(getSelectedProvider());

        }

        return filters;
    }



    @OnClick(R.id.button_filter_dialog_filter)
    public void onFilterClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();

    }

    @OnClick(R.id.button_filter_dialog_cancel)
    public void onCancelClicked() {
        dismiss();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getDialog().getWindow()).setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPlaceAutocompleteFragment != null)
            requireActivity().getFragmentManager().beginTransaction().remove(mPlaceAutocompleteFragment).commit();
    }


}
