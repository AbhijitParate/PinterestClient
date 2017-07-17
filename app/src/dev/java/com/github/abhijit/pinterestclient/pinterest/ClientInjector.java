package com.github.abhijit.pinterestclient.pinterest;

import android.content.Context;

public class ClientInjector {

    public static PinterestClient getClient(Context context) {
        return new RealPinterestClient(context);
    }
}