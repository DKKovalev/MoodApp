package com.app.MoodApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;



public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "db_ver_" + DATABASE_VERSION + ".db";

    private static final String MOODS = "moods";

    private static final String COMMENT = "comment";
    private static final String MOOD = "mood";
    private static final String COORDSLAT = "coordLat";
    private static final String COORDSLNG = "coordLng";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EVENTS = "CREATE TABLE "
                + MOODS + "("
                + COMMENT + " STRING,"
                + MOOD + " STRING,"
                + COORDSLAT + " STRING,"
                + COORDSLNG + " STRING)" ;
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MOODS);
        onCreate(db);
    }

    public void addMood(Mood mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("PRAGMA synchronous=OFF");
            db.setLockingEnabled(false);
            db.beginTransaction();
            int newId = db.update(MOODS, getContentValues(mood), "mood='" + mood.getMood() + "'", null);
            if (newId == 0) {
                db.insert(MOODS, null, getContentValues(mood));
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
            db.setLockingEnabled(true);
            db.execSQL("PRAGMA synchronous=NORMAL");
        }

        db.close();
    }

    private ContentValues getContentValues(Mood mood) {
        ContentValues values = new ContentValues();
        values.put(COMMENT, mood.getComment());
        values.put(MOOD, mood.mood);
        values.put(COORDSLAT, mood.coordLat);
        values.put(COORDSLNG, mood.coordLong);
        return values;
    }

    public ArrayList<Mood> getMoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Mood> notes = new ArrayList<Mood>();
        Cursor notesCursor = db.rawQuery("SELECT * FROM " + MOODS, null);
        if (notesCursor.moveToFirst()) {
            do {
                Mood mood = new Mood();
                mood.setComment(notesCursor.getString(0));
                mood.setMood(notesCursor.getString(1));
                mood.setCoordLat(notesCursor.getDouble(2));
                mood.setCoordLong(notesCursor.getDouble(3));
                notes.add(mood);
            } while (notesCursor.moveToNext());
        }
        notesCursor.close();
        db.close();
        return notes;
    }

    public void formatDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MOODS, null, null);
        db.close();
    }

    public Mood getMood(String title) throws Exception {
        ArrayList<Mood> moods =getMoods();
        for (Mood mood : moods)
            if (mood.getMood().equals(title)) {
                return mood;
            }
        throw new Exception("Event  with title=" + title + " not found");
    }
}