package com.github.abhijit.pinterestclient.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.abhijit.pinterestclient.R;
import com.github.abhijit.pinterestclient.ui.home.fragment.board.BoardFragment;
import com.github.abhijit.pinterestclient.ui.home.fragment.pins.PinsFragment;
import com.github.abhijit.pinterestclient.ui.home.fragment.post.PostFragment;
import com.github.abhijit.pinterestclient.ui.home.presenter.Contract;
import com.github.abhijit.pinterestclient.ui.home.presenter.HomePresenter;
import com.github.abhijit.pinterestclient.ui.login.LoginActivity;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements
        Contract.View,
        FragmentInteraction {

    FragmentManager manager;

    Contract.Presenter presenter;

    FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (presenter == null) {
            presenter = new HomePresenter(this);
        }

        manager = getFragmentManager();
        switchToBoardView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) HomeActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(HomeActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                presenter.logout();
                return true;
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchToBoardView() {
        BoardFragment boardFragment = BoardFragment.newInstance();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_enter, R.animator.slide_exit, R.animator.slide_enter_pop, R.animator.slide_exit_pop);
        ft.replace(R.id.fragment_container, boardFragment, "BoardFragment");
//        ft.addToBackStack("BoardFragment");
        ft.commit();
    }

    @Override
    public void switchToPinsView() {
        PinsFragment pinsFragment = new PinsFragment();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_enter, R.animator.slide_exit, R.animator.slide_enter_pop, R.animator.slide_exit_pop);
        ft.replace(R.id.fragment_container, pinsFragment, "PinsFragment");
        ft.addToBackStack("PinsFragment");
        ft.commit();
    }

    @Override
    public void switchToPins(PDKBoard pdkBoard) {
        PinsFragment pinsFragment = PinsFragment.newInstance(pdkBoard);
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_enter, R.animator.slide_exit, R.animator.slide_enter_pop, R.animator.slide_exit_pop);
        ft.replace(R.id.fragment_container, pinsFragment, "PinsFragment");
        ft.addToBackStack("PinsFragment");
        ft.commit();
    }

    @Override
    public void switchToDescription(PDKPin pin) {
        PostFragment postFragment = PostFragment.newInstance(pin);
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_enter, R.animator.slide_exit, R.animator.slide_enter_pop, R.animator.slide_exit_pop);
        ft.replace(R.id.fragment_container, postFragment, "PostFragment");
        ft.addToBackStack("PostFragment");
        ft.commit();
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
