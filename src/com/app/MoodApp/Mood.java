package com.app.MoodApp;

/**
 * Created by PsichO on 26.02.14.
 */
public class Mood {
    String comment="";
    Double coordLat=0.0, coordLong=0.0;
    String mood;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(Double coordLat) {
        this.coordLat = coordLat;
    }

    public Double getCoordLong() {
        return coordLong;
    }

    public void setCoordLong(Double coord) {
        this.coordLong = coord;
    }
}
