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
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part02Activity extends AppCompatActivity {

    private String greeting="Hello From RXJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;/**NOTE: DisposableObserver is a much efficient way,
     it reduces the number of code line and it makes our works easier,
     especially if you have more than one observers in a class.*/
    private String TAG="MyTAG Part02Activity";
    private TextView txtGreeting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part02);
        txtGreeting=findViewById(R.id.txtGreeting);


        myObservable=Observable.just(greeting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        myObserver=new DisposableObserver<String>() {/**NOTE: we don't have a onSubscribe method with a observable instance.why is that? Actually we don't need it anymore.because now we can directly call DisposableObserver's dispose*/
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



        myObservable.subscribe(myObserver);

    }

    @Override
    protected void onDestroy() {/**NOTE: now in this onDestroy method we can directly call DisposableObserver's dispose method.*/
        super.onDestroy();
        myObserver.dispose();
    }
}