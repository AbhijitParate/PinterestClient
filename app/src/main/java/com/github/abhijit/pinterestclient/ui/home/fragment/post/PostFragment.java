package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.abhijit.pinterestclient.R;
import com.pinterest.android.pdk.PDKPin;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostFragment extends Fragment implements Contract.View {

    private static final String PIN_ID = "PIN_UID";
    String pinId;

    @BindView(R.id.pinImage)
    ImageView pinImage;

//    @BindView(R.id.pinTitle)
//    TextView pinTitle;

    @BindView(R.id.pinDescription)
    TextView pinDescription;

    @BindView(R.id.commentsCount)
    TextView commentsCount;

    Contract.Presenter presenter;

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
        pinId = getArguments().getString(PIN_ID);
        getActivity().setTitle("Pin");

        if (presenter == null) {
            presenter = new PostPresenter(this);
        }

        presenter.subscribe();
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(@StringRes int strId) {
        makeToast(getString(strId));
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPinDetails(PDKPin pin) {
        Glide.with(getActivity())
                .load(pin.getImageUrl())
                .asBitmap()
                .into(pinImage);
//        pinTitle.setText(pin.);
        pinDescription.setText(pin.getNote());
        commentsCount.setText(String.valueOf(pin.getCommentCount()));
    }

    @Override
    public String getPinId() {
        return pinId;
    }
}
