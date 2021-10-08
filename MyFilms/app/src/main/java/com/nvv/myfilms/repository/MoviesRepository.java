package com.nvv.myfilms.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.nvv.myfilms.dao.MovieDao;
import com.nvv.myfilms.db.MoviesDatabase;
import com.nvv.myfilms.models.Movie;

import java.util.List;

public final class MoviesRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> movies;

    public MoviesRepository(Application application) {
        this.movieDao = MoviesDatabase.getInstance(application).getMovieDao();
    }

    public LiveData<List<Movie>> getMovies() {
        movies = movieDao.getAllMovies();
        return movies;
    }

    public LiveData<List<Movie>> getMovie(int genreId) {
        movies = movieDao.getAllMoviesByGenre(genreId);
        return movies;
    }

    public void insertMovie(Movie movie){
        new Thread(() -> {
            synchronized (movieDao){
                movieDao.insert(movie);
            }
        }).start();
    }
    public void updateMovie(Movie movie){
        new Thread(() -> {
            synchronized (movieDao){
                movieDao.update(movie);
            }
        }).start();
    }
    public void deleteMovie(Movie movie){
        new Thread(() -> {
            synchronized (movieDao){
                movieDao.delete(movie);
            }
        }).start();
    }
}
