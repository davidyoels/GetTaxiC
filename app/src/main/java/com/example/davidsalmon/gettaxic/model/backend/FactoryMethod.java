package com.example.davidsalmon.gettaxic.model.backend;

import com.example.davidsalmon.gettaxic.model.datasource.FireBase_DBManager;

public class FactoryMethod {
    static DB_manager manager = null ;

    public static DB_manager getManager() {
        if (manager == null)
            //manager = new List_DBManager();
            manager = new FireBase_DBManager();
        return manager;
    }
}
