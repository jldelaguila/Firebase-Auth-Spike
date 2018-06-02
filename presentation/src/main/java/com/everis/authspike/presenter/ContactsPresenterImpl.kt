package com.everis.authspike.presenter

import android.content.Context
import android.util.Log

import com.everis.authspike.UIThread
import com.everis.authspike.view.views.ContactsView
import com.everis.data.network.local.SpikeContentProviderImpl
import com.everis.data.repository.ContentProviderDataRepository
import com.everis.data.repository.DatabaseDataRepository
import com.everis.domain.interactor.GetLocalContactsBatch
import com.everis.domain.interactor.SyncUserContacts
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser

import rx.Subscriber

/**
 * Created by everis on 3/05/18.
 */

class ContactsPresenterImpl(private val view: ContactsView, context: Context) : ContactsPresenter {

    private val batchUseCase: GetLocalContactsBatch = GetLocalContactsBatch(UIThread(), ContentProviderDataRepository(SpikeContentProviderImpl(context)))
    private val syncUserContactsUseCase: SyncUserContacts = SyncUserContacts(UIThread(), DatabaseDataRepository())


    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        batchUseCase.unsuscribe()
        syncUserContactsUseCase.unsuscribe()
    }

    override fun getLocalContactBatch() {
        batchUseCase.execute(BatchSubscriber())
    }


    override fun syncUserContactsByQuery(contacts: List<LocalContact>) {
        syncUserContactsUseCase.bindParams(contacts, true)
        syncUserContactsUseCase.execute(SyncSubscriber())
    }

    override fun syncUserContactsByRef(contacts: List<LocalContact>) {
        syncUserContactsUseCase.bindParams(contacts, false)
        syncUserContactsUseCase.execute(SyncSubscriber())
    }

    private inner class BatchSubscriber : Subscriber<List<LocalContact>>() {

        override fun onStart() {
            super.onStart()
            view.showLoading()
            Log.d(TAG, "Inicio de carga de contactos en BATCH")
        }

        override fun onCompleted() {
            view.hideLoading()
            Log.d(TAG, "**************fin**************fin")
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
        }

        override fun onNext(localContacts: List<LocalContact>) {
            Log.d(TAG, "Cantidad de contactos: " + localContacts.size)
            view.displayBatchContacts(localContacts)
        }
    }

    private inner class SyncSubscriber : Subscriber<P2PUser>() {

        override fun onStart() {
            super.onStart()
            view.showLoading()
            Log.d(TAG, "Inicio Sync")
        }

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
        }

        override fun onNext(p2PUser: P2PUser?) {
            if (p2PUser != null) {
                Log.d(TAG, "Contactos retornados de P2P: " + p2PUser.toString())
                view.setSync(p2PUser)
            }
            request(1)
        }
    }

    companion object {
        internal val TAG = ContactsPresenterImpl::class.java.simpleName
    }

}
