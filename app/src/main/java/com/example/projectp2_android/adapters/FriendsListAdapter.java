package com.example.projectp2_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.UserViewModel;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> {
    class FriendsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFriendName;
//        private final ImageView ivPic;
        private final Button btnAccept;
        private final Button btnDelete;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvFriendName = itemView.findViewById(R.id.friendName);
//            this.ivPic = itemView.findViewById(R.id.friendPic);
            this.btnAccept = itemView.findViewById(R.id.btn_accept);
            this.btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
    private final LayoutInflater mInflater;
    private List<User> friends;
    private Context context;
    private boolean isFriendRequest;
    private UserViewModel userViewModel;

    public FriendsListAdapter(Context context, boolean isFriendRequest, UserViewModel userViewModel) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.isFriendRequest = isFriendRequest;
        this.userViewModel = userViewModel;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.friend_layout, parent, false);
        return new FriendsListAdapter.FriendsViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        if (friends != null && position< friends.size()) {
            if (position == 0 && !isFriendRequest) {
                holder.tvFriendName.setText("");
                holder.btnAccept.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                return;
            }
            final User current = friends.get(position);
            holder.tvFriendName.setText(current.getFirstName() + " " + current.getLastName());
//            holder.ivPic.setImageBitmap(MyApplication.decodeBase64ToBitmap(current.getProfilePhoto()));

            // Handle button visibility based on whether this is the friend requests list
            if (isFriendRequest) {
                holder.btnAccept.setVisibility(View.VISIBLE); // Show accept button
                approveFriendRequest(holder, current);
                removeFriendRequest(holder, current);
            } else {
                holder.btnAccept.setVisibility(View.GONE); // Hide accept button
                removeFriend(holder,current); // listener for deleting friends from friends list
            }
        }
    }
    @Override
    public int getItemCount() {
        if (friends != null) {
            return friends.size();
        }
        return 0;
    }

    public void setFriends(List<User> c) {
        Log.d("FriendsListAdapter", "Setting friends in adapter: " + c.size() + " items.");
        this.friends = c;
        notifyDataSetChanged();
    }

    public List<User> getFriends() {
        return friends;
    }

    public void approveFriendRequest(FriendsViewHolder holder, User current) {
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userViewModel.acceptFriendRequest(current.getUserId());
            }
        });
    }

    public void removeFriend(FriendsViewHolder holder, User current) {
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userViewModel.removeFriend(current.getUserId());
            }
        });
    }

    public void removeFriendRequest(FriendsViewHolder holder, User current) {
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.removeFriendRequest(current.getUserId());
            }
        });
    }
}
