package com.nvv.simplecontacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsDb extends RoomDatabase {
    public abstract ContactDao getContactDao();
}
