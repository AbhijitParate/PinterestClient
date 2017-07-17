package com.github.abhijit.pinterestclient.pinterest;

import android.content.Intent;

import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface PinterestClient {
    void onOauthResponse(int requestCode, int resultCode, Intent data);
    Completable attemptLogin();
    Completable reAuthenticate();
    Maybe<List<PDKBoard>> getBoard();
    Maybe<List<PDKPin>> getPins();
    Maybe<List<PDKPin>> getPinsForBoard(String boardId);
    Completable logout();
}
