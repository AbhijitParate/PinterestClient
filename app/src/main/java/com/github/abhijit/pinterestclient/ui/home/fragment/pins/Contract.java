package com.github.abhijit.pinterestclient.ui.home.fragment.pins;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

/**
 * Created by abhij on 7/16/2017.
 */

public interface Contract {

    interface View extends BaseView<Presenter> {
        String getBoardId();

        void showPins(List<PDKPin> pinList);
    }

    interface Presenter extends BasePresenter {

    }
}
