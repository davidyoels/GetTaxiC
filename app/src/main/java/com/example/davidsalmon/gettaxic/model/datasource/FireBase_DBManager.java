package com.example.davidsalmon.gettaxic.model.datasource;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.davidsalmon.gettaxic.R;
import com.example.davidsalmon.gettaxic.model.backend.DB_manager;
import com.example.davidsalmon.gettaxic.model.entities.Customer;
import com.example.davidsalmon.gettaxic.model.entities.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBase_DBManager implements DB_manager {

    static DatabaseReference CustomerRef;
    static List<Customer> studentList;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        CustomerRef = database.getReference("Customer");
        studentList = new ArrayList<>();
    }

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    @Override
    public String checkIfCustomerAdded(String Id) {
        //checking if the customer is actually added to the DB.
        String id = FirebaseDatabase.getInstance().getReference("Customer").child(Id).getKey();
        return id;
    }

    @Override
    public void addNewTravel(final String Id, final Travel travel) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Travel");
        DatabaseReference userNameRef = rootRef.child(Id);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("Travel/" + Id);
                    databaseReference.setValue(travel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public String checkIfTravelAdded(String Id) {
        String id = FirebaseDatabase.getInstance().getReference("Travel").child(Id).getKey();
        return id;
    }

    @Override
    public void addNewCustomer(final Customer customer, final Action<Long> action) {

        Log.d("****************", "addNewCustomerFB");
        String key = customer.getId();
        CustomerRef.child(key).setValue(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(Long.getLong("23"));
                action.onProgress("upload student data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload student data", 100);
            }
        });

    }
/*
    public static void addStudent(final Student student, final Action<Long> action) {
        if (student.getImageLocalUri() != null) {         // upload image
            StorageReference imagesRef = FirebaseStorage.getInstance().getReference();
            imagesRef = imagesRef.child("images").child(System.currentTimeMillis() + ".jpg");
            imagesRef.putFile(student.getImageLocalUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    action.onProgress("upload student data", 90);
                    // Get a URL to the uploaded content
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    student.setImageFirebaseUrl(downloadUrl.toString());
                    // add student
                    addStudentToFirebase(student, action);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    action.onFailure(exception);
                    action.onProgress("error upload student image", 100);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double uploadBytes = taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                    double progress = (90.0 * uploadBytes);
                    action.onProgress("upload image", progress);
                }
            });
        } else action.onFailure(new Exception("select image first ..."));
    }
*/
}
