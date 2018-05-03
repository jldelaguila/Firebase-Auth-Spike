package com.everis.data.network.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.everis.data.model.LocalContactEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by everis on 3/05/18.
 */

public class SpikeContentProviderImpl implements SpikeContentProvider {

    private Context context;

    public SpikeContentProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<LocalContactEntity>> getLocalContatsBatch() {

        final String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.PHOTO_URI
        };

        return Observable.create(new Observable.OnSubscribe<List<LocalContactEntity>>() {
            @Override
            public void call(Subscriber<? super List<LocalContactEntity>> subscriber) {
                List<LocalContactEntity> contactList = new ArrayList<>();
                LocalContactEntity contact;

                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        PROJECTION,
                        null,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

                if (cursor != null) {
                    try {
                        final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        final int photoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI);

                        String name, number, photo;
                        while (cursor.moveToNext()) {
                            name = cursor.getString(nameIndex);
                            number = cursor.getString(numberIndex);
                            photo = cursor.getString(photoIndex);

                            contact = new LocalContactEntity();

                            contact.setName(name);
                            contact.setNumber(number);
                            contact.setUriPhoto(photo);
                            contactList.add(contact);
                        }
                    } catch (Exception e) {
                        e.getMessage();
                        subscriber.onError(new Throwable(e.getLocalizedMessage()));
                    } finally {
                        cursor.close();
                    }
                }

                subscriber.onNext(contactList);
                subscriber.onCompleted();

            }
        });
    }
}
