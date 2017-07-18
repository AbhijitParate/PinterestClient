package com.github.abhijit.pinterestclient.ui.home.fragment.search;

import com.github.abhijit.pinterestclient.pinterest.ClientInjector;
import com.github.abhijit.pinterestclient.pinterest.PinterestClient;
import com.github.abhijit.pinterestclient.scheduler.SchedulerInjector;
import com.github.abhijit.pinterestclient.scheduler.SchedulerProvider;
import com.github.abhijit.pinterestclient.ui.home.fragment.post.Contract;
import com.pinterest.android.pdk.PDKBoard;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

public class SearchPresenter implements Contract.Presenter {

    public static final String TAG = SearchPresenter.class.getSimpleName();

    private PinterestClient client;
    private SchedulerProvider schedulerProvider;

    private CompositeDisposable disposable;

    private com.github.abhijit.pinterestclient.ui.home.fragment.post.Contract.View view;

    public SearchPresenter(Contract.View view) {
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
//                                view.showBoard(pdkBoards);
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
}
