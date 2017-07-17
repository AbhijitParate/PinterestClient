package com.github.abhijit.pinterestclient.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.abhijit.pinterestclient.R;
import com.github.abhijit.pinterestclient.ui.home.fragment.board.BoardFragment;
import com.github.abhijit.pinterestclient.ui.home.fragment.pins.PinsFragment;
import com.github.abhijit.pinterestclient.ui.home.fragment.post.PostFragment;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements FragmentInteraction {

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

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
//            case R.id.action_logout:
//                presenter.onLogoutClick();
//                return true;
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchToBoardView() {
        BoardFragment boardFragment = new BoardFragment();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
        ft.replace(R.id.fragment_container, boardFragment, "BoardFragment");
        ft.addToBackStack("BoardFragment");
        ft.commit();
    }

    @Override
    public void switchToPinsView() {
        PinsFragment pinsFragment = new PinsFragment();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
        ft.replace(R.id.fragment_container, pinsFragment, "PinsFragment");
        ft.addToBackStack("PinsFragment");
        ft.commit();
    }

    @Override
    public void switchToPins(PDKBoard pdkBoard) {
        PinsFragment pinsFragment = PinsFragment.newInstance(pdkBoard);
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
        ft.replace(R.id.fragment_container, pinsFragment, "PinsFragment");
        ft.addToBackStack("PinsFragment");
        ft.commit();
    }

    @Override
    public void switchToDescription(PDKPin pin) {
        PostFragment postFragment = PostFragment.newInstance(pin);
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
        ft.replace(R.id.fragment_container, postFragment, "PostFragment");
        ft.addToBackStack("PostFragment");
        ft.commit();
    }
}