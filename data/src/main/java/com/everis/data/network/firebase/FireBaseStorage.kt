package com.everis.data.network.firebase

import com.google.firebase.storage.StorageReference
import java.io.InputStream

interface FireBaseStorage {

    fun uploadFile(reference : StorageReference, stream : InputStream): rx.Observable<String>

}