package com.github.abhijit.pinterestclient.ui.home;

import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

/**
 * Created by abhij on 7/17/2017.
 */

public interface FragmentInteraction {
    void switchToBoardView();
    void switchToPinsView();
    void switchToPins(PDKBoard pdkBoard);
    void switchToDescription(PDKPin pin);
}
