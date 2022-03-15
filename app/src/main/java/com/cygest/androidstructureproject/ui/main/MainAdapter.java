package com.cygest.androidstructureproject.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cygest.androidstructureproject.R;
import com.cygest.androidstructureproject.db.entities.UserEntity;
import com.google.android.material.textview.MaterialTextView;

public class MainAdapter extends PagingDataAdapter<UserEntity, MainAdapter.UsersViewHolder> {

    public static final String TAG = MainAdapter.class.getSimpleName();
    public static final DiffUtil.ItemCallback<UserEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return false;
        }
    };
    public OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(UserEntity userEntity);
    }


    public MainAdapter(@NonNull OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MainAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UsersViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.UsersViewHolder holder, int position) {
        UserEntity userEntity = getItem(position);
        holder.bind(userEntity);
        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClickListener(userEntity));
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        public final MaterialTextView login;
        public final MaterialTextView address;
        public final MaterialTextView other;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            login = itemView.findViewById(R.id.login);
            address = itemView.findViewById(R.id.address);
            other = itemView.findViewById(R.id.other);
        }

        @NonNull
        public static UsersViewHolder create(@NonNull ViewGroup parent) {
            return new UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recyclerview, parent, false));
        }

        public void bind(UserEntity userEntity) {
            login.setText(userEntity.getLogin());
            address.setText(userEntity.getAddress());
            other.setText(userEntity.getVar_to_ignore());
        }
    }
}
