package com.alazz.jobsfinder.base;

import androidx.annotation.StringRes;


@SuppressWarnings("ALL")
public interface BaseView<T> {

    void setPresenter(T presenter);

    void makeToast(@StringRes int stringId);

    void makeToast(String message);
}
