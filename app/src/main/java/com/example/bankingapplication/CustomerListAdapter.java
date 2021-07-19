package com.example.bankingapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    Context context;
    int clickedPosition;
    public static int senderId;
    public static String senderName;
    private ArrayList userId,name,email,balance;

    CustomerListAdapter(Context context,ArrayList userId,ArrayList name,ArrayList email,ArrayList balance){
        this.context=context;
        this.userId=userId;
        this.name=name;
        this.email=email;
        this.balance=balance;

    }


    @NonNull

    @Override
    public CustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.MyViewHolder holder, int position) {

        holder.tvUserId.setText("User-Id: "+userId.get(position));
        holder.tvName.setText("Name: "+name.get(position));
        holder.tvEmail.setText("Email-Id: "+email.get(position));
        holder.tvBalance.setText("Balance: Rs. "+balance.get(position));





    }

    @Override
    public int getItemCount() {
        return this.name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserId;
        TextView tvName;
        TextView tvEmail;
        TextView tvBalance;



        public MyViewHolder(View view) {
            super(view);
            tvUserId = view.findViewById(R.id.tvUserId);
            tvName = view.findViewById(R.id.tvName);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvBalance=view.findViewById(R.id.tvBalance);

            view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.i("User:","Sender Name is "+ name.get(getAdapterPosition()));
                    clickedPosition=getAdapterPosition();
                    senderId=(int)userId.get(clickedPosition);
                    senderName=String.valueOf(name.get(clickedPosition));
                    //Log.i("User id",Integer.toString(senderId));
                    Intent intent=new Intent(context,TransactionProcess.class);
                    context.startActivity(intent);





                }
            });



        }
    }
}
