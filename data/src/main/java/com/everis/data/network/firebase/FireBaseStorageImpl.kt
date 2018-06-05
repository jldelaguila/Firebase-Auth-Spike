package com.everis.data.network.firebase

import com.google.firebase.storage.StorageReference
import rx.Observable
import java.io.InputStream


class FireBaseStorageImpl : FireBaseStorage {

    override fun uploadFile(reference: StorageReference, stream : InputStream): Observable<String> {
        return Observable.create {subscriber ->
            subscriber.onStart()
            reference.putStream(stream).addOnCompleteListener({
                subscriber.onNext(it.result.downloadUrl!!.toString())
            }).addOnFailureListener {
                subscriber.onError(it)
            }

        }
    }
}