package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rxjavaudemy01.R;

import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part04Activity extends AppCompatActivity {

    private String[] greetings = {"Hello A","Hello B"};
    private Observable<String[]> myObservable;
    private DisposableObserver<String[]> myObserver;
     private String TAG = "MyTAG Part04Activity";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part04);


        myObservable = Observable.just(greetings);/**NOTE: The Just operator converts an item into an observable that emits that item,
         it means Just operator only emits the object once,
         it does not iterate through each item of the array.
         So Just operator does not work well for an array */

        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );

    }


    private DisposableObserver getObserver(){


        myObserver = new DisposableObserver<String[]>() {
            @Override
            public void onNext(@NonNull String[] s) {
                Log.i(TAG, "onNext: " + Arrays.toString(s));
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
        return myObserver;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}