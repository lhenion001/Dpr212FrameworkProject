package com.leona.passbook.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.leona.passbook.db.dao.StoredPasswordDao;
import com.leona.passbook.db.entity.StoredPasswordEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.Integer.parseInt;

public class StoredPasswordRepository {

    private StoredPasswordDao mStoredPasswordDao;
    private LiveData<List<StoredPasswordEntity>> mAllStoredPasswords;

    public StoredPasswordRepository(Application application) {
        StoredPasswordDatabase db = StoredPasswordDatabase.getDatabase(application);
        mStoredPasswordDao = db.storedPasswordDao();
        mAllStoredPasswords = mStoredPasswordDao.getAll();
    }
    public LiveData<List<StoredPasswordEntity>> getAllStoredPasswords(){
        return mAllStoredPasswords;
    }

   
    public void insert(StoredPasswordEntity storedPassword) {
        new insertAsyncTask(mStoredPasswordDao).execute(storedPassword);
    }

    private static class insertAsyncTask extends AsyncTask<StoredPasswordEntity, Void, Void> {

        private StoredPasswordDao mAsyncTaskDao;

        insertAsyncTask(StoredPasswordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final StoredPasswordEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(StoredPasswordEntity storedPassword) {
        new updateAsyncTask(mStoredPasswordDao).execute(storedPassword);
    }

    private static class updateAsyncTask extends AsyncTask<StoredPasswordEntity, Void, Void> {

        private StoredPasswordDao mAsyncTaskDao;

        updateAsyncTask(StoredPasswordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final StoredPasswordEntity... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
    public void delete(StoredPasswordEntity storedPassword) {
        new updateAsyncTask(mStoredPasswordDao).execute(storedPassword);
    }

    private static class deleteAsyncTask extends AsyncTask<StoredPasswordEntity, Void, Void> {

        private StoredPasswordDao mAsyncTaskDao;

        deleteAsyncTask(StoredPasswordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final StoredPasswordEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public StoredPasswordEntity findById(int id) {
        String s = Integer.toString(id);
        StoredPasswordEntity entity = null;
        try {
            entity = new findByIdAsyncTask(mStoredPasswordDao).execute(s).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private static class findByIdAsyncTask extends AsyncTask<String, Void, StoredPasswordEntity> {

        private StoredPasswordDao mAsyncTaskDao;

        findByIdAsyncTask(StoredPasswordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected StoredPasswordEntity doInBackground(final String... params) {
           return mAsyncTaskDao.getStoredPasswordById(Integer.valueOf(params[0]));
        }
    }

}
