package com.everis.authspike.presenter;

import android.content.Context;
import android.util.Log;

import com.everis.authspike.UIThread;
import com.everis.authspike.view.views.ContactsView;
import com.everis.data.network.local.SpikeContentProviderImpl;
import com.everis.data.repository.ContentProviderDataRepository;
import com.everis.domain.interactor.GetLocalContactsBatch;
import com.everis.domain.model.LocalContact;

import java.util.List;

import rx.Subscriber;

/**
 * Created by everis on 3/05/18.
 */

public class ContactsPresenterImpl implements ContactsPresenter {

    static final String TAG = ContactsPresenterImpl.class.getSimpleName();

    private GetLocalContactsBatch batchUseCase;
    private ContactsView view;
    private Context context;


    public ContactsPresenterImpl(ContactsView view, Context context) {
        this.context = context;
        this.batchUseCase = new GetLocalContactsBatch(new UIThread(), new ContentProviderDataRepository(new SpikeContentProviderImpl(context)));
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
    }

    @Override
    public void getLocalContactBatch() {
        batchUseCase.execute(new BatchSubscriber());
    }

    @Override
    public void getLocalContactsIndiv() {

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

}
