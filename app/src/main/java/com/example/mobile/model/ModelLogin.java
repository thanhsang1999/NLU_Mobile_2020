package com.example.mobile.model;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class ModelLogin {
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    public AccessToken LayTokenFacebook(){

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }
    public void HuyTokenTracker(){
        accessTokenTracker.stopTracking();
    }
}
