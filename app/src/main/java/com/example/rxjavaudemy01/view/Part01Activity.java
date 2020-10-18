package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rxjavaudemy01.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part01Activity extends AppCompatActivity {

    private String greeting="Hello From RXJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private String TAG="MyTAG Part01Activity";
    private TextView txtGreeting ;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part01);
        txtGreeting=findViewById(R.id.txtGreeting);


        myObservable=Observable.just(greeting)/**NOTE: this is not suitable for large list, like a list from server...*/
                .subscribeOn(Schedulers.io())/** NOTE: this means, from this point onwards data stream will execute on the io thread.*/
                .observeOn(AndroidSchedulers.mainThread());/** NOTE: this means, this data stream will move to main thread again.*/
       // myObservable.subscribeOn(Schedulers.io());
        //myObservable.observeOn(AndroidSchedulers.mainThread());

        myObserver=new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {/** NOTE: This onSubscribe method will be triggered when this observer subscribed to the observable.*/
                Log.i(TAG, "onSubscribe: "+d);
                disposable=d;
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext: "+s);
                txtGreeting.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: "+e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };

        myObservable.subscribe(myObserver);/**NOTE: this subscribe method of observable returns a disposable*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}