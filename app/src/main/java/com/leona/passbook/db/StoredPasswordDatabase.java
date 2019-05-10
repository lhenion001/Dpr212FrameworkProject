package com.leona.passbook.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.leona.passbook.db.dao.StoredPasswordDao;
import com.leona.passbook.db.entity.StoredPasswordEntity;

@Database(entities = {StoredPasswordEntity.class}, version = 1)
public abstract class StoredPasswordDatabase extends RoomDatabase {
    public abstract StoredPasswordDao storedPasswordDao();

    private static volatile StoredPasswordDatabase INSTANCE;

    static StoredPasswordDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StoredPasswordDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StoredPasswordDatabase.class, "stored_password_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final StoredPasswordDao mDao;

        PopulateDbAsync(StoredPasswordDatabase db) {
            mDao = db.storedPasswordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            StoredPasswordEntity word = new StoredPasswordEntity("Google", "LHenion", "Password");
            mDao.insert(word);
            StoredPasswordEntity word1 = new StoredPasswordEntity("Amazon", "LHenion", "Password");
            mDao.insert(word1);
            StoredPasswordEntity word2 = new StoredPasswordEntity("Netflix", "LHenion", "Password");
            mDao.insert(word2);
            return null;
        }
    }
}
