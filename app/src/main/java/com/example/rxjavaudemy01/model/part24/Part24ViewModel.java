package com.example.rxjavaudemy01.model.part24;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rxjavaudemy01.db.part24.entity.Contact;

import java.util.List;

public class Part24ViewModel extends AndroidViewModel {

    private Part24Repository part24Repository;


    public Part24ViewModel(@NonNull Application application) {
        super(application);
        part24Repository =new Part24Repository(application);
    }


    public LiveData<List<Contact>> getAllContacts(){
        return part24Repository.getContactsLiveData();
    }

    public void createContact(String name, String email){
        part24Repository.createContact(name,email);
    }

    public void updateContact(Contact contact){
        part24Repository.updateContact(contact);
    }

    public void deleteContact(Contact contact){
        part24Repository.deleteContact(contact);
    }

    public void clearr( ){
        part24Repository.clearr();
    }




}
