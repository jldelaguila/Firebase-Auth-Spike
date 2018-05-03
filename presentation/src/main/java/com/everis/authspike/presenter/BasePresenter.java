package com.everis.authspike.presenter;

/**
 * Created by everis on 25/04/18.
 */

public interface BasePresenter {

    void onPause();
    void onResume();
    void onStop();
    void onDestroy();

}
