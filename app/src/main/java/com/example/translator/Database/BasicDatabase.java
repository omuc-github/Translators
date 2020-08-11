package com.example.translator.Database;

import java.util.List;

public interface BasicDatabase {

    DatabaseProperties getProperties();

    void Add(Object object, Database database);

    void Delete(Object object, Database database);

    void Update(Object object, Database database);

    Data Search(Object object);

    List<Object> SearchAll(Database database);

}
