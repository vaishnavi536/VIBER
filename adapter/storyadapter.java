package com.example.viber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devlomi.circularstatusview.CircularStatusView;
import com.example.viber.Home;
import com.example.viber.R;
import com.example.viber.model.Storymodel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class storyadapter extends RecyclerView.Adapter<storyadapter.ViewHolder> {

    Context con;
    ArrayList<Storymodel> list;

    TextView text_statusname, text_statususertime;
    CircleImageView img_statususericon;
    CircularStatusView img_statusportionicon;
    public storyadapter(Context con, ArrayList<Storymodel> list )
    {
        this.con=con;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(con).inflate(R.layout.samplestorydesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            text_statusname.setText("your status");
            text_statusname.setText(list.get(position).name);

        }
        text_statususertime.setText(list.get(position).time+" | "+list.get(position).date);
        img_statusportionicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<MyStory> myStories = new ArrayList<>();
                for(String s : list.get(position).urls){
                    myStories.add(
                            new MyStory(

                                    s

                            ));

                }
                new StoryView.Builder(((Home)con).getSupportFragmentManager())
                        .setStoriesList(myStories) // Required
                        .setStoryDuration(6000) // Default is 2000 Millis (2 Seconds)
                        .setTitleText(list.get(position).name) // Default is Hidden
                        .setSubtitleText(list.get(position).date+" | "+list.get(position).time) // Default is Hidden
                        .setStoryClickListeners(new StoryClickListeners() {
                            @Override
                            public void onDescriptionClickListener(int position) {
                                //your action
                            }

                            @Override
                            public void onTitleIconClickListener(int position) {
                                //your action
                            }
                        }) // Optional Listeners
                        .build() // Must be called before calling show method
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
