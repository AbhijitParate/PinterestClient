package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import com.github.abhijit.pinterestclient.base.BasePresenter;
import com.github.abhijit.pinterestclient.base.BaseView;
import com.pinterest.android.pdk.PDKPin;

public interface Contract {

    interface View extends BaseView<Presenter> {
        void showPinDetails(PDKPin pin);
        String getPinId();
    }

    interface Presenter extends BasePresenter {

    }
}
