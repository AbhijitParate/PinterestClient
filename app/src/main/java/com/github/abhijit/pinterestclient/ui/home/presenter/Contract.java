package com.github.abhijit.pinterestclient.ui.home.presenter;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;

public interface Contract {

    interface View extends BaseView<Presenter> {
        void showLoginScreen();
    }

    interface Presenter extends BasePresenter {
        void logout();
    }
}
