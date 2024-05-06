package com.example.projectp2_android;

import java.util.ArrayList;
import java.util.List;
public class RegisteredUsers {
    private List<User> listOfUsers;

    // Private constructor to prevent direct instantiation
   public RegisteredUsers() {
        this.listOfUsers = new ArrayList<>();
    }

    public void addUser(User newUser) {
       this.listOfUsers.add(newUser);
    }

    // Method to get the list of registered users
    public List<User> getUserList() {
        return listOfUsers;
    }
}
