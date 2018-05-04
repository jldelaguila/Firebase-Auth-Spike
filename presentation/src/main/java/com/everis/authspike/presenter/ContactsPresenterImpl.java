package com.everis.authspike.presenter;

import android.content.Context;
import android.util.Log;

import com.everis.authspike.UIThread;
import com.everis.authspike.view.views.ContactsView;
import com.everis.data.network.local.SpikeContentProviderImpl;
import com.everis.data.repository.ContentProviderDataRepository;
import com.everis.data.repository.DatabaseDataRepository;
import com.everis.domain.interactor.GetLocalContactsBatch;
import com.everis.domain.interactor.SyncUserContacts;
import com.everis.domain.model.LocalContact;
import com.everis.domain.model.P2PUser;

import java.util.List;

import rx.Subscriber;

/**
 * Created by everis on 3/05/18.
 */

public class ContactsPresenterImpl implements ContactsPresenter {

    static final String TAG = ContactsPresenterImpl.class.getSimpleName();

    private GetLocalContactsBatch batchUseCase;
    private SyncUserContacts syncUserContactsUseCase;
    private ContactsView view;
    private Context context;


    public ContactsPresenterImpl(ContactsView view, Context context) {
        this.context = context;
        this.batchUseCase = new GetLocalContactsBatch(new UIThread(), new ContentProviderDataRepository(new SpikeContentProviderImpl(context)));
        this.syncUserContactsUseCase = new SyncUserContacts(new UIThread(), new DatabaseDataRepository());
        this.view = view;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        batchUseCase.unsuscribe();
        syncUserContactsUseCase.unsuscribe();
    }

    @Override
    public void getLocalContactBatch() {
        batchUseCase.execute(new BatchSubscriber());
    }

    @Override
    public void syncUserContactsByQuery(List<LocalContact> contacts) {
        syncUserContactsUseCase.bindParams(contacts, true);
        syncUserContactsUseCase.execute(new SyncSubscriber());
    }

    @Override
    public void syncUserContactsByRef(List<LocalContact> contacts) {
        syncUserContactsUseCase.bindParams(contacts, false);
        syncUserContactsUseCase.execute(new SyncSubscriber());
    }

    private class BatchSubscriber extends Subscriber<List<LocalContact>>{

        @Override
        public void onStart() {
            super.onStart();
            view.showLoading();
            Log.d(TAG, "Inicio de carga de contactos en BATCH");
        }

        @Override
        public void onCompleted() {
            view.hideLoading();
            Log.d(TAG, "**************fin**************fin");
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
        }

        @Override
        public void onNext(List<LocalContact> localContacts) {
            Log.d(TAG, "Cantidad de contactos: " + localContacts.size());
            view.displayBatchContacts(localContacts);
        }
    }

    private class SyncSubscriber extends Subscriber<P2PUser>{

        @Override
        public void onStart() {
            super.onStart();
            view.showLoading();
            Log.d(TAG, "Inicio Sync");
        }

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
        }

        @Override
        public void onNext(P2PUser p2PUser) {
            /*if(p2PUsers!=null){
                Log.d(TAG, "Contactos retornados de P2P: " + p2PUsers.size());
                //view.setSync(p2PUser.getPhoneNumber());
            }*/

            if(p2PUser!=null){
                Log.d(TAG, "Contactos retornados de P2P: " + p2PUser.toString());
                view.setSync(p2PUser);
            }
            request(1);
        }


    }

}
