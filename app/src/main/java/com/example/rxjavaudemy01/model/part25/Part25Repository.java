package com.example.rxjavaudemy01.model.part25;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.service.part25.MoviesDataService;
import com.example.rxjavaudemy01.service.part25.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Part25Repository {
    private Application application;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private MutableLiveData<List<Movie>> moviesLiveData =new MutableLiveData<>();
    private ArrayList<Movie> movies;
    private Observable<MovieDBResponse> movieDBResponseObservable;


    public Part25Repository(Application application) {
        this.application = application;

    }


    public MutableLiveData<List<Movie>> getMoviesLiveData() {



        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesWithRx(application.getApplicationContext().getString(R.string.api_key));

        compositeDisposable.add(movieDBResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<MovieDBResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                        return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                    }
                })
                .filter(new Predicate<Movie>() {
                    @Override
                    public boolean test(Movie movie) throws Exception {
                        return movie.getVoteAverage()>7.0;
                    }
                })
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        movies.add(movie);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        moviesLiveData.postValue(movies);
                    }
                }));




        return moviesLiveData;
    }

    public void clear(){
        compositeDisposable.clear();
    }

}
