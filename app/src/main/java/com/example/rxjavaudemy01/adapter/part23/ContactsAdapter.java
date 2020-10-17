package com.example.rxjavaudemy01.adapter.part23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.db.part23.entity.Contact;
import com.example.rxjavaudemy01.view.MainActivity;
import com.example.rxjavaudemy01.view.Part23Activity;

import java.util.ArrayList;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contactssList;
    private Part23Activity part23Activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView emil;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            emil = view.findViewById(R.id.email);

        }
    }


    public ContactsAdapter(Context context, ArrayList<Contact> contacts, Part23Activity part23Activity) {
        this.context = context;
        this.contactssList = contacts;
        this.part23Activity = part23Activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Contact contact = contactssList.get(position);

        holder.name.setText(contact.getName());
        holder.emil.setText(contact.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                part23Activity.addAndEditContacts(true, contact, position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return contactssList.size();
    }


}

