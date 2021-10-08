package com.nvv.myfilms;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nvv.myfilms.models.Genre;
import com.nvv.myfilms.models.MainActivityVM;
import com.nvv.myfilms.models.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private MainActivityVM activityVM;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityVM = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainActivityVM.class);

        activityVM.getGenres().observe(this, genres -> {
            for (Genre g:genres){

                Log.d("MyTag", g.getGenreName());
            }
        });
        activityVM.getMovie(2).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                for (Movie m:movies){
                    Log.d("MyTag", m.getMovieName());
                }
            }
        });
    }
}