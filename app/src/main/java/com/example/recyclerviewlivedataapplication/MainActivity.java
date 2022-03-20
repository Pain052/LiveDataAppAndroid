package com.example.recyclerviewlivedataapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recyclerviewlivedataapplication.adapter.UserAdapter;
import com.example.recyclerviewlivedataapplication.database.UserDatabase;
import com.example.recyclerviewlivedataapplication.model.User;
import com.example.recyclerviewlivedataapplication.view_model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.IOnClick{
    private RecyclerView rvListUser;
    private AppCompatButton btnAddUserM;
    private UserAdapter mUserAdapter;
    private UserViewModel mUserViewModel;
    private EditText edSearchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        adapterModify();
    }

    public void initView(){
        rvListUser = findViewById(R.id.rvListUser);
        btnAddUserM = findViewById(R.id.btnAddUserM);
        edSearchUser = findViewById(R.id.edSearchUser);

        mUserAdapter = new UserAdapter(MainActivity.this, new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvListUser.setLayoutManager(layoutManager);
        rvListUser.setAdapter(mUserAdapter);
    }

    private void adapterModify() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.getListMutableLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mUserAdapter.setUserList(users);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserViewModel.loadUser(this);
    }

    public void handleAddUser(View view){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.item_dialog, null);
        final EditText edUsername = (EditText) alertLayout.findViewById(R.id.edUsername);
        final EditText edAddress = (EditText) alertLayout.findViewById(R.id.edAddress);

        //init alertBuilder and set properties
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ADD USER");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO cancel
            }
        });

        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //init string contain input text and check valid
                String username = edUsername.getText().toString();
                String address = edAddress.getText().toString();
                if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(address)){
                    User user = new User(username, address);
                    //Check book exist
                    if (mUserViewModel.isUserExist(user, MainActivity.this)){
                        Log.e("DATA_EXIST", "====>" + mUserViewModel.isUserExist(user, MainActivity.this));
                        Toast.makeText(MainActivity.this, "User exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mUserViewModel.addUser(user, MainActivity.this);
                    mUserViewModel.loadUser(MainActivity.this);
                    Toast.makeText(MainActivity.this, "ADD USER SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "ADD USER FAILED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void handleSearchUser(View view){
        String searchUser = edSearchUser.getText().toString().trim();
        List<User> users = UserDatabase.getInstance(MainActivity.this).userDAO().searchUser(searchUser);
        mUserAdapter.setUserList(users);
    }

    void handleDialog(User user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Options");
        alertDialog.setMessage("Update information user or delete user?");
        alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserViewModel viewModel = new UserViewModel();
                viewModel.deleteUser(user, MainActivity.this);
                List<User> users = UserDatabase.getInstance(MainActivity.this).userDAO().getAllUser();
                mUserAdapter.setUserList(users);
                Toast.makeText(MainActivity.this, "Delete user successful!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, UpdateUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_obj", user);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public void onClick(User user) {
        handleDialog(user);
    }
}