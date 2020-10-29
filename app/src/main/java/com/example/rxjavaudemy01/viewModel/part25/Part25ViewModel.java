package com.example.rxjavaudemy01.viewModel.part25;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rxjavaudemy01.model.part25.Movie;
import com.example.rxjavaudemy01.model.part25.Part25Repository;

import java.util.List;

public class Part25ViewModel extends AndroidViewModel {

    private Part25Repository part25Repository;


    public Part25ViewModel(@NonNull Application application) {
        super(application);
        part25Repository =new Part25Repository(application);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return part25Repository.getMoviesLiveData();
    }

    public void clear(){
        part25Repository.clear();
    }



}
