package com.github.abhijit.pinterestclient.ui.home.fragment.search;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abhijit.pinterestclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    private static final String PIN_ID = "PIN_UID";

    @BindView(R.id.pinTitle)
    TextView pinTitle;

    com.github.abhijit.pinterestclient.ui.home.fragment.post.Contract.Presenter presenter;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String pinId = getArguments().getString(PIN_ID);
        pinTitle.setText(pinId);
    }

//    @Override
//    public void setPresenter(Contract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void makeToast(@StringRes int strId) {
//        makeToast(getString(strId));
//    }
//
//    @Override
//    public void makeToast(String message) {
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showLoginScreen() {
//
//    }
//
//    @Override
//    public void showBoard(List<PDKBoard> boardList) {
//
//    }
}
