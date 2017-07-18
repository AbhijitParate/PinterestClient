package com.github.abhijit.pinterestclient.ui.home.presenter;

import android.util.Log;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;

public class HomePresenter implements Contract.Presenter {

    public static final String TAG = HomePresenter.class.getSimpleName();

    private PinterestClient client;
    private SchedulerProvider schedulerProvider;

    private CompositeDisposable disposable;

    private Contract.View view;

    public HomePresenter(Contract.View view) {
        this.view = view;
        client = ClientInjector.getClient(view.getContext());
        schedulerProvider = SchedulerInjector.getScheduler();

        disposable = new CompositeDisposable();

        view.setPresenter(this);
    }

    public void subscribe() {
        view.makeToast("Login successful");
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void logout() {
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
//                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }
}
