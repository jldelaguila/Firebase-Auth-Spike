package com.everis.authspike.view.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.everis.authspike.R
import com.everis.authspike.model.ContactModelDataMapper
import com.everis.authspike.presenter.ContactsPresenter
import com.everis.authspike.presenter.ContactsPresenterImpl
import com.everis.authspike.view.adapters.ContactsAdapter
import com.everis.authspike.view.views.ContactsView
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsActivity : BaseActivity(), ContactsView, PermissionListener {

    internal var presenter: ContactsPresenter? = null

    private var adapter: ContactsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        presenter = ContactsPresenterImpl(this, this)
        adapter = ContactsAdapter()
        val layoutManager = LinearLayoutManager(this)
        contacts_rv.layoutManager = layoutManager
        contacts_rv!!.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        contacts_rv!!.addItemDecoration(itemDecoration)
    }

    override fun onResume() {
        super.onResume()
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_CONTACTS).withListener(this).onSameThread().check()
    }

    override fun showLoading() {
        this.contacts_progressbar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        this.contacts_progressbar!!.visibility = View.GONE
    }


    override fun setSync(user: P2PUser) {
        adapter!!.updateUser(user)
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        presenter!!.getLocalContactBatch()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {

    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {}

    override fun displayBatchContacts(contacts: List<LocalContact>) {
        adapter!!.contacts = ContactModelDataMapper.transform(contacts)
        presenter!!.syncUserContactsByRef(contacts)
    }
}
