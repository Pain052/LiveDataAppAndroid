package com.example.recyclerviewlivedataapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recyclerviewlivedataapplication.database.UserDatabase;
import com.example.recyclerviewlivedataapplication.model.User;
import com.example.recyclerviewlivedataapplication.view_model.UserViewModel;

public class UpdateUserActivity extends AppCompatActivity {
    private EditText edUsernameU, edAddressU;
    private User mUser;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        getDataUser();
        initView();
    }

    //get data
    public void getDataUser(){
        Intent intent = getIntent();
        mUser = (User) intent.getExtras().get("user_obj");
    }

    public void initView(){
        edUsernameU = findViewById(R.id.edUsernameU);
        edAddressU = findViewById(R.id.edAddressU);
        viewModel = new UserViewModel();

        //set text on textview
        edUsernameU.setText(mUser.getUsername());
        edAddressU.setText(mUser.getAddress());
    }

    public void goToHome(View view){
        finish();
    }

    public void handleUpdateUser(View view){
        String username = edUsernameU.getText().toString().trim();
        String address = edAddressU.getText().toString().trim();

        if (mUser.getUsername().equals(username) && mUser.getAddress().equals(address)){
            Toast.makeText(this, "User information has not changed!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(address)){
            Toast.makeText(this, "Username or Address is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        //set data again for user object
        mUser.setUsername(username);
        mUser.setAddress(address);
        //update on room database
        viewModel.updateUser(mUser, UpdateUserActivity.this);
        finish();
    }
}