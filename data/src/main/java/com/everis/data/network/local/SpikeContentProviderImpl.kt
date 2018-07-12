package com.everis.data.network.local

import android.content.Context
import android.provider.ContactsContract

import com.everis.data.model.LocalContactEntity

import java.util.ArrayList

import rx.Observable

/**
 * Created by everis on 3/05/18.
 */

class SpikeContentProviderImpl(private val context: Context) : SpikeContentProvider {

    override fun getLocalContactsBatch(): Observable<List<LocalContactEntity>> {

        val projection = arrayOf(ContactsContract.CommonDataKinds.Email.CONTACT_ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Photo.PHOTO_URI)

        return Observable.create { subscriber ->
            val contactList = ArrayList<LocalContactEntity>()
            var contact: LocalContactEntity

            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, null, null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

            if (cursor != null) {
                try {
                    val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    var name: String
                    var number: String
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex)
                        number = cursor.getString(numberIndex)

                        contact = LocalContactEntity()

                        contact.name = name
                        contact.number = number
                        contactList.add(contact)
                    }
                } catch (e: Exception) {
                    e.message
                    subscriber.onError(Throwable(e.localizedMessage))
                } finally {
                    cursor.close()
                }
            }

            subscriber.onNext(contactList)
            subscriber.onCompleted()
        }
    }
}
