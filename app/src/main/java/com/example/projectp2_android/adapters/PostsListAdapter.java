package com.example.projectp2_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.projectp2_android.entities.Post;
import com.example.projectp2_android.R;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvContent;
        //private final ImageView ivPic;
        private final TextView tvLikes;
        //private ImageButton likeButton;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.tvAuthor = itemView.findViewById(R.id.postsAuthor);
            this.tvContent = itemView.findViewById(R.id.postsText);
            //this.ivPic = itemView.findViewById(R.id.postsImage);
            this.tvLikes = itemView.findViewById(R.id.num_of_likes);
//            Post current = null;
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                current = posts.get(position);
//            }
//            //region like button
//            Post finalCurrent = current;
//            likeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (finalCurrent != null) {
//                        finalCurrent.setLikes(finalCurrent.getLikes() + 1);
//                    }
//                    notifyDataSetChanged();
//                }
//            });

            //region comments recycle layout
//            RecyclerView lstComments = itemView.findViewById(R.id.commentList);
//            final CommentListAdapter comAdapter = new CommentListAdapter(itemView.getContext());
//            lstComments.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//            lstComments.setAdapter(comAdapter);
//            List<Comment> comments = current.getComments();
//            if (comments != null) {
//                comAdapter.setComments(comments);
//            }
//            //endregion
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;

    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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
            //holder.ivPic.setImageResource(current.getPic());
            int numberOfLikes = current.getLikes();
            holder.tvLikes.setText(String.valueOf(numberOfLikes));
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
