package com.example.recyclerviewlivedataapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewlivedataapplication.R;
import com.example.recyclerviewlivedataapplication.UpdateUserActivity;
import com.example.recyclerviewlivedataapplication.model.User;
import com.example.recyclerviewlivedataapplication.view_model.UserViewModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private List<User> mUserList;
    private IOnClick onClick;


    public UserAdapter(Context context, List<User> mUserList) {
        this.context = context;
        this.mUserList = mUserList;
        this.onClick = (IOnClick) context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(List<User> userList) {
        this.mUserList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = mUserList.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvAddress.setText(user.getAddress());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(mUserList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvAddress;
        private final View container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //mapping xml
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            container = itemView.findViewById(R.id.container);
        }
    }

    public interface IOnClick {
        void onClick(User user);
    }
}
