package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.model.part07.Part07Student;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part09Activity extends AppCompatActivity {

    /**NOTE:
     if you send an item to map function it will return an item which will be emitted only once in the the downstream,
     When you want to convert item emitted to another type i prefer map operator.
     map and flatmap can both work. but map is much easier to handle.*/

    /**EXAMPLE: Consider a scenario where you have a network call to fetch movie details.
     * then you have another network call,
     * that gives you lists filming locations of those movies.
     * now the requirement is to create an Observable that emits movie with their filming location.
     * to achieve this, first you need to get the movies list then you have to make another separate network call for each movie to fetch location details.
     * This can be done easily using FlatMap operator.*/


    private final static String TAG = "MyTAG Part09Activity";
    private Observable<Part07Student> myObservable;
    private DisposableObserver<Part07Student> myObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part09);


        myObservable = Observable.create(new ObservableOnSubscribe<Part07Student>() {
            @Override
            public void subscribe(ObservableEmitter<Part07Student> emitter) throws Exception {
                ArrayList<Part07Student> studentArrayList = getStudents();
                for (Part07Student student : studentArrayList) {
                    emitter.onNext(student);
                }
                emitter.onComplete();
            }
        });

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        /*
                        .map(new Function<Part07Student, Part07Student>() {
                            @Override
                            public Part07Student apply(Part07Student student) throws Throwable {
                                student.setName(student.getName().toUpperCase());
                                return student;
                            }
                        })*/
                        .flatMap(new Function<Part07Student, Observable<Part07Student>>() {/**NOTE: transform the items emitted by an Observable into Observables,
                         then flatten the emissions from this into a single Observable.*/
                            @Override
                            public Observable<Part07Student> apply(Part07Student student) throws Throwable {

                                Part07Student student1 = new Part07Student();
                                student1.setName(student.getName());

                                Part07Student student2 = new Part07Student();
                                student2.setName("New member" + student.getName());

                                student.setName(student.getName().toUpperCase());
                                return Observable.just(student, student1, student2);
                            }
                        })
                        .subscribeWith(getObserver())

        );


    }

    private DisposableObserver getObserver() {

        myObserver = new DisposableObserver<Part07Student>() {
            @Override
            public void onNext(Part07Student s) {
                Log.i(TAG, s.getName());
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