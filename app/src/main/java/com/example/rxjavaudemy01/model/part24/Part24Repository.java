package com.example.rxjavaudemy01.model.part24;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.rxjavaudemy01.db.part24.ContactsAppDatabase;
import com.example.rxjavaudemy01.db.part24.entity.Contact;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part24Repository {

    private Application application;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();
    private long rowIdOfTheItemInserted;

    public Part24Repository(Application application) {
        this.application = application;
        contactsAppDatabase = Room.databaseBuilder(application.getApplicationContext(), ContactsAppDatabase.class, "ContactDB").build();


        disposable.add(contactsAppDatabase.getContactDAO().getContacts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contact>>() {
                               @Override
                               public void accept(List<Contact> contacts) throws Exception {

                                   contactsLiveData.postValue(contacts);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                               }
                           }
                )
        );
    }


    public void createContact(final String name, final String email) {


        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                rowIdOfTheItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact added successfully " + rowIdOfTheItemInserted, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));


    }

    public void updateContact(/*final String name, final String email, int position*/final Contact contact) {

        /*final Contact contact = contactArrayList.get(position);

        contact.setName(name);
        contact.setEmail(email);*/


        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                contactsAppDatabase.getContactDAO().updateContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact updated successfully ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));


    }


    public void deleteContact(final Contact contact/*, int position*/) {


        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                contactsAppDatabase.getContactDAO().deleteContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact deleted successfully ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));
    }


    public MutableLiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

    /**NOTE: why i invoked the clear() method of disposable instead of dispose() method?
     * If you use dispose it ends entirely you cannot restart the data flow.
     * For this ContactManager app's scenario,Clear is the best choice because we may need to restart data streams again and again during the application
     * */
    public void clearr(){
        disposable.clear();
    }


}
