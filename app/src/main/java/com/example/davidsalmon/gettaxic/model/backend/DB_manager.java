package com.example.davidsalmon.gettaxic.model.backend;

import android.content.ContentValues;

import com.example.davidsalmon.gettaxic.model.datasource.FireBase_DBManager;
import com.example.davidsalmon.gettaxic.model.entities.Customer;
import com.example.davidsalmon.gettaxic.model.entities.Travel;

public interface DB_manager {
    String checkIfCustomerAdded(String Id);
    String checkIfTravelAdded(String Id);
    void addNewCustomer(final Customer customer, final FireBase_DBManager.Action<Long> action);
    //void addNewCustomer(String Id,Customer customer);
    void addNewTravel(String Id, Travel travel);
}
