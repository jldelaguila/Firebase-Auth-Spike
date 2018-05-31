package com.everis.data.network.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

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

        return Observable.create(new Observable.OnSubscribe<List<LocalContactEntity>>() {
            @Override
            public void call(Subscriber<? super List<LocalContactEntity>> subscriber) {

                final String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone._ID
                };

                List<LocalContactEntity> contactList = new ArrayList<>();
                LocalContactEntity contact;

                ContentResolver contentResolver = context.getContentResolver();
                Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        null,
                        null,
                        null);

                if (phoneCursor != null) {

                    final int numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String number;
                    while (phoneCursor.moveToNext()) {
                        long contactId = phoneCursor.getLong(phoneCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                        number = phoneCursor.getString(numberIndex);
                        contact = new LocalContactEntity();
                        contact.setNumber(number);

                        final String[] contactProjection = new String[]{
                                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
                        };


                        String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] whereParameters = new String[]{String.valueOf(contactId), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};

                        Cursor contactCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                                contactProjection,
                                where,
                                whereParameters,
                                null);

                        if (contactCursor != null) {
                            final int givenNameIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
                            final int familyNameIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);

                            String givenName;
                            String familyName;

                            while (contactCursor.moveToNext()) {

                                givenName = contactCursor.getString(givenNameIndex);
                                familyName = contactCursor.getString(familyNameIndex);
                                contact.setName(givenName + " " + familyName);

                                contactList.add(contact);

                            }

                            contactCursor.close();

                        }

                    }

                    phoneCursor.close();

                }

                subscriber.onNext(contactList);
                subscriber.onCompleted();

            }
        });
    }
}
