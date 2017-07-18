package com.github.abhijit.pinterestclient.ui.home.fragment.pins;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private static final String BOARD_ID = "BOARD_UID";
    private static final String BOARD_NAME = "BOARD_NAME";
    private static final int COLUMNS = 2;

    String boardId;
    String boardName;

    @BindView(R.id.pinsRecyclerView)
    RecyclerView mRecyclerView;

    PinsAdapter adapter;

    Contract.Presenter presenter;

    FragmentInteraction interaction;

    public static PinsFragment newInstance(PDKBoard pdkBoard) {
        Bundle args = new Bundle();
        args.putString(BOARD_ID, pdkBoard.getUid());
        args.putString(BOARD_NAME, pdkBoard.getName());
        PinsFragment fragment = new PinsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        boardId = getArguments().getString(BOARD_ID);
        boardName = getArguments().getString(BOARD_NAME);
    }

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
        setRetainInstance(true);
        getActivity().setTitle(boardName);
        interaction = (FragmentInteraction) getActivity();

        if (presenter == null ) {
            presenter = new PinsPresenter(this);
        }

        adapter = new PinsAdapter(getActivity(), this);
        mRecyclerView.setAdapter(adapter);

//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

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
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void showPins(List<PDKPin> pinList) {
        adapter.addBoard(pinList);
    }

    @Override
    public void onPinClick(PDKPin pin) {
        interaction.switchToDescription(pin);
    }
}
