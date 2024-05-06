package com.example.projectp2_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import com.example.projectp2_android.entities.Comment;
import com.example.projectp2_android.R;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvContent;
        private Comment current;
        private boolean likeIsPressed = false;

        public CommentViewHolder(View itemView) {
            super(itemView);
            this.tvAuthor = itemView.findViewById(R.id.commentingUser);
            this.tvContent = itemView.findViewById(R.id.commentText);
//            int position = getAdapterPosition();
//            current = comments.get(position);
        }
    }

    private final LayoutInflater mInflater;
    private List<Comment> comments;
    private Context context;

    public CommentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (comments != null) {
            final Comment current = comments.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getText());
        }
    }

    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        return 0;
    }

    public void setComments(List<Comment> c) {
        comments = c;
        notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return comments;
    }
}
