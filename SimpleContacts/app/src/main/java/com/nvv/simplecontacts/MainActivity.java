package com.nvv.simplecontacts;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.nvv.simplecontacts.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ContactAdapter contactAdapter;
    private ContactsDb contactsDb;
    private ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new ContactAdapter(this.contacts,this);
        recyclerView.setAdapter(contactAdapter);

        contactsDb = Room.databaseBuilder(getApplicationContext(), ContactsDb.class, "ContactsDb").build();
        loadContacts();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = contacts.get(viewHolder.getAdapterPosition());
                deleteContact(contact);
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContact(false, null, -1);
            }
        });
    }

    public void addAndEditContact(boolean isUpdate, Contact contact, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        View view = layoutInflater.inflate(R.layout.add_edit_contact, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        TextView contactTitleTextView = view.findViewById(R.id.contactTitleTextView);

        EditText firstNameEditText = view.findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = view.findViewById(R.id.lastNameEditText);
        EditText emailEditText = view.findViewById(R.id.emailEditText);
        EditText phoneEditText = view.findViewById(R.id.phoneNameEditText);

        contactTitleTextView.setText(!isUpdate ? "Add Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            firstNameEditText.setText(contact.getFirstName());
            lastNameEditText.setText(contact.getLastName());
            emailEditText.setText(contact.getEmail());
            phoneEditText.setText(contact.getPhoneNumber());
        }

        builder.setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(firstNameEditText.getText().toString())){
                            Toast.makeText(MainActivity.this,"Enter first name",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(lastNameEditText.getText().toString())){
                            Toast.makeText(MainActivity.this,"Enter last name",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(emailEditText.getText().toString())){
                            Toast.makeText(MainActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
                            Toast.makeText(MainActivity.this,"Enter phone number",Toast.LENGTH_SHORT).show();
                        }else{
                            if (isUpdate&&contact!=null){
                                updateContact(position,new String[]{firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),
                                        emailEditText.getText().toString(),phoneEditText.getText().toString()});
                            }else{
                                Contact temp = new Contact(0,firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),
                                        emailEditText.getText().toString(),phoneEditText.getText().toString());
                                addContact(temp);
                            }
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadContacts() {
        new GetAllContactsAsyncTask().execute();

    }

    private void deleteContact(Contact contact) {
        new DeleteContactAsyncTask().execute(contact);
    }

    private void addContact(Contact contact) {
        new addContactAsyncTask().execute(contact);
    }

    private void updateContact(int position,String...args){
        Contact contact = contacts.get(position);
        contact.setAll(args);
        new updateContactAsyncTask().execute(contact);
        contacts.set(position,contact);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            contacts = (ArrayList<Contact>) contactsDb.getContactDao().getAllContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            contactAdapter.setContactArrayList(contacts);
        }
    }

    private class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDb.getContactDao().deleteContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            loadContacts();
        }
    }

    private class addContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDb.getContactDao().insertContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            loadContacts();
        }
    }

    private class updateContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDb.getContactDao().updateContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            loadContacts();
        }
    }
}