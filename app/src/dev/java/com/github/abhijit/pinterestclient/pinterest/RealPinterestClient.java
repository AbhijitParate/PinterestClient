package com.github.abhijit.pinterestclient.pinterest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;
import com.pinterest.android.pdk.PDKResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.annotations.NonNull;


public class RealPinterestClient implements PinterestClient {

    private static final String APP_ID = "4911684310779574788";
    private static final String TAG = RealPinterestClient.class.getSimpleName();

    private PDKClient pdkClient;

    private static RealPinterestClient client;

    private Context context;

    public static RealPinterestClient getClient(Context context) {
        if (client == null) {
            client = new RealPinterestClient(context);
        }
        return client;
    }

    public RealPinterestClient(Context context) {
        this.context = context;
        this.pdkClient = PDKClient.configureInstance(context, APP_ID);
        this.pdkClient.onConnect(context);
    }

    @Override
    public void onOauthResponse(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onOauthResponse: ");
        this.pdkClient.onOauthResponse(requestCode, resultCode, data);
    }

    @Override
    public Completable attemptLogin() {
        Log.d(TAG, "attemptLogin: ");
        final List<String> scopes = new ArrayList<>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(@NonNull final CompletableEmitter e) throws Exception {
                        RealPinterestClient.this.pdkClient
                                .login(context, scopes, new PDKCallback(){
                            @Override
                            public void onSuccess(JSONObject response) {
//                                super.onSuccess(response);
                                Log.d(getClass().getName(), response.toString());
                                e.onComplete();
                            }

                            @Override
                            public void onFailure(PDKException exception) {
//                                super.onFailure(exception);
                                Log.e(getClass().getName(), exception.getDetailMessage());
                                e.onError(exception);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public Completable reAuthenticate() {
        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(@NonNull final CompletableEmitter e) throws Exception {
                        RealPinterestClient.this.pdkClient
                                .getMe(new PDKCallback(){
                                    @Override
                                    public void onSuccess(JSONObject response) {
                                        Log.d(getClass().getName(), response.toString());
                                        e.onComplete();
                                    }

                                    @Override
                                    public void onFailure(PDKException exception) {
                                        Log.e(getClass().getName(), exception.getDetailMessage());
                                        e.onError(exception);
                                    }
                                });
                    }
                }
        );
    }

    @Override
    public Maybe<List<PDKPin>> getPinsForBoard(final String boardId){
        final String fields = "id,link,creator,image,counts,note,created_at,board,metadata";

        return Maybe.create(new MaybeOnSubscribe<List<PDKPin>>() {
            @Override
            public void subscribe(@NonNull final MaybeEmitter<List<PDKPin>> e) throws Exception {
                pdkClient.getBoardPins(boardId, fields, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        super.onSuccess(response);
                        response.getPinList();
                        e.onSuccess(response.getPinList());
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        super.onFailure(exception);
                        e.onError(exception);
                    }
                });
            }
        });
    }

    @Override
    public Maybe<PDKPin> getPinDetails(final String pinId) {
        final String fields = "id,link,creator,image,counts,note,created_at,board,metadata";

        return Maybe.create(
                new MaybeOnSubscribe<PDKPin>() {
                    @Override
                    public void subscribe(@NonNull final MaybeEmitter<PDKPin> e) throws Exception {
                        pdkClient.getPin(pinId, fields, new PDKCallback(){
                            @Override
                            public void onSuccess(PDKResponse response) {
                                super.onSuccess(response);
                                e.onSuccess(response.getPin());
                                e.onComplete();
                            }

                            @Override
                            public void onFailure(PDKException exception) {
                                super.onFailure(exception);
                                e.onError(exception);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public Maybe<List<PDKBoard>> getBoard() {
        final String fields = "id,name,description,creator,image,counts,created_at";
        return Maybe.create(
                new MaybeOnSubscribe<List<PDKBoard>>() {
                    @Override
                    public void subscribe(@NonNull final MaybeEmitter<List<PDKBoard>> e) throws Exception {
                        pdkClient.getMyBoards(fields, new PDKCallback(){
                            @Override
                            public void onSuccess(PDKResponse response) {
                                super.onSuccess(response);
                                response.getBoardList();
                                e.onSuccess(response.getBoardList());
                                e.onComplete();
                            }

                            @Override
                            public void onFailure(PDKException exception) {
                                super.onFailure(exception);
                                e.onError(exception);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public Maybe<List<PDKPin>> getPins() {
        final String fields = "id,name,description,creator,image,counts,created_at";

        return Maybe.create(new MaybeOnSubscribe<List<PDKPin>>() {
            @Override
            public void subscribe(@NonNull final MaybeEmitter<List<PDKPin>> e) throws Exception {
                pdkClient.getMyPins(fields, new PDKCallback(){
                    @Override
                    public void onSuccess(PDKResponse response) {
                        super.onSuccess(response);
                        response.getPinList();
                        e.onSuccess(response.getPinList());
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        super.onFailure(exception);
                        e.onError(exception);
                    }
                });
            }
        });
    }

    @Override
    public Completable logout() {
        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(@NonNull final CompletableEmitter e) throws Exception {
                        pdkClient.logout();
                        e.onComplete();
                    }
                }
        );
    }
}
