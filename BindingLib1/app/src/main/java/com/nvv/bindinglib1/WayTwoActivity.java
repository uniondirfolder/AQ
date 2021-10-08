package com.nvv.bindinglib1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.nvv.bindinglib1.databinding.ActivityTwoWayBinding;


public class WayTwoActivity extends AppCompatActivity {

    private ActivityTwoWayBinding twoWayActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way);
        twoWayActivity = DataBindingUtil.setContentView(this,R.layout.activity_two_way);
        Greeting g = new Greeting("Bob","Hello!");
        twoWayActivity.setGreeting(g);

    }
}