package com.example.viber.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viber.Message;
import com.example.viber.R;

import java.util.ArrayList;
import java.util.zip.Inflater;
import  com.example.viber.model.usermodel;

public class useradapter extends  RecyclerView.Adapter<useradapter.viewholder>{

    Context con;
    ArrayList<usermodel> user;

    TextView username,useremail;
    public  useradapter( Context con,ArrayList<usermodel> user )
    {
        this.con=con;
        this.user=user;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v=LayoutInflater.from(con).inflate(R.layout.sampleuserdesign,parent,false);
      return  new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
            username.setText(user.get(position).name);
            useremail.setText(user.get(position).emailid);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(con, Message.class);
                    i.putExtra("name",user.get(position).name);
                    i.putExtra("uid",user.get(position).uid);
                    con.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    class viewholder extends  RecyclerView.ViewHolder
    {

        public viewholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.text_username);
            useremail=itemView.findViewById(R.id.text_useremail);
        }
    }
}








