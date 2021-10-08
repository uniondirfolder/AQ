package com.nvv.myfilms.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nvv.myfilms.dao.GenreDao;
import com.nvv.myfilms.dao.MovieDao;
import com.nvv.myfilms.models.Genre;
import com.nvv.myfilms.models.Movie;

@Database(entities = {Genre.class, Movie.class}, version = 2)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract GenreDao getGenreDao();
    public abstract MovieDao getMovieDao();

    private static MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, "filmDb")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void,Void,Void>{
        private GenreDao genreDao;
        private MovieDao movieDao;

        public InitialDataAsyncTask(MoviesDatabase database) {
            this.genreDao = database.getGenreDao();
            this.movieDao = database.getMovieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Genre comedy = new Genre();
            comedy.setGenreName("Comedy");

            Genre romance = new Genre();
            romance.setGenreName("Romance");

            Genre drama = new Genre();
            romance.setGenreName("Drama");
            genreDao.insert(comedy);
            genreDao.insert(romance);
            genreDao.insert(drama);


            Movie m1 = new Movie();
            m1.setMovieName("Bad Boys for Life");
            m1.setMovieDescription("Some text");
            m1.setGenreId(1);

            Movie m2 = new Movie();
            m2.setMovieName("Bad Boys for Life 2");
            m2.setMovieDescription("Some text");
            m2.setGenreId(2);

            Movie m3 = new Movie();
            m3.setMovieName("Bad Boys for Life 3");
            m3.setMovieDescription("Some text");
            m3.setGenreId(3);
            movieDao.insert(m1);
            movieDao.insert(m2);
            movieDao.insert(m3);

            return null;
        }
    }
}
