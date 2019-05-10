package com.leona.passbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.leona.passbook.db.StoredPasswordRepository;
import com.leona.passbook.db.entity.StoredPasswordEntity;
import com.leona.passbook.viewmodel.StoredPasswordViewModel;

import java.util.List;

public class StoredPasswordDetailActivity extends AppCompatActivity {

    int idEntity;
    EditText txtAccountName;
    EditText txtUserName;
    EditText txtPassword;
    private StoredPasswordRepository mRepository;
    private StoredPasswordViewModel mViewModel;
    private List<StoredPasswordEntity> mEntities;
    private StoredPasswordEntity mCurrent;
    private int mPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int mode = getIntent().getIntExtra("requestCode",0);
        mPosition = getIntent().getIntExtra("position",-1);

        mViewModel = ViewModelProviders.of(this).get(StoredPasswordViewModel.class);
        mViewModel.getAllStoredPasswords().observe(this, new Observer<List<StoredPasswordEntity>>() {
            @Override
            public void onChanged(List<StoredPasswordEntity> storedPasswordEntities) {
                if(mPosition < 0) return;
                mCurrent = storedPasswordEntities.get(mPosition);
                loadItem();
            }
        });
//        mRepository = new StoredPasswordRepository(this.getApplication());
//        mEntities = mRepository.getAllStoredPasswords().getValue();
        setContentView(R.layout.item_detail);

        txtAccountName = findViewById(R.id.txtAccount);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        Button cancel = findViewById(R.id.btnCancel);
        Button accept = findViewById(R.id.btnAccept);
        CheckBox showPassword = findViewById(R.id.cbShowPassword);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    txtPassword.setTransformationMethod(null);
                }else {
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mPosition == -1) {
                    StoredPasswordEntity spe = new StoredPasswordEntity(
                            txtAccountName.getText().toString(),
                            txtUserName.getText().toString(),
                            txtPassword.getText().toString());
                    mViewModel.insert(spe);
                }
                else {
                    mCurrent.AccountName = txtAccountName.getText().toString();
                    mCurrent.UserName = txtUserName.getText().toString();
                    mCurrent.Password = txtPassword.getText().toString();
                    mViewModel.update(mCurrent);
                }

                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });
    }

    private void loadItem() {
        if(mCurrent == null) return;
        txtAccountName.setText(mCurrent.AccountName);
        txtUserName.setText(mCurrent.UserName);
        txtPassword.setText(mCurrent.Password);
    }
}
