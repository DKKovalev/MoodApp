package com.app.MoodApp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Mood mood;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;
    private TypedArray mMenuIcons;

    private ArrayList<NavigationDrawerItem> navigationDrawerItems;
    private NavigationDrawerAdapter adapter;

    final private static int GET_NEW_MOOD = 0000;

    private DatabaseHandler databaseHandler;

    public static ArrayList<Mood> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        databaseHandler = new DatabaseHandler(this);

        //------------------
        //databaseHandler.formatDB();
        //------------------

        list = new ArrayList<Mood>();

        list.addAll(databaseHandler.getMoods());
        HomeFragment.moodAdapter = new MoodAdapter(this, R.layout.list_item,list);

        mTitle = mDrawerTitle = getTitle();

        mMenuTitles = getResources().getStringArray(R.array.menu_items);

        mMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.list_slidermenu);

        navigationDrawerItems = new ArrayList<NavigationDrawerItem>();

        navigationDrawerItems.add(new NavigationDrawerItem(mMenuTitles[0],mMenuIcons.getResourceId(0,-1)));
        navigationDrawerItems.add(new NavigationDrawerItem(mMenuTitles[1],mMenuIcons.getResourceId(1,-1)));
        navigationDrawerItems.add(new NavigationDrawerItem(mMenuTitles[2],mMenuIcons.getResourceId(2,-1)));

        adapter = new NavigationDrawerAdapter(getApplicationContext(),navigationDrawerItems);
        mDrawerList.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,R.string.app_name,R.string.app_name){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null) {
            displayView(0);
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.add:
                Intent addNewMood = new Intent(this, AddActivity.class);
                if(addNewMood.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(addNewMood, GET_NEW_MOOD);
                }
                else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_NEW_MOOD:
                    //----------------------------------------------------------HERE!!--------------------------------------------------------------------------------
                    String moody = data.getStringExtra("mood");
                    ArrayAdapter<Mood>moodArrayAdapter = (ArrayAdapter<Mood>)HomeFragment.moodListView.getAdapter();
                    mood = new Mood();
                    mood.setMood(moody);
                    moodArrayAdapter.add(mood);
                    databaseHandler.addMood(mood);
                    moodArrayAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new SettingsFragment();
                break;
            case 2:
                fragment = new SettingsFragment();
                break;
            default:
                break;
        }

        if (fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            mDrawerList.setItemChecked(position,true);
            mDrawerList.setSelection(position);
            setTitle(mMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}