package com.example.projectp2_android.entities;

import java.util.List;

public class FriendRequestsResponse {
    private List<User> pendingRequests;

    // Getter
    public List<User> getPendingRequests() {
        return pendingRequests;
    }

    // Setter
    public void setPendingRequests(List<User> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }
}

