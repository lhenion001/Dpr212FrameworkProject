package com.leona.passbook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.leona.passbook.db.entity.StoredPasswordEntity;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface StoredPasswordDao {

    @Query("SELECT * FROM stored_password_table ORDER BY accountName ASC")
    LiveData<List<StoredPasswordEntity>> getAll();

    @Query("SELECT * FROM stored_password_table WHERE id = :id")
    StoredPasswordEntity getStoredPasswordById(int id);

    @Insert
    void insert(StoredPasswordEntity value);

    @Delete
    void delete(StoredPasswordEntity value);

    @Update(onConflict = REPLACE)
    void update(StoredPasswordEntity value);

    @Query("DELETE FROM stored_password_table")
    void deleteAll();
}
