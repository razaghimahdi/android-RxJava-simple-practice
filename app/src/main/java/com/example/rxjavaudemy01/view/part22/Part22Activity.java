package com.example.rxjavaudemy01.view.part22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.adapter.part22.MovieAdapter;
import com.example.rxjavaudemy01.model.part22.Movie;
import com.example.rxjavaudemy01.model.part22.MovieDBResponse;
import com.example.rxjavaudemy01.service.part22.MoviesDataService;
import com.example.rxjavaudemy01.service.part22.RetrofitInstance;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Part22Activity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;
    private Call<MovieDBResponse> call;
    private Observable<MovieDBResponse> movieDBResponseObservable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part22);
        pb = findViewById(R.id.pb);


        getSupportActionBar().setTitle(" TMDb Popular Movies Today");


        getPopularMoviesRxFlatMap();


        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMoviesRxFlatMap();
            }
        });


    }

    public void getPopularMovies() {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        call = getMoviesDataService.getPopularMovies(this.getString(R.string.api_key));

        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {


                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    init();

                    pb.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                Log.i("TAG", "onFailure: " + t.getMessage());

                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getApplicationContext(), "Socket Time out.", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    public void getPopularMoviesRx() {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesWithRx(this.getString(R.string.api_key));

        compositeDisposable.add(
                movieDBResponseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MovieDBResponse>() {
                            @Override
                            public void onNext(@NonNull MovieDBResponse movieDBResponse) {

                                movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                                init();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("TAG", "onError: " + e.getMessage());
                                pb.setVisibility(View.GONE);
                            }

                            @Override
                            public void onComplete() {
                                pb.setVisibility(View.GONE);
                            }
                        }));

    }

    public void getPopularMoviesRxFlatMap() {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesWithRx(this.getString(R.string.api_key));

        compositeDisposable.add(
                movieDBResponseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<MovieDBResponse, ObservableSource<Movie>>() {
                            @Override
                            public ObservableSource<Movie> apply(MovieDBResponse movieDBResponse) throws Throwable {
                                return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(@NonNull Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                init();
                                pb.setVisibility(View.GONE);
                            }
                        })
        );

    }


    public void getPopularMoviesRxFlatMapWithFilter() {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesWithRx(this.getString(R.string.api_key));

        compositeDisposable.add(
                movieDBResponseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<MovieDBResponse, ObservableSource<Movie>>() {
                            @Override
                            public ObservableSource<Movie> apply(MovieDBResponse movieDBResponse) throws Throwable {
                                return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
                        .filter(new Predicate<Movie>() {
                            @Override
                            public boolean test(Movie movie) throws Throwable {
                                return movie.getVoteAverage()>7.0;
                            }
                        })
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(@NonNull Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                init();
                                pb.setVisibility(View.GONE);
                            }
                        })
        );

    }


    public void init() {


        recyclerView = findViewById(R.id.rvMovies);
        movieAdapter = new MovieAdapter(this, movies);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}