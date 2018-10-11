package com.example.davidsalmon.gettaxic.model.datasource;

import android.content.ContentValues;
import android.widget.Toast;

import com.example.davidsalmon.gettaxic.R;
import com.example.davidsalmon.gettaxic.model.backend.DB_manager;
import com.example.davidsalmon.gettaxic.model.entities.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBase_DBManager implements DB_manager {
    @Override
    public String checkIfCustomerAdded(String Id) {
        //checking if the customer is actually added to the DB.
        String id = FirebaseDatabase.getInstance().getReference("Customer").child(Id).getKey();
        return id;
    }

    @Override
    public void addNewCustomer(final String Id, final Customer customer) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Customer");
        DatabaseReference userNameRef = rootRef.child(Id);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("Customer/" + Id);
                    databaseReference.setValue(customer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

//    @Override
//    public String addNewCustomer(String Id,Customer customer) {
//        boolean customerExist = checkIfCustomerExist(Id);
//        if(customerExist)
//            return "-1";
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Customer/" + Id);
//        databaseReference.setValue(customer);
//        //checking if the customer is actually added to the DB.
//        return FirebaseDatabase.getInstance().getReference("Customer").child(Id).getKey();
//    }

    @Override
    public String addNewTravel() {
      return null;
    }
}
