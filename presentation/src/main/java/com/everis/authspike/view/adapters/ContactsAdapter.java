package com.everis.authspike.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.everis.authspike.R;
import com.everis.authspike.model.ContactModel;
import com.everis.domain.model.LocalContact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by everis on 3/05/18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.LocalContactViewHolder> {

    private List<ContactModel> contacts;

    public ContactsAdapter() {
        contacts = new ArrayList<>();
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public void updateUser(String phoneNumber) {
        for(ContactModel contactModel :contacts){
            if(contactModel.getNumber().equals(phoneNumber)){
                contactModel.setCloudUser(true);
            }
        }
        notifyDataSetChanged();
    }

    class LocalContactViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvName)
        TextView nameTv;

        @BindView(R.id.tvNumber)
        TextView numberTv;

        public LocalContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(ContactModel contact){
            nameTv.setText(String.format("%s %s", contact.getName(), contact.isCloudUser()));
            numberTv.setText(contact.getNumber());

        }
    }


    @NonNull
    @Override
    public LocalContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_local_contacts, parent, false);
        return new LocalContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalContactViewHolder holder, int position) {
        ContactModel currentContact = contacts.get(position);
        holder.bindView(currentContact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
