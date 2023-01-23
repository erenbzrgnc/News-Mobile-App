package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    ImageView Img;
    TextView Title;
    TextView Date;
    TextView content;
    News currentNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        int id = getIntent().getIntExtra("id",1);
        String menuCategory = getIntent().getStringExtra("menuCategory");
        getSupportActionBar().setTitle(menuCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Img = findViewById(R.id.imageNews);
        Title = findViewById(R.id.textTitle);
        Date = findViewById(R.id.textDate);
        content = findViewById(R.id.textContent);

        NewsRepository repo = new NewsRepository();
        repo.getNewsById(((NewsApp)getApplication()).srv,dataHandler,id);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);



        MenuItem commentsItem = menu.findItem(R.id.showComments);
        commentsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                Intent intent = new Intent(NewsActivity.this, CommentListActivity.class);
                int id = getIntent().getIntExtra("id",1);
                intent.putExtra("news_id", id);
                startActivity(intent);



                return true;
            }
        });




        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return true;
    }


    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            News news_1 = (News)msg.obj;

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,news_1.getImage());
            Title.setText(news_1.getTitle());
            Date.setText(news_1.getDate());
            content.setText(news_1.getText());

            return true;
        }
    });


    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap)msg.obj;
            Img.setImageBitmap(img);

            return true;
        }
    });









}