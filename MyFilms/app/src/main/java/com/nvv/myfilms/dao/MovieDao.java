package com.nvv.myfilms.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.nvv.myfilms.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insert(Movie movie);
    @Update
    void update(Movie movie);
    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movies_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies_table WHERE genre_id==:genreId")
    LiveData<List<Movie>> getAllMoviesByGenre(int genreId);
}
