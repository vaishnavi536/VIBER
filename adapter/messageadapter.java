package com.example.viber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.viber.R;
import com.example.viber.messagemodel;
import com.example.viber.model.*;
import com.google.firebase.auth.FirebaseAuth;

public class messageadapter extends RecyclerView.Adapter {
    Context con;
    ArrayList<messagemodel> list;

    TextView text_sendermsg, text_sendermsgtime, text_sendermsgdate, text_receivermsg, text_receivermsgtime, text_receivermsgdate;
    public messageadapter(Context con, ArrayList<messagemodel> list)
    {
        this.con=con;
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(con).inflate(R.layout.sendermsgsampledesign,parent,false);
            return(RecyclerView.ViewHolder) new SenderViewHolder(v);
        }
        else {
            View v= LayoutInflater.from(con).inflate(R.layout.receivermsgesampledesign,parent,false);
            return (RecyclerView.ViewHolder) new ReceiverViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getClass()==SenderViewHolder.class)
        {
            text_sendermsg.setText(list.get(position).message);
            text_sendermsgtime.setText(list.get(position).time);
            text_sendermsgdate.setText(list.get(position).date);
        }
        else {
            text_receivermsg.setText(list.get(position).message);
            text_receivermsgdate.setText(list.get(position).date);
            text_receivermsgtime.setText(list.get(position).time);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).sender.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            return 1;
        }
        else {
            return 2;
        }

    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        public SenderViewHolder(@NonNull View itemView){
            super(itemView);
            text_sendermsg=itemView.findViewById(R.id.text_sendermsg);
            text_sendermsgtime=itemView.findViewById(R.id.text_sendermsgtime);
            text_sendermsgdate=itemView.findViewById(R.id.text_sendermsgdate);

        }

    }
    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        public ReceiverViewHolder(@NonNull View itemView){
            super(itemView);
            text_receivermsg=itemView.findViewById(R.id.text_receivermsg);
            text_receivermsgdate=itemView.findViewById(R.id.text_receivermsgdate);
            text_receivermsgtime=itemView.findViewById(R.id.text_receivermsgtime);
        }

    }
}
