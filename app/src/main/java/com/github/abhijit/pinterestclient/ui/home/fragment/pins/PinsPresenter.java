package com.github.abhijit.pinterestclient.ui.home.fragment.pins;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

public class PinsPresenter implements Contract.Presenter {

    private static final String TAG = PinsPresenter.class.getSimpleName();

    private PinterestClient client;
    private SchedulerProvider schedulerProvider;

    private CompositeDisposable disposable;

    private Contract.View view;

    PinsPresenter(Contract.View view) {
        this.view = view;
        client = ClientInjector.getClient(view.getContext());
        schedulerProvider = SchedulerInjector.getScheduler();

        disposable = new CompositeDisposable();

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getPins(view.getBoardId());
    }

    private void getPins(String boardId) {
        disposable.add(
                client.getPinsForBoard(boardId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableMaybeObserver<List<PDKPin>>(){

                            @Override
                            public void onSuccess(@NonNull List<PDKPin> pins) {
                                view.showPins(pins);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.makeToast("Error while retrieving pins");
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
