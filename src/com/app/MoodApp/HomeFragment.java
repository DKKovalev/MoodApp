package com.app.MoodApp;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class HomeFragment extends ListFragment {

    public static MoodAdapter moodAdapter;
    public static ListView moodListView;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        moodListView = (ListView)rootView.findViewById(android.R.id.list);

        setListAdapter(moodAdapter);


        return rootView;
    }
}