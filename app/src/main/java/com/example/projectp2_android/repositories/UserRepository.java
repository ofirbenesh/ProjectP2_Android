package com.example.projectp2_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.R;
import com.example.projectp2_android.db.LocalDatabase;
import com.example.projectp2_android.db.UsersDB;
import com.example.projectp2_android.db.dao.UserDao;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.webservices.FriendAPI;
import com.example.projectp2_android.webservices.PostAPI;
import com.example.projectp2_android.webservices.UserAPI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserRepository {
    private UserAPI userAPI;
    private FriendAPI friendAPI;
    private FriendsListData friendsListData;

    public UserRepository() {
        userAPI = new UserAPI();
        friendAPI = new FriendAPI();
    }

    public void getUser(String userId) {
        userAPI.getUserById(userId);
    }
//
//    public void setCallback(OperationCallback callback) {
//        this.userAPI.setCallback(callback);
//    }

    public void addUser(String email,String username, String password, String picture) {
        userAPI.addUser(email, username, password, picture);
    }

    class FriendsListData extends MutableLiveData<List<User>> {
        public FriendsListData() {
            super();
            setValue(new LinkedList<User>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            if (friendAPI == null) {
                friendAPI = new FriendAPI();
            }
            friendAPI.getFriends();
//            new Thread(() ->
//            {
//                // TODO check if dao.get
//                postListData.postValue(dao.index());
//            }).start();
        }
    }

    public LiveData<List<User>> getAll() {
        return friendsListData;
    }

//    public void addFriend(final Post post) {
//
//    }
}
