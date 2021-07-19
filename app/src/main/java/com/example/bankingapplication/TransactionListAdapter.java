package com.example.bankingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.MyViewHolder> {
    Context context;
    private ArrayList transactId, senderName, recipientName, amount, time;

    TransactionListAdapter(Context context, ArrayList transactId, ArrayList senderName, ArrayList recipientName, ArrayList amount, ArrayList time) {
        this.context = context;
        this.transactId = transactId;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.amount = amount;
        this.time = time;
    }


    @Override
    public TransactionListAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_table_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TransactionListAdapter.MyViewHolder holder, int position) {
        holder.transactIdTV.setText(""+transactId.get(position));
        holder.senderNameTV.setText(""+senderName.get(position));
        holder.recipientNameTV.setText(""+recipientName.get(position));
        holder.amountTV.setText(""+amount.get(position));
        holder.timeTV.setText(""+time.get(position));

    }



    @Override
    public int getItemCount() {
        return transactId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView transactIdTV;
        TextView senderNameTV;
        TextView recipientNameTV;
        TextView amountTV;
        TextView timeTV;

        public MyViewHolder(View view) {
            super(view);
            transactIdTV = view.findViewById(R.id.transactIdTV);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            recipientNameTV = view.findViewById(R.id.recipientNameTV);
            amountTV=view.findViewById(R.id.amountTV);
            timeTV=view.findViewById(R.id.timeTV);
        }
    }
}
