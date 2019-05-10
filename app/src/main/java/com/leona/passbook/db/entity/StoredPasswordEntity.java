package com.leona.passbook.db.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stored_password_table")

public class StoredPasswordEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int StoredPasswordId;

    @ColumnInfo(name = "accountName")
    public String AccountName;
    @ColumnInfo(name = "userName")
    public String UserName;
    @ColumnInfo(name = "password")
    public String Password;

    public StoredPasswordEntity() {

    }

    public StoredPasswordEntity(String account, String username, String password) {
        AccountName = account;
        UserName = username;
        Password = password;
    }
}
