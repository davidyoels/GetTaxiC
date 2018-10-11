package com.example.davidsalmon.gettaxic.model.backend;

import android.content.ContentValues;

import com.example.davidsalmon.gettaxic.model.entities.Customer;

public interface DB_manager {
    String checkIfCustomerAdded(String Id);
    void addNewCustomer(String Id,Customer customer);
    String addNewTravel();
}
