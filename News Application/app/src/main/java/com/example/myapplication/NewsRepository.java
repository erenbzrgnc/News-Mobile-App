package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {



    public void getAllData(ExecutorService srv, Handler uiHandler){

        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject obj = new JSONObject(buffer.toString());
                JSONArray arr = obj.getJSONArray("items");

                List<News> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);




                   News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("date"),
                            current.getString("categoryName"),
                            current.getString("image"));
                    data.add(news);

                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }


    public void getNewsByCategoryId(ExecutorService srv, Handler uiHandler, int ind){

        srv.execute(()->{
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(ind+1));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject obj = new JSONObject(buffer.toString());
                JSONArray arr = obj.getJSONArray("items");

                List<News> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);



                    News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("date"),
                            current.getString("categoryName"),
                            current.getString("image"));
                    data.add(news);

                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }






    public void getNewsById(ExecutorService srv, Handler uiHandler,int idt){


        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(idt));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }




                JSONObject current = new JSONObject(buffer.toString());
                JSONArray arr = current.getJSONArray("items");
                JSONObject firstNews = arr.getJSONObject(0);



                News news = new News(firstNews.getInt("id"),
                        firstNews.getString("title"),
                        firstNews.getString("text"),
                        firstNews.getString("date"),
                        firstNews.getString("categoryName"),
                        firstNews.getString("image"));


                Message msg = new Message();
                msg.obj = news;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }


    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int newsIndex) {

        srv.execute(() -> {
            try {



                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(newsIndex));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject obj = new JSONObject(buffer.toString());
                JSONArray arr = obj.getJSONArray("items");

                List<Comment> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);


                    Comment comment = new Comment(current.getInt("id"),
                            current.getInt("news_id"),
                            current.getString("text"),
                            current.getString("name"));

                    data.add(comment);

                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });




    }


    public void postComment(ExecutorService srv,Handler uiHandler, JSONObject comment){

        srv.execute(()->{
            try {

                String urlString = "http://10.3.0.14:8080/newsapp/savecomment";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept","application/json");

                conn.setDoOutput(true);
                conn.setDoInput(true);



                OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
                wr.write(comment.toString());
                wr.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                wr.close();
                reader.close();

                JSONObject response = new JSONObject(sb.toString());

                int serviceMessageCode = response.getInt("serviceMessageCode");
                String serviceMessageCodeString =  Integer.toString(serviceMessageCode);


                if (serviceMessageCodeString.equals("1")) {

                    Message msg = new Message();
                    msg.what = 1;
                    uiHandler.sendMessage(msg);
                } else {

                    Message msg = new Message();
                    msg.what = 0;
                    uiHandler.sendMessage(msg);

                }







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }
}
