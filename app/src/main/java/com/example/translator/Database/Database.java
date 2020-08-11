package com.example.translator.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Database  extends SQLiteOpenHelper {

    @SuppressLint("StaticFieldLeak")
    private SQLiteDatabase sqLiteDatabase;

    private List<Object> dataList;

    private Context context;

    private BasicDatabase properties;

    public Object getDataList() {
        return dataList != null ? dataList : SearchAll();
    }

    public Database setDataList(List<Object> dataList) {
        this.dataList = dataList;
        return this;
    }

    public Database(BasicDatabase properties){

        super(properties.getProperties().getContext(), properties.getProperties().getDatabaseName(), null, 1);

        this.LoadData(context, properties);

        this.CreateDatabase();

    }

    private void LoadData(Context context, BasicDatabase properties){
        this.properties = properties;
        this.context = properties.getProperties().getContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void OpenDataBase(){
        String dbPath;
        dbPath = context.getDatabasePath(properties.getProperties().getDatabaseName() + ".db").getPath();
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            return;
        }
        sqLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void CloseDataBase(){
        if (sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    private boolean CopyDatabase(Context context, InputStream inputStream){
        try{

            String outFileName = properties.getProperties().getDatabasePath() + properties.getProperties().getDatabaseName() + ".db";
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.i("MainActivity", "Database Copied");
            Toast.makeText(context, "Database Copied", Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void CreateDatabase(){
        File database = this.context.getDatabasePath(properties.getProperties().getDatabaseName() + ".db");

        if (!database.exists()){
            this.getReadableDatabase();
            try {
                if (this.CopyDatabase(this.context, context.getAssets().open(properties.getProperties().getDatabaseName() + ".db"))){
                    Toast.makeText(this.context, "Copy Database Succes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.context, "Copy Data Error", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}

    public boolean Add(Data data) {
        if (checkData(data)) {
            // ma'lumotlarni bazaga qo'shadi
            try {
                OpenDataBase();

                properties.Add(data, this);

                CloseDataBase();

                Toast.makeText(context, "Record saved", Toast.LENGTH_SHORT).show();
                return true;
            }catch (Exception ex){

                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
                if (sqLiteDatabase.isOpen()){
                CloseDataBase();}
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean Update(Data data) {

        try {

            OpenDataBase();

            properties.Update(data, this);

            CloseDataBase();

            return true;
        } catch (Exception e){

            e.printStackTrace();

            CloseDataBase();

            return false;
        }
    }

    public void Delete(Data data) {

        try {

            OpenDataBase();

            properties.Delete(data, this);

            CloseDataBase();

        } catch (Exception e){

            e.printStackTrace();

            CloseDataBase();

        }
    }

    public Object SearchAll(){

        try {

            OpenDataBase();

            this.dataList = properties.SearchAll(this);

            CloseDataBase();

            return dataList;

        } catch (Exception e){

            e.printStackTrace();

            CloseDataBase();

            return null;
        }
    }

    private boolean checkData(Data data){
        if (data.getEnglish().equals("")) {
            Toast.makeText(context, "Name can be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    BasicDatabase getProperties() {
        return properties;
    }
}
