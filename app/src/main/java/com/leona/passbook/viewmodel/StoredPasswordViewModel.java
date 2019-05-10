package com.leona.passbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.leona.passbook.db.StoredPasswordRepository;
import com.leona.passbook.db.entity.StoredPasswordEntity;

import java.util.List;

public class StoredPasswordViewModel extends AndroidViewModel {

    private StoredPasswordRepository mRepository;
    private LiveData<List<StoredPasswordEntity>> mAllStoredPasswords;

    public StoredPasswordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new StoredPasswordRepository(application);
        mAllStoredPasswords = mRepository.getAllStoredPasswords();
    }

    public LiveData<List<StoredPasswordEntity>> getAllStoredPasswords() {
        return mAllStoredPasswords;
    }

    public void insert(StoredPasswordEntity storedPassword){
        mRepository.insert(storedPassword);
    }
    public void update(StoredPasswordEntity storedPassword){
        mRepository.update(storedPassword);
    }
    public void delete(StoredPasswordEntity storedPassword){
        mRepository.delete(storedPassword);
    }
    public StoredPasswordEntity findById(int id){
        return mRepository.findById(id);
    }
    public StoredPasswordEntity getByPosition(int position){
        return mAllStoredPasswords.getValue()
                .get(position);
    }
}
