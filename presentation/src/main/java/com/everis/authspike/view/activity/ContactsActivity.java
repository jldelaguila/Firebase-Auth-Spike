package com.everis.authspike.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.everis.authspike.R;
import com.everis.authspike.presenter.ContactsPresenter;
import com.everis.authspike.presenter.ContactsPresenterImpl;
import com.everis.authspike.view.adapters.ContactsAdapter;
import com.everis.authspike.view.views.ContactsView;
import com.everis.domain.model.LocalContact;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends BaseActivity implements ContactsView, PermissionListener {

    ContactsPresenter presenter;

    @BindView(R.id.contacts_rv)
    RecyclerView contactsRecycler;

    @BindView(R.id.contacts_progressbar)
    ProgressBar progressBar;

    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        presenter = new ContactsPresenterImpl(this, this);
        adapter = new ContactsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        contactsRecycler.setLayoutManager(layoutManager);
        contactsRecycler.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        contactsRecycler.addItemDecoration(itemDecoration);
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_CONTACTS).withListener(this).onSameThread().check();
    }


    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayBatchContacts(List<LocalContact> contacts) {
        adapter.setContacts(contacts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        presenter.getLocalContactBatch();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

    }
}
