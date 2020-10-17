package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.model.part07.Part07Student;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part07Activity extends AppCompatActivity {

    private final static String TAG = "MyTAG Part07Activity";
    private Observable<Part07Student> myObservable;
    private DisposableObserver<Part07Student> myObserver;


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part07);

        myObservable=Observable.create(new ObservableOnSubscribe<Part07Student>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Part07Student> emitter) throws Throwable {
                ArrayList<Part07Student> studentArrayList=getStudents();
                for (Part07Student student :
                        studentArrayList) {
                    emitter.onNext(student);
                }
                emitter.onComplete();
            }
        });



        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );


    }

    private DisposableObserver getObserver() {

        myObserver = new DisposableObserver<Part07Student>() {
            @Override
            public void onNext(Part07Student s) {


                Log.i(TAG, " onNext invoked with " + s.getEmail());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, " onError invoked");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " onComplete invoked");
            }
        };

        return myObserver;
    }


    private ArrayList<Part07Student> getStudents() {

        ArrayList<Part07Student> students = new ArrayList<>();

        Part07Student student1 = new Part07Student();
        student1.setName(" student 1");
        student1.setEmail(" student1@gmail.com ");
        student1.setAge(27);
        students.add(student1);

        Part07Student student2 = new Part07Student();
        student2.setName(" student 2");
        student2.setEmail(" student2@gmail.com ");
        student2.setAge(20);
        students.add(student2);

        Part07Student student3 = new Part07Student();
        student3.setName(" student 3");
        student3.setEmail(" student3@gmail.com ");
        student3.setAge(20);
        students.add(student3);

        Part07Student student4 = new Part07Student();
        student4.setName(" student 4");
        student4.setEmail(" student4@gmail.com ");
        student4.setAge(20);
        students.add(student4);

        Part07Student student5 = new Part07Student();
        student5.setName(" student 5");
        student5.setEmail(" student5@gmail.com ");
        student5.setAge(20);
        students.add(student5);

        return students;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}