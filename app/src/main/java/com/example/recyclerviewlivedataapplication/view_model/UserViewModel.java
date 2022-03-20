package com.example.recyclerviewlivedataapplication.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recyclerviewlivedataapplication.MainActivity;
import com.example.recyclerviewlivedataapplication.database.UserDatabase;
import com.example.recyclerviewlivedataapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listMutableLiveData;
    private List<User> mUserList;

    public UserViewModel() {
        listMutableLiveData = new MutableLiveData<>();
        mUserList = new ArrayList<>();
    }


    public MutableLiveData<List<User>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void addUser(User user, Context context){
        UserDatabase.getInstance(context).userDAO().insertUser(user);
    }

    public void loadUser(Context context){
        mUserList = UserDatabase.getInstance(context).userDAO().getAllUser();
        listMutableLiveData.setValue(mUserList);
    }

    public void updateUser(User user, Context context){
        UserDatabase.getInstance(context).userDAO().updateUser(user);
    }

    public void deleteUser(User user, Context context){
        UserDatabase.getInstance(context).userDAO().deleteUser(user);
    }

    public void searchUser(String username, Context context){
        UserDatabase.getInstance(context).userDAO().searchUser(username);
    }

    public boolean isUserExist(User user, Context context){
        List<User> list = UserDatabase.getInstance(context).userDAO().checkUserExist(user.getUsername());
        return list != null && !list.isEmpty();
    }
}
