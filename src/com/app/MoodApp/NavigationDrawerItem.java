package com.app.MoodApp;

/**
 * Created by PsichO on 19.02.14.
 */
public class NavigationDrawerItem {
    private String title;
    private int icon;

    public NavigationDrawerItem(){
    }

    public NavigationDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}
