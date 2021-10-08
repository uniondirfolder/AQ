package com.nvv.myfilms.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nvv.myfilms.repository.GenreRepository;
import com.nvv.myfilms.repository.MoviesRepository;

import java.util.List;

public class MainActivityVM extends AndroidViewModel {
    public MainActivityVM(@NonNull Application application) {
        super(application);
        gr = new GenreRepository(application);
        mr = new MoviesRepository(application);
    }

    GenreRepository gr;
    MoviesRepository mr;

    public LiveData<List<Genre>> getGenres(){
        return gr.getGenres();
    }
    public LiveData<List<Movie>> getMovie(int genreId){
        return mr.getMovie(genreId);
    }
    public void addNewMovie(Movie movie){ mr.insertMovie(movie);}
    public void updateMovie(Movie movie){ mr.updateMovie(movie);}
    public void deleteMovie(Movie movie){ mr.deleteMovie(movie);}
}
