package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;
import com.pinterest.android.pdk.PDKBoard;

import java.util.List;

public interface Contract {

    interface View extends BaseView<Presenter> {
        void showLoginScreen();
        void showBoard(List<PDKBoard> boardList);
    }

    interface Presenter extends BasePresenter {
        void onLogoutClick();
    }
}
