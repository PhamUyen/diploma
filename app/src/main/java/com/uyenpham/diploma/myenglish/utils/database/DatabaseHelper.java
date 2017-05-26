package com.uyenpham.diploma.myenglish.utils.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.uyenpham.diploma.myenglish.model.Group;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Ka on 4/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    private String DB_PATH = "data/data/com.uyenpham.diploma.myenglish/";
    private static String DB_NAME = "myenglish.db";
    private SQLiteDatabase myDatabase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.context = context;
        boolean dbexist = checkdatabase();

        if (dbexist) {
        } else {
            LogUtil.d("Database doesn't exist!");

            createDatabse();
        }
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
    }

    public void createDatabse() {

        this.getReadableDatabase();

        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getMyDatabase() {
        return myDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private boolean checkdatabase() {

        boolean checkdb = false;

        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Databse doesn't exist!");
        }

        return checkdb;
    }

    private void copyDatabase() throws IOException {

        AssetManager dirPath = context.getAssets();

        InputStream myinput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myinput.close();
    }

    public void open() {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        myDatabase.close();
        super.close();
    }

    public ArrayList<Group> getAllGroup(){
        open();
        ArrayList<Group> list = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary_group" ;
        Cursor cursor = myDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Group group = new Group(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2));
                list.add(group);
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<Vocabulary> getListVocalByGroupID(String groupID){
        open();
        ArrayList<Vocabulary>list = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary_detail where group_id ='"+groupID+"'" ;
        Cursor cursor = myDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Vocabulary vocabulary = new Vocabulary(cursor.getString(0),
                        cursor.getInt(1), cursor.getString(2),cursor.getString(3)
                ,cursor.getInt(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                list.add(vocabulary);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Vocabulary> getAllWord(){
        open();
        ArrayList<Vocabulary>list = new ArrayList<>();
        String sql = "SELECT * FROM vocabulary_detail" ;
        Cursor cursor = myDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Vocabulary vocabulary = new Vocabulary(cursor.getString(0),
                        cursor.getInt(1), cursor.getString(2),cursor.getString(3)
                        ,cursor.getInt(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                list.add(vocabulary);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
