package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class CommentListActivity extends AppCompatActivity {


    ProgressBar prg;
    RecyclerView recView;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Comment> data = (List<Comment>)msg.obj;
            CommentAdapter adp = new CommentAdapter(CommentListActivity.this,data);
            recView.setAdapter(adp);
            recView.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);

            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prg = findViewById(R.id.progressBarList2);
        recView = findViewById(R.id.recyclerViewListComments);
        recView.setLayoutManager(new LinearLayoutManager(this));
        prg.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        int news_idd  = intent.getIntExtra("news_id",0);
        intent.putExtra("news_id", news_idd);

        NewsRepository repo = new NewsRepository();
        repo.getCommentsByNewsId(((NewsApp) getApplication()).srv, dataHandler, news_idd);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_3, menu);



        MenuItem postCommentsItem = menu.findItem(R.id.postComment);
        postCommentsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {




                Intent intent = new Intent(CommentListActivity.this, PostCommentActivity.class);
                int id = getIntent().getIntExtra("news_id",1);
                intent.putExtra("news_id", id);
                startActivity(intent);



                return true;
            }
        });




        return true;
    }






}