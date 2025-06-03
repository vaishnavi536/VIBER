package com.example.viber;

import static com.google.firebase.storage.StorageKt.getStorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryFragment extends Fragment {
    public StoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        View img_mystoryicon = v.findViewById(R.id.img_mystoryicon);
        img_mystoryicon.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");
                startActivityForResult(in, 100);
            }

        });




        return v;
    }

    //override onActivityResult to get response of calling of Explicit intent

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri img= data.getData();
        //set select image URL into image

        CircleImageView ci=getActivity().findViewById(R.id.img_mystoryicon);
        ci.setImageURI(img);
        //set selected image into database
        Random r=new Random();
        int number=r .nextInt(400000);
        FirebaseStorage.getInstance().getReference().child("Viber"+number)
                .putFile(img).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            // download url of recent uploads file from firebaseStorage
                            //and store that uRl in Realtime Database
                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    SimpleDateFormat formater1=new SimpleDateFormat("yyyy-MM-dd");
                                    String date=formater1.format(new Date());
                                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                                    String time=format1.format(new Date());
                                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    String name=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                    HashMap<String, Object> hm=new HashMap<>();
                                    hm.put("name",name);
                                    hm.put("date",date);
                                    hm.put("time",time);
                                    FirebaseDatabase.getInstance().getReference().child("Story").child(uid).updateChildren(hm);
                                    HashMap<String,Object> hm1=new HashMap<>();
                                    hm.put("url",uri.toString());
                                    FirebaseDatabase.getInstance().getReference().child("story").child(uid).child("status").push().setValue(hm1);
                                    Toast.makeText(getContext(), "Story Updated", Toast.LENGTH_LONG).show();


                                }
                            });
                        }
                        else {
                            Toast.makeText(getContext(),"Server Error. Try again!",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}