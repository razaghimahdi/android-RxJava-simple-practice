package com.example.rxjavaudemy01.service.part22;


import com.example.rxjavaudemy01.model.part22.MovieDBResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {

    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey);


}
