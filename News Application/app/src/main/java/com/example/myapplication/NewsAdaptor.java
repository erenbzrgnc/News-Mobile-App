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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.NewsViewHolder> {

    private Context ctx;
    private List<News> data;

    public NewsAdaptor(Context ctx, List<News> data){
        this.ctx = ctx;
        this.data = data;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root= LayoutInflater.from(ctx).inflate(R.layout.news_row_layout,parent,false);

        NewsViewHolder holder = new NewsViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        holder.txtListTitle.setText(data.get(holder.getAdapterPosition()).getTitle());
        holder.txtListDate.setText(data.get(holder.getAdapterPosition()).getDate());

        NewsApp app = (NewsApp)((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv,data.get(holder.getAdapterPosition()).getImage());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx,NewsActivity.class);
                i.putExtra("id",data.get(holder.getAdapterPosition()).getId());
                String menuCategory = data.get(holder.getAdapterPosition()).getCategoryName();
                i.putExtra("menuCategory", menuCategory);
                ctx.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView txtListTitle;
        TextView txtListDate;
        ImageView imgList;
        ConstraintLayout row;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap)msg.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtListTitle = itemView.findViewById(R.id.txtListTitle);
            txtListDate = itemView.findViewById(R.id.txtListDate);
            imgList = itemView.findViewById(R.id.imgList);
            row = itemView.findViewById(R.id.row_list);

        }

        public void downloadImage(ExecutorService srv, String path){

            if (!imageDownloaded){

                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv,imgHandler,path);



            }



        }

    }



}
