package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostCommentActivity extends AppCompatActivity {



    TextView name;
    TextView textBlock;
    ProgressDialog progressDialog;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if(msg.what == 1){

                Intent commentsIntent = new Intent(PostCommentActivity.this, CommentListActivity.class);
                commentsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                int id = getIntent().getIntExtra("news_id",1);
                commentsIntent.putExtra("news_id", id);
                startActivity(commentsIntent);

            }











            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post Comment");

        int id = getIntent().getIntExtra("id",1);


        name = findViewById(R.id.editTextName);
        textBlock = findViewById(R.id.editTextBlock);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        Button postButton = (Button)findViewById(R.id.buttonPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.editTextName)).getText().toString();
                String text = ((EditText) findViewById(R.id.editTextBlock)).getText().toString();
                int newsId = getIntent().getIntExtra("news_id", 1);
                TextView errorMessage = (TextView) findViewById(R.id.text_error);
                if (username.isEmpty()) {
                    errorMessage.setText("Please type your name");
                    errorMessage.setVisibility(View.VISIBLE);

                } else if (text.isEmpty()) {
                    errorMessage.setText("Please  type your comment");
                    errorMessage.setVisibility(View.VISIBLE);
                } else {

                    errorMessage.setVisibility(View.INVISIBLE);

                    progressDialog.show();



                    String sNewsId =Integer.toString(newsId);
                    try {
                        JSONObject comment = new JSONObject();

                        comment.put("name", username);
                        comment.put("text", text);
                        comment.put("news_id", newsId);

                        NewsRepository repo = new NewsRepository();

                        repo.postComment(((NewsApp) getApplication()).srv, dataHandler, comment);


                    } catch (JSONException e) {
                        // Handle JSON exception
                    }
                }

            }


        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return true;
    }





}