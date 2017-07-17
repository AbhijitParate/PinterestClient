package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abhijit.pinterestclient.R;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostFragment extends Fragment implements Contract.View {

    private static final String PIN_ID = "PIN_UID";

    @BindView(R.id.pinTitle)
    TextView pinTitle;

    PostPresenter presenter;

    public static PostFragment newInstance(PDKPin pin) {
        Bundle args = new Bundle();
        args.putString(PIN_ID, pin.getUid());
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String pinId = getArguments().getString(PIN_ID);
        pinTitle.setText(pinId);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {

    }

    @Override
    public void makeToast(@StringRes int strId) {

    }

    @Override
    public void makeToast(String message) {

    }

    @Override
    public void showLoginScreen() {

    }

    @Override
    public void showBoard(List<PDKBoard> boardList) {

    }
}
