package com.nvv.retrofitdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.nvv.retrofitdemo.adapters.CountryAdapter;
import com.nvv.retrofitdemo.model.CountryInfo;
import com.nvv.retrofitdemo.model.Result;
import com.nvv.retrofitdemo.service.CountryService;
import com.nvv.retrofitdemo.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Result> resultArrayList;
    private CountryAdapter countryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCountries();
    }

    private Object getCountries() {
        CountryService countryService = RetrofitInstance.getService();
        Call<CountryInfo> call = countryService.getResults();

        call.enqueue(new Callback<CountryInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CountryInfo> call, Response<CountryInfo> response) {

                CountryInfo countryInfo = response.body();

                if(countryInfo!=null && countryInfo.getRestResponse()!=null){

                    resultArrayList= (ArrayList<Result>) countryInfo.getRestResponse().getResult();

//                    resultArrayList.forEach(e-> Log.d("resultArrayList: ", e.getName()));
                    fillRecycleView();
                }
            }

            @Override
            public void onFailure(Call<CountryInfo> call, Throwable t) {

            }
        });

        return resultArrayList;
    }

    private void fillRecycleView() {
        recyclerView = findViewById(R.id.recyclerView);
        countryAdapter = new CountryAdapter(resultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(countryAdapter);
    }
}