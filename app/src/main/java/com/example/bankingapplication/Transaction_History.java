package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import static com.example.bankingapplication.MainActivity.customerDB;

public class Transaction_History extends AppCompatActivity {
    TransactionListAdapter transactionListAdapter;
    int transactId;
    String senderName;
    String recipientName;
    double amount;
    String time;
    ArrayList<Integer> transactIdList;
    ArrayList<String> senderNameList;
    ArrayList<String> recipientNameList;
    ArrayList<Double> amountList;
    ArrayList<String> timeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        try {
            customerDB = this.openOrCreateDatabase("BankingSystem", MODE_PRIVATE, null);
            customerDB.execSQL("CREATE TABLE IF NOT EXISTS transferRecord (transferid INTEGER PRIMARY KEY, senderName VARCHAR, recipientName VARCHAR, amount REAL,transferTime currentdateandtime)");

            transactIdList=new ArrayList<>();
            senderNameList=new ArrayList<>();
            recipientNameList=new ArrayList<>();
            amountList=new ArrayList<>();
            timeList=new ArrayList<>();

            Cursor c4 = customerDB.rawQuery("SELECT * FROM transferRecord", null);
            int idIndex = c4.getColumnIndex("transferid");
            int senderNameIndex=c4.getColumnIndex("senderName");
            int recipientNameIndex=c4.getColumnIndex("recipientName");
            int amountIndex = c4.getColumnIndex("amount");
            int timeIndex = c4.getColumnIndex("transferTime");
            if (c4.moveToFirst()) {
                do {
                    transactId = c4.getInt(idIndex);
                    Log.i("Transfer Id", String.valueOf(transactId));
                    senderName=c4.getString(senderNameIndex);
                    recipientName=c4.getString(recipientNameIndex);
                    amount=c4.getDouble(amountIndex);
                    time=c4.getString(timeIndex);

                    transactIdList.add(transactId);
                    senderNameList.add(senderName);
                    recipientNameList.add(recipientName);
                    amountList.add(amount);
                    timeList.add(time);
                } while (c4.moveToNext());
                c4.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.transactionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        transactionListAdapter=new TransactionListAdapter(this,transactIdList,senderNameList,recipientNameList,amountList,timeList);
        recyclerView.setAdapter(transactionListAdapter);
    }
}