package com.example.rxjavaudemy01.db.part23;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rxjavaudemy01.db.part23.entity.Contact;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


@Dao
public interface ContactDAO {

    @Insert
    public  long addContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from contacts")
    public List<Contact> getContacts();
    /**
     * Why Flowable here(Only works on Rxjava2)?
     * when we dealing with large about of data,flowable type protect our data from backpressuer related issues
     * in this project we can use observable type
     *
    @Query("select * from contacts")
    Flowable<List<Contact>> getContacts();

    @Query("select * from contacts")
    Observable<List<Contact>> getContactsObservable();*/

    @Query("select * from contacts")
    Observable<List<Contact>> getContactsObservable();

    @Query("select * from contacts where contact_id ==:contactId")
    public Contact getContact(long contactId);


}
