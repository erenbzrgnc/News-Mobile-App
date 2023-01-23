package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context ctx;
    private List<Comment> data;



    public CommentAdapter(Context ctx, List<Comment> data){
        this.ctx = ctx;
        this.data = data;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root= LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout,parent,false);

        CommentViewHolder holder = new CommentViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        holder.txtName.setText(data.get(holder.getAdapterPosition()).getName());
        holder.txtComment.setText(data.get(holder.getAdapterPosition()).getText());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtComment;

        ConstraintLayout row;





        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.textName);
            txtComment = itemView.findViewById(R.id.textComment);

            row = itemView.findViewById(R.id.row_list_comment);

        }





        }

    }
