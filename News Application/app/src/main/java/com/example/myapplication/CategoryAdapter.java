package com.example.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryAdapter extends FragmentStateAdapter {


    public CategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        NewsFragment nf = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("index", position);
        nf.setArguments(args);
        return nf;

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
