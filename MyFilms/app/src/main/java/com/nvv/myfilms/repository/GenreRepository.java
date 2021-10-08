package com.nvv.myfilms.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.nvv.myfilms.dao.GenreDao;
import com.nvv.myfilms.db.MoviesDatabase;
import com.nvv.myfilms.models.Genre;

import java.util.List;

public class GenreRepository {
    private volatile GenreDao genreDao;
    private LiveData<List<Genre>> genres;

    public GenreRepository(Application application) {
        this.genreDao = MoviesDatabase.getInstance(application).getGenreDao();
    }

    public LiveData<List<Genre>> getGenres() {
        genres = genreDao.getAllGenres();
        return genres;
    }

    public void insertGenre(Genre genre) {
        new Thread(() -> {
            synchronized (genreDao) {
                genreDao.insert(genre);
            }
        }).start();
    }
    public void updateGenre(Genre genre) {
        new Thread(() -> {
            synchronized (genreDao) {
                genreDao.update(genre);
            }
        }).start();
    }
    public void deleteGenre(Genre genre) {
        new Thread(() -> {
            synchronized (genreDao) {
                genreDao.delete(genre);
            }
        }).start();
    }
}
