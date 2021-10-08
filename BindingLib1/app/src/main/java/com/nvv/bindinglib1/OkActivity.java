package com.nvv.bindinglib1;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nvv.bindinglib1.databinding.ActivityOkBinding;

public class OkActivity extends AppCompatActivity {

    private ActivityOkBinding activityOkBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityOkBinding = DataBindingUtil.setContentView(this,R.layout.activity_ok);
        activityOkBinding.setBook(getCurrentBook());
    }



    private Book getCurrentBook(){
        return new Book("Hamlet", "Shakespeare");
    }
}