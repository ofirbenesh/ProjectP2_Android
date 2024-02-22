package com.example.projectp2_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectp2_android.CommentsActivity;
import com.example.projectp2_android.Feed;
import com.example.projectp2_android.entities.GlobalVariables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.R;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvContent;
        private final TextView tvDate;
        private final ImageView ivPic;
        private final ImageView ivProfilePic;
        private final TextView tvLikes;
        private final ImageButton likeButton;
        private final ImageButton shareButton;
        private final ImageButton commentButton;
        private final ImageButton editPostButton;
        private LinearLayout popupLayout;
        private View overlay;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.tvAuthor = itemView.findViewById(R.id.postsAuthor);
            this.tvContent = itemView.findViewById(R.id.postsText);
            this.tvDate = itemView.findViewById(R.id.postsDate);
            this.ivPic = itemView.findViewById(R.id.postsImage);
            this.ivProfilePic = itemView.findViewById(R.id.profilePic);
            this.tvLikes = itemView.findViewById(R.id.num_of_likes);
            this.likeButton = itemView.findViewById(R.id.likeButton);
            this.shareButton = itemView.findViewById(R.id.shareButton);
            this.commentButton = itemView.findViewById(R.id.commentButton);
            this.popupLayout = itemView.findViewById(R.id.shareApps);
            this.overlay = itemView.findViewById(R.id.overlay);
            this.editPostButton = itemView.findViewById(R.id.editButton);

        }

        //region for pop up share button
        public void togglePopup() {
            if (popupLayout.getVisibility() == View.VISIBLE) {
                popupLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
            } else {
                popupLayout.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);
            }
        }
        //endregion
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;
    private Context context;

    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getContent());
            holder.tvDate.setText(current.getDate());

            // to check if Uri was sent as img
            if (current.getPic() != -1) {
                holder.ivPic.setImageResource(current.getPic());
            }
            else {
                holder.ivPic.setImageURI(current.getPicUri());
            }
            if (current.getProfilePic() != -1) {
                holder.ivProfilePic.setImageResource(current.getProfilePic());
            }
            else {
                holder.ivProfilePic.setImageURI(current.getProfilePicUri());
            }

            int numberOfLikes = current.getLikes();
            holder.tvLikes.setText(String.valueOf(numberOfLikes));

            //region like button
            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO add boolean variable to know if liked already been clicked
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        Post currentPost = posts.get(currentPosition);
                        if (currentPost != null) {
                            boolean isLiked = currentPost.isLiked();

                            // Update the likes count based on the like status
                            int likes = currentPost.getLikes();
                            if (isLiked) {
                                // If the post is liked, remove the like
                                currentPost.setLikes(Math.max(likes - 1,0));
                                holder.likeButton.setImageResource(R.drawable.btn_like_blue);
                            } else {
                                // If the post is unliked, add like
                                currentPost.setLikes(likes + 1);
                                holder.likeButton.setImageResource(R.drawable.btn_like);
                            }
                            currentPost.setLiked(!isLiked);

                            holder.tvLikes.setText(String.valueOf(currentPost.getLikes()));

                            // notifyDataSetChanged();
                            notifyItemChanged(currentPosition);
                        }
                    }
                }
            });

            // edit button onclick
            if (current.getAuthor().equals(GlobalVariables.userName)) {
                holder.editPostButton.setVisibility(View.VISIBLE);
                holder.editPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Implement your edit logic here
                        // This could involve starting a new Activity or showing a Dialog to edit the post
                    }
                });
            } else {
                holder.editPostButton.setVisibility(View.GONE);
            }
            // share button onclick
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.togglePopup();
                }
            });
            // after share button is open click on background will close it
            holder.overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.togglePopup();
                }
            });

            // move to comments page for a specific post
            holder.commentButton.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Post currentPost = posts.get(currentPosition);
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("POST_ID", currentPost.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }

    public List<Post> getPosts() {
        return posts;
    }

}
