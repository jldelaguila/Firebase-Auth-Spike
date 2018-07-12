package com.everis.authspike.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.everis.authspike.R
import com.everis.authspike.model.ContactModel
import com.everis.domain.model.P2PUser
import kotlinx.android.synthetic.main.row_local_contacts.view.*

import java.util.ArrayList


class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.LocalContactViewHolder>() {

    var contacts: ArrayList<ContactModel>? = null
    var filteredContacts: ArrayList<ContactModel>? = null
    var filteredList: Boolean = false

    var listener: OnClickListener? = null

    init {
        contacts = ArrayList()
        filteredContacts = ArrayList()
    }


    fun updateUser(p2PUser: P2PUser) {
        if (filteredList) {
            updateFilteredList(p2PUser)
        } else {
            updateNotFilteredList(p2PUser)
        }
        notifyDataSetChanged()
    }

    private fun updateFilteredList(p2PUser: P2PUser) {
        val localIndex = getLocalIndex(p2PUser)
        if (localIndex != -1) {

            val index = getFilteredIndex(p2PUser)
            val contact = contacts!![localIndex]
            contact.isCloudUser = p2PUser.enable
            contact.url = p2PUser.picture_url

            if (contact.isCloudUser) {

                if (index == -1) {
                    filteredContacts!!.add(contact)
                } else {
                    filteredContacts!![index] = contact
                }

            }
            else if (index != -1){
                filteredContacts!!.removeAt(index)
            }


        }
    }

    private fun getLocalIndex(p2PUser: P2PUser): Int {
        var index = -1

        for (i in 0 until contacts!!.size) {
            val tempContact = contacts!![i]
            if (tempContact.number == p2PUser.phoneNumber) {
                index = i
                break
            }
        }

        return index
    }

    private fun getFilteredIndex(p2PUser: P2PUser): Int {
        var index = -1

        for (i in 0 until filteredContacts!!.size) {
            val tempContact = filteredContacts!![i]
            if (tempContact.number == p2PUser.phoneNumber) {
                index = i
                break
            }
        }

        return index
    }

    private fun updateNotFilteredList(p2PUser: P2PUser) {
        for (contactModel in contacts!!) {
            if (contactModel.number == p2PUser.phoneNumber) {
                contactModel.isCloudUser = p2PUser.enable
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_local_contacts, parent, false)
        return LocalContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalContactViewHolder, position: Int) {
        val currentContact: ContactModel
        if (filteredList) {
            currentContact = filteredContacts!![position]
        } else {
            currentContact = contacts!![position]
        }
        holder.bindView(currentContact)
    }

    override fun getItemCount(): Int {
        if (filteredList)
            return filteredContacts!!.size
        else
            return contacts!!.size
    }

    interface OnClickListener {
        fun onClick(phone: String, url: String)
    }


    inner class LocalContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName = itemView.tvName!!
        private val tvNumber = itemView.tvNumber!!
        private val statusIv = itemView.status_image!!

        fun bindView(contact: ContactModel) {
            tvName.text = contact.name
            tvNumber.text = contact.number
            statusIv.setImageResource(if (contact.isCloudUser) R.drawable.user_circle else R.drawable.not_user_circle)
            itemView.setOnClickListener {
                if (listener != null) {
                    listener!!.onClick(contact.number, contact.url)
                }
            }
        }
    }

}
