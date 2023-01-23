package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    List<News> data;
    ProgressBar prg;
    RecyclerView recView;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            data = (List<News>)msg.obj;
            NewsAdaptor adp = new NewsAdaptor(getContext(),data);
            recView.setAdapter(adp);


            recView.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);

            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news, container, false);

        data =  new ArrayList<>();

        Bundle args = getArguments();
        int index = args.getInt("index", 0);
        prg = v.findViewById(R.id.progressBarList);
        recView = v.findViewById(R.id.recyclerViewList);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        NewsAdaptor adapter = new NewsAdaptor( getContext(), data);
        recView.setAdapter(adapter);
        recView.setLayoutManager(layoutManager);


        recView.setVisibility(View.INVISIBLE);
        prg.setVisibility(View.VISIBLE);



        NewsApp app = (NewsApp)getActivity().getApplication();
        NewsRepository repo = new NewsRepository();

        repo.getNewsByCategoryId(app.srv, dataHandler, index);



        // Inflate the layout for this fragment
        return v;
    }
}