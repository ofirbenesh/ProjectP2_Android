package com.example.projectp2_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private final MutableLiveData<String> myStringLiveData = new MutableLiveData<>();
    private LiveData<List<User>> friends;
    private LiveData<List<User>> friendRequests;

    public UserViewModel() {
        this.userRepository = new UserRepository();
        this.friends = userRepository.getAll();
        this.friendRequests = userRepository.getFriendRequestsData();
    }

    // Getter for LiveData
    public LiveData<String> getMyStringLiveData() {
        return myStringLiveData;
    }

    public void updateToken(String token) {
        myStringLiveData.postValue(token);
    }
    public void registerUser(String email, String username, String password, String picture) {
        userRepository.addUser(email, username, password, picture);
    }
    public void getUser(String userId) {
        userRepository.getUser(userId);
    }
    public LiveData<List<User>> getFriends() { return friends; }
    public LiveData<List<User>> getFriendRequests() { return friendRequests; }
    public void sendFriendRequest(String friendId) {
        userRepository.sendFriendRequest(friendId);
    }
}
