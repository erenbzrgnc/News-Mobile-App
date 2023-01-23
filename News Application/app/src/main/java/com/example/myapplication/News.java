package com.example.myapplication;
import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class News {

    private int id;
    private String title;
    private String text;
    private String date;
    private String categoryName;
    private String image;

    public News(int id, String title, String text, String date, String categoryName, String image) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.categoryName = categoryName;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {


        String inPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
        String outPattern = "dd/MM/yyyy";
        String outDate="";




        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat(inPattern);

        SimpleDateFormat outFormat = new SimpleDateFormat(outPattern, Locale.getDefault());

        try {
            Date inDate = inFormat.parse(date);
            outDate = outFormat.format(inDate);
            Log.e("TEST", outDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return outDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
