package com.nvv.myfilms.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres_table")
public class Genre extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key_id")
    private int keyId;

    @ColumnInfo(name = "genre_name")
    private String genreName;

    public Genre() {
    }

    @Ignore
    public Genre(int keyId, String genreName) {
        this.keyId = keyId;
        this.genreName = genreName;
    }

    @Bindable
    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
        notifyPropertyChanged(BR.keyId);
    }

    @Bindable
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
        notifyPropertyChanged(BR.genreName);
    }
}
