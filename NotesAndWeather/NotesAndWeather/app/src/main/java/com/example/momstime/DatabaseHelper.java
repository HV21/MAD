package com.example.momstime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.momstime.Models.NotesModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    final public static String database_name = "Dairy.db";
    final public static String table_name = "SavedNotes";
    final public static String table_schedules = "SavedSchedules";
    final public static String table_reminders = "SavedReminders";
    final public static String COL_1 = "title";
    final public static String COL_2 = "desc";
    final public static String COL_3 = "date";
    final public static String COL_4 = "status";
    final public static String COL_5 = "ID";
    Context context;


    public DatabaseHelper(@Nullable Context context ) {
        super(context, database_name, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + table_name + "(" + COL_1 + " TEXT ," + COL_2 + " TEXT ," + COL_3 + " TEXT " + ")");
        db.execSQL("CREATE TABLE " + table_schedules + "(" + COL_1 + " TEXT ," + COL_2 + " TEXT ," + COL_3 + " TEXT ," + COL_4 + " TEXT ," + COL_5 + " TEXT " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + table_name);
        db.execSQL(" DROP TABLE IF EXISTS " + table_schedules);
        onCreate(db);
    }



    public void insertNotesData(NotesModel notesModel) {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COL_1, notesModel.getTitle());
        values.put(COL_2, notesModel.getDesc());
        values.put(COL_3, notesModel.getDate());
        sqLiteDatabase.insert(table_name, null, values);

        sqLiteDatabase.close();



    }




    public void insertSchedulesData(NotesModel notesModel) {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COL_1, notesModel.getTitle());
        values.put(COL_2, notesModel.getDesc());
        values.put(COL_3, notesModel.getDate());
        values.put(COL_4, notesModel.getStatus());
        values.put(COL_5, notesModel.getId());
        sqLiteDatabase.insert(table_schedules, null, values);

        sqLiteDatabase.close();



    }


    public ArrayList<NotesModel> fetchNotesData() {
        ArrayList<NotesModel> notesModelArrayList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String fetchQuery = " SELECT * FROM " + table_name;
        Cursor cursor = database.rawQuery(fetchQuery, null);
        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {
                    NotesModel notesModel = new NotesModel();
                    String title = cursor.getString(cursor.getColumnIndex(COL_1));
                    String desc = cursor.getString(cursor.getColumnIndex(COL_2));
                    String date = cursor.getString(cursor.getColumnIndex(COL_3));


                    notesModel.setTitle(title);
                    notesModel.setDesc(desc);
                    notesModel.setDate(date);

                    notesModelArrayList.add(notesModel);


                }
                while (cursor.moveToNext());
            }

        }

        return notesModelArrayList;
    }






    public ArrayList<NotesModel> fetchSchedulesData() {
        ArrayList<NotesModel> notesModelArrayList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String fetchQuery = " SELECT * FROM " + table_schedules;
        Cursor cursor = database.rawQuery(fetchQuery, null);
        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {
                    NotesModel notesModel = new NotesModel();
                    String title = cursor.getString(cursor.getColumnIndex(COL_1));
                    String desc = cursor.getString(cursor.getColumnIndex(COL_2));
                    String date = cursor.getString(cursor.getColumnIndex(COL_3));
                    String status = cursor.getString(cursor.getColumnIndex(COL_4));
                    String id = cursor.getString(cursor.getColumnIndex(COL_5));


                    notesModel.setTitle(title);
                    notesModel.setDesc(desc);
                    notesModel.setDate(date);
                    notesModel.setStatus(status);
                    notesModel.setId(id);

                    notesModelArrayList.add(notesModel);


                }
                while (cursor.moveToNext());
            }

        }

        return notesModelArrayList;
    }





    public void updateScheduleStatus(String id){



        SQLiteDatabase database = this.getWritableDatabase();
        String status="done";
        String query = "UPDATE SavedSchedules SET  status='" + status + "'  WHERE ID='" + id + "'";
        database.execSQL(query);
        database.close();
    }
}
