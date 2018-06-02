package com.everis.authspike.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.everis.authspike.R
import com.everis.authspike.model.ContactModel
import com.everis.domain.model.P2PUser
import kotlinx.android.synthetic.main.row_local_contacts.view.*

import java.util.ArrayList


/**
 * Created by everis on 3/05/18.
 */

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.LocalContactViewHolder>() {

    var contacts: List<ContactModel>? = null

    init {
        contacts = ArrayList()
    }


    fun updateUser(p2PUser: P2PUser) {
        for (contactModel in contacts!!) {
            if (contactModel.number == p2PUser.phoneNumber) {
                contactModel.isCloudUser = p2PUser.isEnable
            }
        }
        notifyDataSetChanged()
    }

    inner class LocalContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tvName!!
        val tvNumber = itemView.tvNumber!!
        val statusIv = itemView.status_image!!


        fun bindView(contact: ContactModel) {
            tvName.text = contact.name
            tvNumber.text = contact.number
            statusIv.setImageResource(if (contact.isCloudUser) R.drawable.user_circle else R.drawable.not_user_circle)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_local_contacts, parent, false)
        return LocalContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalContactViewHolder, position: Int) {
        val currentContact = contacts!![position]
        holder.bindView(currentContact)
    }

    override fun getItemCount(): Int {
        return contacts!!.size
    }
}
