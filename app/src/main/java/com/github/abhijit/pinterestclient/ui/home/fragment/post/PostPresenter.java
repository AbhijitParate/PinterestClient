package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import android.util.Log;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;
import com.pinterest.android.pdk.PDKBoard;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

/**
 * Created by abhij on 7/16/2017.
 */

public class PostPresenter implements Contract.Presenter {

    public static final String TAG = PostPresenter.class.getSimpleName();

    private PinterestClient client;
    private SchedulerProvider schedulerProvider;

    private CompositeDisposable disposable;

    private Contract.View view;

    public PostPresenter(Contract.View view) {
        this.view = view;
        client = ClientInjector.getClient(view.getContext());
        schedulerProvider = SchedulerInjector.getScheduler();

        disposable = new CompositeDisposable();

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getBoard();
    }

    private void getBoard() {
        disposable.add(
                client.getBoard()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableMaybeObserver<List<PDKBoard>>(){

                            @Override
                            public void onSuccess(@NonNull List<PDKBoard> pdkBoards) {
                                view.showBoard(pdkBoards);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.makeToast("Error : getBoard()");
                            }

                            @Override
                            public void onComplete() {
//                                view.makeToast("Posts retrieval successful");
                            }
                        })
        );
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onLogoutClick() {
        disposable.add(
                client.logout()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: ");
                                view.showLoginScreen();
                                view.makeToast("Logout complete");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }
}
