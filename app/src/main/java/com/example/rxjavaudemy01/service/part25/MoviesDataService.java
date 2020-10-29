package com.example.rxjavaudemy01.service.part25;


import com.example.rxjavaudemy01.model.part25.MovieDBResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {


    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey);


}
