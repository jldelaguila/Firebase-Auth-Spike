package com.everis.authspike.navigator;

import android.app.Activity;
import android.content.Intent;

import com.everis.authspike.view.activity.ContactsActivity;
import com.everis.authspike.view.activity.HomeActivity;
import com.everis.authspike.view.activity.RegisterActivity;

/**
 * Created by everis on 25/04/18.
 */

public class Navigator {

    public Navigator() {
    }

    public void navigateToHomeActivity(Activity activity){
        Intent homeIntent = new Intent(activity, HomeActivity.class);
        activity.startActivity(homeIntent);
        activity.finish();
    }

    public void navigateToRegisterActivity(Activity activity){
        Intent registerIntent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(registerIntent);
        activity.finish();
    }

    public void navigateToContactsActivity(Activity activity) {
        Intent contactsIntent = new Intent(activity, ContactsActivity.class);
        activity.startActivity(contactsIntent);
    }
}
