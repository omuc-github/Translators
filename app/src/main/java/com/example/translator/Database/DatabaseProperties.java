package com.example.translator.Database;

import android.content.Context;

import java.util.List;

public class DatabaseProperties {

    private Context context;
    private String databaseName;
    private String databasePath;
    private String tableName;
    private List<String> columnsName;

    public DatabaseProperties(Context context, String databaseName, String databasePath, String tableName, List<String> columnsName) {
        this.context = context;
        this.databaseName = databaseName;
        this.databasePath = databasePath;
        this.tableName = tableName;
        this.columnsName = columnsName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(List<String> columnsName) {
        this.columnsName = columnsName;
    }
}
