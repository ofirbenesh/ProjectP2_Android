package com.example.projectp2_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private final MutableLiveData<String> myStringLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> friends;
    private MutableLiveData<List<User>> friendRequests;

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
    public void registerUser(String fullname, String email, String username, String password, String picture) {
        userRepository.addUser(fullname, email, username, password, picture);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }
    public void getUser(String userId) {
        userRepository.getUser(userId);
    }

    public User getUserFromRoom(String userId) {
        return userRepository.getUserFromRoom(userId);
    }
    public MutableLiveData<List<User>> getFriends() { return friends; }
    public MutableLiveData<List<User>> getFriendRequests() { return friendRequests; }
    public void sendFriendRequest(String friendId) {
        userRepository.sendFriendRequest(friendId);
    }
    public void acceptFriendRequest(String friendId) {
        userRepository.acceptFriendRequest(friendId);
    }
    public void removeFriend(String friendId) {
        userRepository.removeFriend(friendId);
    }
    public void removeFriendRequest(String friendId) {
        userRepository.removeFriendRequest(friendId);
    }
}
