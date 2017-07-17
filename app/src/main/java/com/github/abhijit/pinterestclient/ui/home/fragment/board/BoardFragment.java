package com.github.abhijit.pinterestclient.ui.home.fragment.board;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.abhijit.pinterestclient.R;
import com.github.abhijit.pinterestclient.ui.home.FragmentInteraction;
import com.github.abhijit.pinterestclient.ui.login.LoginActivity;
import com.pinterest.android.pdk.PDKBoard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardFragment extends Fragment
        implements
        Contract.View,
        BoardAdapter.OnBoardClickListener {

    private static final int COLUMNS = 2;

    @BindView(R.id.boardRecyclerView)
    RecyclerView mRecyclerView;

    BoardAdapter adapter;

    Contract.Presenter presenter;

    FragmentInteraction interaction;

    public static BoardFragment newInstance() {

        Bundle args = new Bundle();

        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BoardFragment() {

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interaction = (FragmentInteraction) getActivity();

        if (presenter == null ){
            presenter = new BoardPresenter(this);
        }

        adapter = new BoardAdapter(getActivity(), this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
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
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showBoard(List<PDKBoard> boardList) {
        adapter.addBoard(boardList);
    }

    @Override
    public void onBoardClick(PDKBoard board) {
        makeToast(board.getName());
        interaction.switchToPins(board);
    }
}
