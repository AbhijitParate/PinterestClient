package com.github.abhijit.pinterestclient.ui.home.fragment.pins;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.abhijit.pinterestclient.R;
import com.github.abhijit.pinterestclient.ui.home.FragmentInteraction;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinsFragment extends Fragment
        implements
        Contract.View,
        PinsAdapter.OnPinClickListener {

    private static final int COLUMNS = 2;

    @BindView(R.id.pinsRecyclerView)
    RecyclerView mRecyclerView;

    PinsAdapter adapter;

    Contract.Presenter presenter;

    FragmentInteraction interaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pins, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interaction = (FragmentInteraction) getActivity();

        if (presenter == null ) {
            presenter = new PinsPresenter(this);
        }

        adapter = new PinsAdapter(getActivity(), this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
        presenter.subscribe();
    }

    public static PinsFragment newInstance(PDKBoard pdkBoard) {
        Bundle args = new Bundle();
        args.putString("UID", pdkBoard.getUid());
        PinsFragment fragment = new PinsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
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
    public void showPins(List<PDKPin> pinList) {
        adapter.addBoard(pinList);
        makeToast(String.valueOf(pinList.size()));
    }

    @Override
    public void onPinClick(PDKPin pin) {
        interaction.switchToDescription(pin);
    }
}
