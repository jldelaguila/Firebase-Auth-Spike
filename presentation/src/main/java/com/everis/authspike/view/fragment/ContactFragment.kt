package com.everis.authspike.view.fragment


import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.everis.authspike.R
import com.everis.authspike.model.ContactModelDataMapper
import com.everis.authspike.presenter.ContactsPresenter
import com.everis.authspike.presenter.ContactsPresenterImpl
import com.everis.authspike.view.adapter.ContactsAdapter
import com.everis.authspike.view.view.ContactsView
import com.everis.domain.model.LocalContact
import com.everis.domain.model.P2PUser
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : Fragment(), ContactsView, PermissionListener {

    companion object {
        fun newInstance() : ContactFragment = ContactFragment()
    }

    private lateinit var presenter: ContactsPresenter

    private lateinit var adapter: ContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = ContactsPresenterImpl(this, context!!)
        adapter = ContactsAdapter()

        contacts_rv.layoutManager = LinearLayoutManager(context!!)
        contacts_rv!!.adapter = adapter
        contacts_rv!!.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))

    }

    override fun onResume() {
        super.onResume()
        Dexter.withActivity(activity).withPermission(Manifest.permission.READ_CONTACTS).withListener(this).onSameThread().check()
    }

    override fun showLoading() {
        contacts_progressbar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        contacts_progressbar!!.visibility = View.GONE
    }


    override fun setSync(user: P2PUser) {
        adapter.updateUser(user)
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        presenter.getLocalContactBatch()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {

    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {}

    override fun displayBatchContacts(contacts: List<LocalContact>) {
        adapter.contacts = ContactModelDataMapper.transform(contacts)
        adapter.notifyDataSetChanged()
        presenter.syncUserContactsByRef(contacts)
    }
}
