package com.github.abhijit.pinterestclient.pinterest;

public class ClientInjector {

    public static PinterestClient getAuthSource() {
        return new MockPinterestClient();
    }
}