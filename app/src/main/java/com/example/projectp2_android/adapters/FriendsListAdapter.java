package com.example.projectp2_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.entities.User;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> {
    class FriendsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFriendName;
        private final ImageView ivPic;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvFriendName = itemView.findViewById(R.id.friendName);
            this.ivPic = itemView.findViewById(R.id.friendPic);
        }
    }
    private final LayoutInflater mInflater;
    private List<User> friends;
    private Context context;

    public FriendsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.friend_layout, parent, false);
        return new FriendsListAdapter.FriendsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        if (friends != null) {
            final User current = friends.get(position);
            holder.tvFriendName.setText(current.getFirstName());
            holder.ivPic.setImageBitmap(MyApplication.decodeBase64ToBitmap(current.getProfilePhoto()));
        }
    }
    @Override
    public int getItemCount() {
        return 0;
    }
    public void setFriends(List<User> c) {
        friends = c;
        notifyDataSetChanged();
    }
}
