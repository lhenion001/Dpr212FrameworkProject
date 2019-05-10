package com.leona.passbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leona.passbook.db.entity.StoredPasswordEntity;
import com.leona.passbook.viewmodel.StoredPasswordViewModel;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a representing
 * item details. On tablets, the activity presents the list of ites and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    private final int NEW_PASSWORD_ACTIVITY_REQUEST_CODE = 1;
    private final int EDIT_PASSWORD_ACTIVITY_REQUEST_CODE = 2;

    private StoredPasswordViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemListActivity.this, StoredPasswordDetailActivity.class);
                startActivityForResult(intent,NEW_PASSWORD_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        final StoredPasswordListAdapter adapter = new StoredPasswordListAdapter(this, mEditClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = ViewModelProviders.of(this).get(StoredPasswordViewModel.class);
        mViewModel.getAllStoredPasswords().observe(this, new Observer<List<StoredPasswordEntity>>() {
            @Override
            public void onChanged(List<StoredPasswordEntity> storedPasswordEntities) {
                adapter.setPasswords(storedPasswordEntities);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PASSWORD_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"New item saved", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(),"New item canceled", Toast.LENGTH_LONG);
            }
        } else {
            if(resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Item edits saved", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(),"Item edits canceled", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    private View.OnClickListener mEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = (int)view.getTag();

            //Context context = view.getContext();
            Intent intent = new Intent(ItemListActivity.this, StoredPasswordDetailActivity.class);
            intent.putExtra("position", itemPosition);
            startActivityForResult(intent,EDIT_PASSWORD_ACTIVITY_REQUEST_CODE);
        }
    };
}
