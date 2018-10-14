package com.example.davidsalmon.gettaxic.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidsalmon.gettaxic.R;
import com.example.davidsalmon.gettaxic.model.backend.FactoryMethod;
import com.example.davidsalmon.gettaxic.model.entities.Customer;

import java.net.IDN;

public class AddNewCustomer extends Activity {

    EditText PrivateName;
    EditText FamilyName;
    EditText Id;
    EditText Email;
    EditText CreditCard;
    Button GetStarted;
    String PhoneNumber;
    TextView IdError;
    TextView EmailError;
    TextView CreditCardError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);
        PhoneNumber = getIntent().getStringExtra("phoneNumber");
        findViews();
    }

    private void findViews() {
        PrivateName = findViewById(R.id.PrivateNameAdd);
        FamilyName = findViewById(R.id.FamilyNameAdd);
        Id = findViewById(R.id.IdAdd);
        Email = findViewById(R.id.EmailAdd);
        CreditCard = findViewById(R.id.CreditCardAdd);
        GetStarted = findViewById(R.id.GetStartedAdd);
        IdError = findViewById(R.id.IdError);
        EmailError = findViewById(R.id.EmailError);
        CreditCardError = findViewById(R.id.CreditCardError);
        OnChangeToClearErrorsText();

        GetStarted.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if (v == GetStarted) {
                    if(checkData())
                        return;
                    final Customer customer = new Customer(PrivateName.getText().toString(), FamilyName.getText().toString(), PhoneNumber, Email.getText().toString(), CreditCard.getText().toString());
                    //customer.setId(Id.getText().toString());
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected void onPostExecute(String idResult) {
                            //super.onPostExecute(idResult);
                            Log.d("**************", idResult);
                           if (Long.parseLong(idResult) > 0)
                                Toast.makeText(getBaseContext(), "insert id: " + idResult, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddNewCustomer.this,OrderTravel.class);
                            //customerName, String customerPhone, String customerEmail) {
                            intent.putExtra("customerName", customer.getPrivateName() + customer.getFamilyName());
                            intent.putExtra("customerPhone", customer.getPhoneNumber());
                            intent.putExtra("customerEmail", customer.getEmail());
                            startActivity(intent);
                        }

                        @Override
                        protected String doInBackground(Void... params) {
                            FactoryMethod.getManager().addNewCustomer(Id.getText().toString(), customer);
                            return FactoryMethod.getManager().checkIfCustomerAdded(Id.getText().toString());
                        }
                    }.execute();
                }
            }
        });

    }
    @SuppressLint("SetTextI18n")
    boolean checkData()
    {
     int i=0;
         if((Id.getText().toString().length() < 8)) {
             IdError.setText("Id cannot be lower then 8 digits");
             i=1;
         }
         if(!Email.getText().toString().contains("@")) {
             EmailError.setText("email must contain '@'");
             i = 1;
         }
         if(CreditCard.getText().toString().length()< 8) {
             CreditCardError.setText("CreditCard must containt 8 digits");
             i = 1;
         }
         return i==1;
    }
    void OnChangeToClearErrorsText() {
        Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 IdError.setText("");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EmailError.setText("");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        CreditCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CreditCardError.setText("");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
