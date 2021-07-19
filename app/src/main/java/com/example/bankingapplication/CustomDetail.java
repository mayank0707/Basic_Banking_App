package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.bankingapplication.MainActivity.customerDB;

public class CustomDetail extends AppCompatActivity {
    CustomerListAdapter customerListAdapter;
    int userId;
    String name;
    String email;
    double balance;
    ArrayList<Integer> userIdList;
    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<Double> balanceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);

        try {
            customerDB = this.openOrCreateDatabase("BankingSystem", MODE_PRIVATE, null);
            customerDB.execSQL("CREATE TABLE IF NOT EXISTS customer (userId INTEGER PRIMARY KEY, name VARCHAR, emailId VARCHAR, balance REAL)");

            Cursor c = customerDB.rawQuery("SELECT * FROM customer", null);
            int userIdIndex = c.getColumnIndex("userId");
            int nameIndex = c.getColumnIndex("name");
            int emailIndex = c.getColumnIndex("emailId");
            int balanceIndex = c.getColumnIndex("balance");

            userIdList=new ArrayList<>();
            nameList=new ArrayList<>();
            emailList=new ArrayList<>();
            balanceList=new ArrayList<>();

            if (c.moveToFirst()) {

                do {
                    userId=(Integer.parseInt(c.getString(userIdIndex)));
                    userIdList.add(userId);
                    name=(c.getString(nameIndex));
                    nameList.add(name);
                    email=(c.getString(emailIndex));
                    emailList.add(email);
                    balance=(Double.parseDouble(c.getString(balanceIndex)));
                    balanceList.add(balance);

                } while (c.moveToNext());
                c.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        customerListAdapter=new CustomerListAdapter(this,userIdList,nameList,emailList,balanceList);
        recyclerView.setAdapter(customerListAdapter);

    }
}