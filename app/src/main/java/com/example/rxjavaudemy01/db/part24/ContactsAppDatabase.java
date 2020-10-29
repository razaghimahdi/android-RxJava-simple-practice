package com.example.rxjavaudemy01.db.part24;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rxjavaudemy01.db.part24.entity.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();

}
