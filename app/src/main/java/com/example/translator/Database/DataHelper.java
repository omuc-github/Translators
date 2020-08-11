package com.example.translator.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHelper {

    private static DataHelper dataHelper;

    private Database dictionary;

    public static DataHelper init(Context context){
        return dataHelper != null ? dataHelper : (dataHelper = new DataHelper(context));
    }

    private DataHelper(Context context) {
        this.dictionary = new Database(dictionary_BasicDatabase(context));
    }

    public static DataHelper getDataHelper() { return dataHelper; }

    public Database getDictionary() { return dictionary; }

    private BasicDatabase dictionary_BasicDatabase(final Context context){
        return new BasicDatabase() {
            DatabaseProperties properties;
            @SuppressLint("SdCardPath")
            @Override
            public DatabaseProperties getProperties() {
                if (context != null) {
                    return properties != null ? properties : (properties = new DatabaseProperties(
                            context,
                            "base",
                            android.os.Build.VERSION.SDK_INT >= 17 ? context.getApplicationInfo().dataDir + "/databases/" : "/data/data/" + context.getPackageName() + "/databases/",
                            "lugat",
                            new ArrayList<>(Arrays.asList("id","english", "uzbek"))));
                }
                return null;
            }

            @Override
            public void Add(Object object, Database database) {
                Data data = (Data) object;

                SQLiteStatement sqLiteStatement = database.getSqLiteDatabase().compileStatement("Insert into " + database.getProperties().getProperties().getTableName() + " (" +
                        database.getProperties().getProperties().getColumnsName().get(1) + "," +
                        database.getProperties().getProperties().getColumnsName().get(2) + ") VALUES(?, ?)");

                sqLiteStatement.bindString(1, data.getEnglish());
                sqLiteStatement.bindString(2, data.getUzbek());
                sqLiteStatement.executeInsert();

            }

            @Override
            public void Delete(Object object, Database database) {
                Data data = (Data) object;

                database.getSqLiteDatabase().execSQL(
                        "Delete From " + database.getProperties().getProperties().getTableName() +
                                " Where " + database.getProperties().getProperties().getColumnsName().get(0) + " = '" + data.getId() + "'");
            }

            @Override
            public void Update(Object object, Database database) {
                Data data = (Data) object;

                database.getSqLiteDatabase().execSQL(
                        "Update " + database.getProperties().getProperties().getTableName() + " Set " +
                                database.getProperties().getProperties().getColumnsName().get(1) + " = '" + data.getEnglish() + "'," +
                                database.getProperties().getProperties().getColumnsName().get(2) + " = '" + data.getUzbek() + "'" +
                        " Where " + database.getProperties().getProperties().getColumnsName().get(0) + " = " + data.getId() + "");
            }

            @Override
            public Data Search(Object object) {return null;}

            @Override
            public List<Object> SearchAll(Database database) {
                String cmd = "SELECT * FROM " + database.getProperties().getProperties().getTableName();
                Cursor cursor = database.getSqLiteDatabase().rawQuery(cmd, null);

                List<Data> dataList = new ArrayList<>();


                while (cursor.moveToNext()){
                    if (cursor.getColumnCount() != 0) {
                        dataList.add(new Data(
                                cursor.getString(cursor.getColumnIndex(database.getProperties().getProperties().getColumnsName().get(0))),
                                cursor.getString(cursor.getColumnIndex(database.getProperties().getProperties().getColumnsName().get(1))),
                                cursor.getString(cursor.getColumnIndex(database.getProperties().getProperties().getColumnsName().get(2)))));
                    } else {
                        Toast.makeText(database.getProperties().getProperties().getContext(), "Database empty", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                cursor.close();
                return new ArrayList<Object>(dataList);
            }
        };
    }
}
