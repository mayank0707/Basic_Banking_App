package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    Button viewCustomers;
    Button transactionHist;
    public static SQLiteDatabase customerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            customerDB = this.openOrCreateDatabase("BankingSystem", MODE_PRIVATE, null);
            customerDB.execSQL("CREATE TABLE IF NOT EXISTS customer (userId INTEGER PRIMARY KEY, name VARCHAR, emailId VARCHAR, balance REAL)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(101,'Shyam','shyam0205@gmail.com',4500.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(102,'Raju','rairaju0411@gmail.com',7500.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(103,'Kajal','kajal1212@gmail.com',5000.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(104,'Divya','divyagoel21@gmail.com',8500.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(105,'Raghav','raghav06@gmail.com',3000.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(106,'Ajay','kumarajay07@gmail.com',9000.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(107,'Preeti','prettypreeti03@gmail.com',5500.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(108,'Jatin','jatin0107@gmail.com',6000.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(109,'Nancy','nansee2801@gmail.com',9000.00)");
            customerDB.execSQL("INSERT INTO customer(userId,name,emailId,balance) VALUES(110,'Nitesh','niteshgupta24@gmail.com',8000.00)");



        }catch (Exception e){
            e.printStackTrace();
        }
        viewCustomers=findViewById(R.id.viewCustomers);
        viewCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CustomDetail.class);
                startActivity(intent);

            }
        });
        transactionHist=(Button)findViewById(R.id.transactHistoryBTN);
        transactionHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Transaction_History.class);
                startActivity(intent);
            }
        });

    }
}