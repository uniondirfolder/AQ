package com.nvv.bindinglib1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.nvv.bindinglib1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

//    private TextView titleTV;
//    private TextView authorTV;

    private ActivityMainBinding activityMainBinding;
    private MainActivityButtonsHandler mainActivityButtonsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        titleTV = findViewById(R.id.titleTextView);
//        authorTV = findViewById(R.id.authorTextView);
//
//        Book b = getCurrentBook();
//        titleTV.setText(b.getTitle());
//        authorTV.setText(b.getAuthor());

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setBook(getCurrentBook());

        mainActivityButtonsHandler = new MainActivityButtonsHandler(this, this);
        activityMainBinding.setButtonHandler(mainActivityButtonsHandler);
    }

    private Book getCurrentBook(){
        return new Book("Hamlet", "Shakespeare");
    }

//    public void onCancelClicked(View view) {
//        Toast.makeText((Context) this, "Cancel", Toast.LENGTH_SHORT).show();
//    }
//
//    public void onOkClicked(View view) {
//        Toast.makeText((Context) this, "Ok", Toast.LENGTH_SHORT).show();
//    }

}