package com.example.davidsalmon.gettaxic.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.davidsalmon.gettaxic.R;

public class MainActivity extends Activity {

    EditText PhoneNumber;
    Button Continue;
    TextView PhoneNumberError;
    Spinner Prefix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        PhoneNumber = findViewById(R.id.PhoneNumberMain);
        PhoneNumber.setText("");
        Continue = findViewById(R.id.ContinueMain);
        PhoneNumberError = findViewById(R.id.PhoneNumberError);
        PhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PhoneNumberError.setText("");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(v == Continue) {
                    if (PhoneNumber.getText().toString().length() < 10) {
                        PhoneNumberError.setText("Phone Number is incorrect");
                        return;
                    }
//                    else if ((!PhoneNumber.getText().toString().startsWith("050")) || (!PhoneNumber.getText().toString().startsWith("052"))
//                            || (!PhoneNumber.getText().toString().startsWith("053")) || (!PhoneNumber.getText().toString().startsWith("054"))
//                            || (!PhoneNumber.getText().toString().startsWith("055"))) {
//                        PhoneNumberError.setText("e.g. start with 054,052,050");
//                        return;
//                    }
                    Intent intent = new Intent(MainActivity.this,AddNewCustomer.class);
                    intent.putExtra("phoneNumber", PhoneNumber.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
