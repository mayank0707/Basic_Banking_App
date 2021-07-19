package com.example.bankingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.bankingapplication.MainActivity.customerDB;
import static com.example.bankingapplication.CustomerListAdapter.senderId;
import static com.example.bankingapplication.CustomerListAdapter.senderName;

public class TransactionProcess extends AppCompatActivity {
    String recipientName;
    int recipientId;
    double recipientBal;
    ArrayList<String> nameSpinner;
    EditText balanceET;
    double leftBalance;
    Button transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_process);
        TextView userTV=findViewById(R.id.userIdTV);
        TextView nameTV=findViewById(R.id.nameTV);
        TextView balanceTV=findViewById(R.id.balanceTV);
        balanceET=findViewById(R.id.balanceET);

        try {
            customerDB = this.openOrCreateDatabase("BankingSystem", MODE_PRIVATE, null);
            customerDB.execSQL("CREATE TABLE IF NOT EXISTS customer (userId INTEGER PRIMARY KEY, name VARCHAR, emailId VARCHAR, balance REAL)");
            Cursor c1 = customerDB.rawQuery("SELECT * FROM customer WHERE userId="+senderId, null);
            int userIdIndex = c1.getColumnIndex("userId");
            int nameIndex = c1.getColumnIndex("name");
            int balanceIndex = c1.getColumnIndex("balance");
            if(c1.moveToFirst()){
                userTV.setText(Integer.toString(c1.getInt(userIdIndex)));
                nameTV.setText(c1.getString(nameIndex));
                balanceTV.setText(String.valueOf(c1.getDouble(balanceIndex)));
            }
            Cursor c2= customerDB.rawQuery("SELECT * FROM customer WHERE userId!="+senderId, null);
            int nameSpinnerIndex=c2.getColumnIndex("name");
            nameSpinner=new ArrayList<>();
            if(c2.moveToFirst()){
                do {

                    recipientName=(c2.getString(nameSpinnerIndex));
                    nameSpinner.add(recipientName);


                } while (c2.moveToNext());
            }
            c2.close();


            Spinner dynamicSpinner = (Spinner) findViewById(R.id.recipientSpinner);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, nameSpinner);

            dynamicSpinner.setAdapter(adapter);

            dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        recipientName = (String) parent.getItemAtPosition(position);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

            customerDB.execSQL("CREATE TABLE IF NOT EXISTS transferRecord (transferid INTEGER PRIMARY KEY, senderName VARCHAR, recipientName VARCHAR, amount REAL,transferTime currentdateandtime)");

            transaction=findViewById(R.id.transactionBTN);
            transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double curBal = Double.parseDouble(String.valueOf(balanceTV.getText()));
                    //customerDB.execSQL("DELETE FROM transferRecord");
                    if(TextUtils.isEmpty(balanceET.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Amount ", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        double balance = Double.parseDouble(String.valueOf(balanceET.getText()));
                        if (curBal >= balance) {
                            leftBalance = curBal - balance;
                            new AlertDialog.Builder(TransactionProcess.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Confirm!")
                                    .setMessage("Do you want to Transact ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ContentValues cv = new ContentValues();
                                            cv.put("balance", leftBalance);
                                            customerDB.update("customer", cv, "userId= " + senderId, null);

                                            Cursor c3 = customerDB.rawQuery("SELECT * FROM customer WHERE name LIKE '%" + recipientName + "%'", null);
                                            int userIdIndex = c3.getColumnIndex("userId");
                                            int balanceIndex = c3.getColumnIndex("balance");
                                            if (c3.moveToFirst()) {
                                                recipientId = c3.getInt(userIdIndex);
                                                recipientBal = c3.getDouble(balanceIndex);
                                            }
                                            c3.close();

                                            ContentValues values = new ContentValues();
                                            values.put("balance", recipientBal + balance);
                                            customerDB.update("customer", values, "userId= " + recipientId, null);

                                            balanceTV.setText(String.valueOf(leftBalance));

                                            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("senderName", senderName);
                                            contentValues.put("recipientName", recipientName);
                                            contentValues.put("amount", balance);
                                            contentValues.put("transferTime", currentDateandTime);
                                            customerDB.insert("transferRecord", null, contentValues);

                                            Toast.makeText(getApplicationContext(), "Transaction Successful", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);


                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Transaction Unsuccessful\nYour current balance is low", Toast.LENGTH_LONG).show();
                        }


                    }

                }




            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}