package com.github.abhijit.pinterestclient.ui.login.presenter;

import android.content.Intent;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;

public interface Contract {

    interface View extends BaseView<Presenter> {
        void showHomeScreen();
    }

    interface Presenter extends BasePresenter {
        void onLoginClick();
        void onOauthResponse(int requestCode, int resultCode, Intent data);
    }
}
