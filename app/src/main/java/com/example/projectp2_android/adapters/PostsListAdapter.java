package com.example.projectp2_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.activities.CommentsActivity;
import com.example.projectp2_android.activities.UserProfileActivity;
import com.example.projectp2_android.entities.GlobalVariables;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.PostsViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.UserAPI;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {
    class PostViewHolder extends RecyclerView.ViewHolder implements CallBack {

        private final TextView tvAuthor;
        private final TextView tvContent;
        private final TextView tvDate;
        private final ImageView ivPic;
        private Bitmap profileBitmap;
        private ImageView ivProfilePic;
        private final TextView tvLikes;
        private final ImageButton likeButton;
        private final ImageButton shareButton;
        private final ImageButton commentButton;
        private final ImageButton editPostButton;
        private final ImageButton deletePostButton;
        private final ImageButton addFriendButton;
        private LinearLayout popupLayoutShare;
        private LinearLayout popupLayoutEdit;
        private View overlay;
        private UserAPI userApi;

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
            this.popupLayoutShare = itemView.findViewById(R.id.shareApps);
            this.popupLayoutEdit = itemView.findViewById(R.id.editPost);
            this.overlay = itemView.findViewById(R.id.overlay);
            this.editPostButton = itemView.findViewById(R.id.editButton);
            this.deletePostButton = itemView.findViewById(R.id.deleteButton);
            this.addFriendButton = itemView.findViewById(R.id.AddFriendBtn);
            this.userApi = new UserAPI();
            this.userApi.setCallback(this);
        }

        //region for pop up share button

        public void togglePopup(LinearLayout layout) {
            if (layout.getVisibility() == View.VISIBLE) {
                layout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onSuccess(String token) {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void userIsReturned(User user) {
            String profilePicBase64 = user.getProfilePic();
            if (profilePicBase64 != null && !profilePicBase64.equals("")) {
                profileBitmap = MyApplication.decodeBase64ToBitmap(profilePicBase64);
                return;
//                    if (profileBitmap != null && this.ivProfilePic != null) {
//                        ivProfilePic.setImageBitmap(profileBitmap);
//                    }
                }
            }
        }
    private final LayoutInflater mInflater;

    private List<Post> posts;
    private Context context;
    private UserViewModel userViewModel;
    private PostsViewModel postsViewModel;
    public PostsListAdapter(Context context, UserViewModel userViewModel) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.userViewModel = userViewModel;
        this.postsViewModel = new PostsViewModel();
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

            String PostPicBase64 = current.getPhoto();
            if (PostPicBase64 != null && !PostPicBase64.equals("")) {
                Bitmap profileBitmap = MyApplication.decodeBase64ToBitmap(PostPicBase64);
                holder.ivPic.setImageBitmap(profileBitmap);
            }

            String authorId = current.getUserId();
            holder.userApi.getUserById(authorId);
            if (holder.profileBitmap != null) {
                holder.ivProfilePic.setImageBitmap(holder.profileBitmap);
            }

            // TODO take care of time
            holder.tvDate.setText("");

            int numberOfLikes = current.getLikes();
            holder.tvLikes.setText(String.valueOf(numberOfLikes));

            //region like button
            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        Post currentPost = posts.get(currentPosition);
                        if (currentPost != null) {
                            boolean isLiked = currentPost.isLiked();

                            // Update the likes count based on the like status
                            int likes = currentPost.getLikes();
                            if (isLiked) {
                                // If the post is liked, remove the like
                                currentPost.removeLike();
                                holder.likeButton.setImageResource(R.drawable.btn_like_blue);
                            } else {
                                // If the post is unliked, add like
                                currentPost.addLike();
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
            if (current.getAuthor().equals(MyApplication.loggedUser)) {
                holder.editPostButton.setVisibility(View.VISIBLE);
                holder.editPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition(); // Use a different variable name if necessary
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            Post currentPost = posts.get(adapterPosition);
                            holder.togglePopup(holder.popupLayoutEdit);
                            EditText newContent = holder.itemView.findViewById(R.id.editTextPost);

                            ImageButton publishBtn = holder.itemView.findViewById(R.id.publishEdit);
                            publishBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    current.setContent(newContent.getText().toString());
                                    holder.tvContent.setText(newContent.getText().toString());
                                    postsViewModel.updatePost(current);
                                }
                            });
                    }
                }
                });
            } else {
                holder.editPostButton.setVisibility(View.GONE);
            }

            // delete post button clicked
            if (current.getAuthor().equals(MyApplication.loggedUser)) {
                holder.deletePostButton.setVisibility(View.VISIBLE);
                holder.deletePostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition(); // Use a different variable name if necessary
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            Post currentPost = posts.get(adapterPosition);
                            GlobalVariables.allPosts.remove(currentPost);
                            setPosts(GlobalVariables.allPosts);
                        }
                    }
                });
            } else {
                holder.deletePostButton.setVisibility(View.GONE);
            }

            // share button onclick
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.togglePopup(holder.popupLayoutShare);
                }
            });
            // after share button is open click on background will close it
            holder.overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.togglePopup(holder.popupLayoutShare);
                }
            });

            // add Friend onClick
            holder.addFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String friendID = current.getUserId();
                    userViewModel.sendFriendRequest(friendID);
                    holder.addFriendButton.setImageResource(R.drawable.friendsent);
                }
            });
            if (MyApplication.isFriendOf(current.getUserId())) {
                holder.addFriendButton.setVisibility(View.GONE);
            }
            else {
                holder.addFriendButton.setVisibility(View.VISIBLE);
            }

            // move to comments page for a specific post
            holder.commentButton.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Post currentPost = posts.get(currentPosition);
                    Intent intent = new Intent(context, CommentsActivity.class);
                    context.startActivity(intent);
                }
            });

            // when clicking on another user's name, go to the user's profile
            holder.tvAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = posts.get(position);
                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("userId", post.getUserId());
                        context.startActivity(intent);
                    }
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
