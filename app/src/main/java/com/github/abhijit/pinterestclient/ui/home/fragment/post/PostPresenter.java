package com.github.abhijit.pinterestclient.ui.home.fragment.post;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;
import com.pinterest.android.pdk.PDKPin;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

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
        getPinDetails(view.getPinId());
    }

    private void getPinDetails(String pinId) {
        disposable.add(
                client.getPinDetails(pinId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableMaybeObserver<PDKPin>(){

                            @Override
                            public void onSuccess(@NonNull PDKPin pdkPin) {
                                view.showPinDetails(pdkPin);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.makeToast("Error : getPinDetails()");
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
}
