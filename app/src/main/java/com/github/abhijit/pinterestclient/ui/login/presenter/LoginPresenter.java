package com.github.abhijit.pinterestclient.ui.login.presenter;

import android.content.Intent;
import android.util.Log;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;

public class LoginPresenter implements Contract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private PinterestClient client;
    private SchedulerProvider schedulerProvider;

    private CompositeDisposable disposable;

    private Contract.View view;

    public LoginPresenter(Contract.View view) {
        this.view = view;
        client = ClientInjector.getClient(view.getContext());
        schedulerProvider = SchedulerInjector.getScheduler();

        disposable = new CompositeDisposable();

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        reAuthenticateUser();
    }

    private void reAuthenticateUser() {
        disposable.add(
                client.reAuthenticate()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver(){

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        view.showHomeScreen();
                        view.makeToast("Login complete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        view.makeToast("");
                    }
                })
        );
    }

    @Override
    public void unsubscribe() {
//        disposable.clear();
    }

    @Override
    public void onLoginClick() {
        disposable.add(
        client.attemptLogin()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver(){

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        view.showHomeScreen();
                        view.makeToast("Login complete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.makeToast(e.getMessage());
                    }
                })
        );
    }

    @Override
    public void onOauthResponse(int requestCode, int resultCode, Intent data) {
        client.onOauthResponse(requestCode, resultCode, data);
    }
}
