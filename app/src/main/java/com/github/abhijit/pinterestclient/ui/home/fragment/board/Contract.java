package com.github.abhijit.pinterestclient.ui.home.fragment.board;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;
import com.pinterest.android.pdk.PDKBoard;

import java.util.List;

/**
 * Created by abhij on 7/16/2017.
 */

public interface Contract {

    interface View extends BaseView<Presenter> {
        void showLoginScreen();
        void showBoard(List<PDKBoard> boardList);
    }

    interface Presenter extends BasePresenter {
        void onLogoutClick();
    }
}
