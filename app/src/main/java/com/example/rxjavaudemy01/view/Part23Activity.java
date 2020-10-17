package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.adapter.part23.ContactsAdapter;
import com.example.rxjavaudemy01.db.part21.ToDoListItem;
import com.example.rxjavaudemy01.db.part23.ContactsAppDatabase;
import com.example.rxjavaudemy01.db.part23.entity.Contact;
import com.example.rxjavaudemy01.model.part22.Movie;
import com.example.rxjavaudemy01.model.part22.MovieDBResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Part23Activity extends AppCompatActivity {

    /**
     * NOTE: WHY should we use RxJava with Room?
     * Profer thread handling
     * Easy to modify data streams
     * Cleaner,readable & maintainable code
     * **Get live updates from database**
     */


    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private long rowIdOfTheItemInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part23);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Contacts Manager ");

        recyclerView = findViewById(R.id.recycler_view_contacts);
        contactsAppDatabase = Room.databaseBuilder(getApplicationContext(), ContactsAppDatabase.class, "ContactDB")
                .allowMainThreadQueries().build();//allowMainThreadQueries() SHOULD NOT BE HERE

        contactArrayList.addAll(contactsAppDatabase.getContactDAO().getContacts());

        contactsAdapter = new ContactsAdapter(this, contactArrayList, Part23Activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        //getAllContactsData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContacts(false, null, -1);
            }


        });
    }

    private void getAllContactsData() {
/** NOTE:
 * when we are using consumers, to handle exceptions we need to add another consumer taking the type as Throwable */
/*
        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContacts()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Contact>>() {
                            @Override
                            public void accept(List<Contact> contacts) {
                                contactArrayList.clear();
                                contactArrayList.addAll(contacts);
                                contactsAdapter.notifyDataSetChanged();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {

                            }
                        }));*/

    }

    private void getAllContactsDataObservable() {
/** NOTE:
 * when we are using consumers, to handle exceptions we need to add another consumer taking the type as Throwable */
        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContactsObservable()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Contact>>() {
                            @Override
                            public void accept(List<Contact> contacts) {
                                contactArrayList.clear();
                                contactArrayList.addAll(contacts);
                                contactsAdapter.notifyDataSetChanged();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {

                            }
                        }));

    }


    public void addAndEditContacts(final boolean isUpdate, final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Part23Activity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {

                                    deleteContact(contact, position);
                                } else {

                                    dialogBox.cancel();

                                }

                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(newContact.getText().toString())) {
                    Toast.makeText(Part23Activity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && contact != null) {

                    updateContact(newContact.getText().toString(), contactEmail.getText().toString(), position);
                } else {

                    createContact(newContact.getText().toString(), contactEmail.getText().toString());
                }
            }
        });
    }

    private void deleteContact(Contact contact, int position) {

        //contactArrayList.remove(position);
        //contactsAppDatabase.getContactDAO().deleteContact(contact);
        //contactsAdapter.notifyDataSetChanged();



        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void run() throws Exception {
                        contactsAppDatabase.getContactDAO().deleteContact(contact);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            /**Why DisposableCompletableObserver? because we use CompositDisposable*/
                            @Override
                            public void onComplete() {
                                Toast.makeText(Part23Activity.this, "Contact Deleted Successfully" , Toast.LENGTH_SHORT).show();
                                contactArrayList.remove(position);//IT SHOULD NOT USE HERE !!!
                                contactsAdapter.notifyDataSetChanged();//IT SHOULD NOT USE HERE !!!
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));




    }

    private void updateContact(final String name, final String email, int position) {

        final Contact contact = contactArrayList.get(position);

        contact.setName(name);
        contact.setEmail(email);

        //contactsAppDatabase.getContactDAO().updateContact(contact);
        // contactArrayList.set(position, contact);
        //contactsAdapter.notifyDataSetChanged();



        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void run() throws Exception {
                        contactsAppDatabase.getContactDAO().updateContact(contact);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            /**Why DisposableCompletableObserver? because we use CompositDisposable*/
                            @Override
                            public void onComplete() {
                                Toast.makeText(Part23Activity.this, "Contact Updated Successfully" , Toast.LENGTH_SHORT).show();
                                contactArrayList.set(position, contact);//IT SHOULD NOT USE HERE !!!
                                contactsAdapter.notifyDataSetChanged();//IT SHOULD NOT USE HERE !!!
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));

    }

    private void createContact(final String name, final String email) {

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void run() throws Exception {
                        rowIdOfTheItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            /**Why DisposableCompletableObserver? because we use CompositDisposable*/
                            @Override
                            public void onComplete() {
                                Toast.makeText(Part23Activity.this, "Contact Added Successfully" + rowIdOfTheItemInserted, Toast.LENGTH_SHORT).show();
                                Contact contact = contactsAppDatabase.getContactDAO().getContact(rowIdOfTheItemInserted);//IT SHOULD NOT USE HERE !!!
                                contactArrayList.add(0, contact);//IT SHOULD NOT USE HERE !!!
                                contactsAdapter.notifyDataSetChanged();//IT SHOULD NOT USE HERE !!!
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));





       /* long id = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
        Contact contact = contactsAppDatabase.getContactDAO().getContact(id);
        if (contact != null) {
            contactArrayList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();
        }*/

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

}

