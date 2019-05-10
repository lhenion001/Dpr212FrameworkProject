package com.leona.passbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leona.passbook.db.entity.StoredPasswordEntity;

import java.util.Collections;
import java.util.List;

public class StoredPasswordListAdapter  extends RecyclerView.Adapter<StoredPasswordListAdapter.StoredPasswordViewHolder> {

    private final LayoutInflater mInflater;
    private View.OnClickListener mItemClickListener;
    private List<StoredPasswordEntity> mStoredPasswords = Collections.emptyList(); // cached copy


    public StoredPasswordListAdapter(Context context, View.OnClickListener itemClickListener) {

        mInflater = LayoutInflater.from(context);
        mItemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public StoredPasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_content, parent, false);
        return new StoredPasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoredPasswordViewHolder holder, int position) {
        StoredPasswordEntity currentEntity = mStoredPasswords.get(position);
        holder.accountNameView.setTag(position);
        holder.accountNameView.setText(currentEntity.AccountName);
        holder.accountNameView.setOnClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mStoredPasswords.size();
    }

    public void setPasswords(List<StoredPasswordEntity> passwords)    {
        mStoredPasswords = passwords;
        notifyDataSetChanged();
    }

    class StoredPasswordViewHolder extends RecyclerView.ViewHolder {

        private final TextView accountNameView;

        public StoredPasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameView = itemView.findViewById(R.id.txtAccountName);
        }
    }
}
