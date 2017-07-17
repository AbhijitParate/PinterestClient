package com.github.abhijit.pinterestclient.base;

import android.content.Context;
import android.support.annotation.StringRes;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
    void makeToast(@StringRes int strId);
    void makeToast(String message);
    Context getContext();
}
