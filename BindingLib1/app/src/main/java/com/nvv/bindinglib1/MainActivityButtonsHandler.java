package com.nvv.bindinglib1;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainActivityButtonsHandler {
    private final MainActivity mainActivity;
    private Context context;


    public MainActivityButtonsHandler(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    public void onCancelClicked(View view) {
        //Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
        mainActivity.startActivity(new Intent(mainActivity, WayTwoActivity.class));
    }

    public void onOkClicked(View view) {
        //Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
        mainActivity.startActivity(new Intent(mainActivity, OkActivity.class));
    }
}
