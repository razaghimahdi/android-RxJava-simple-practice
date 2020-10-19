package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjavaudemy01.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part03Activity extends AppCompatActivity {

    private String greeting = "Hello From RXJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;
    private String TAG = "MyTAG Part03Activity";
    private TextView txtGreeting;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();/**NOTE: in one class you can have more than one observables (each observable can have many observers),
     so in case like that you will have many observers to dispose.
     When we have more than one observers we use CompositeDisposable.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part03);
        txtGreeting = findViewById(R.id.txtGreeting);


        myObservable = Observable.just(greeting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext: " + s);
                txtGreeting.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };
        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver)
        );
        /*
        compositeDisposable.add(myObserver);
        myObservable.subscribe(myObserver);*/


        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext myObserver2: " + s);
                Toast.makeText(Part03Activity.this, "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError myObserver2: " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete myObserver2: ");
            }
        };
        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver2)
        );
        /*compositeDisposable.add(myObserver2);
        myObservable.subscribe(myObserver2);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myObserver.dispose();
        //myObserver2.dispose();
        compositeDisposable.clear();
    }
}