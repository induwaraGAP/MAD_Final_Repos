package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Notices;
import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public class New_message extends AppCompatActivity {

       private EditText reciever,subject,body;
       private Button send;
       private Button back;
       private DatabaseReference reffcheckuserexist;
       private String ssubject,sbody,ssender;
       private String[] sreciever;
       private  String username;
       private SystemUser suns = new SystemUser();
       private ArrayList<String> Arrayreciver =  new ArrayList<String>();
       private DatabaseReference reffmgsinsert = FirebaseDatabase.getInstance().getReference().child("Notices");
       private Notices notices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        final Spinner category = (Spinner) findViewById(R.id.category);
        ArrayList<String> items=  new ArrayList<String>();
        items.clear();
        items.add("Students");
        items.add("Group");
        items.add("Year");

        ArrayAdapter<String> cateadapter = new ArrayAdapter<String>(New_message.this,android.R.layout.simple_list_item_1,items);
        cateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(cateadapter);
        reciever =  (EditText) findViewById(R.id.reciever);
        subject =  (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);
        send = (Button) findViewById(R.id.btnsend);
        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getSupportFragmentManager();
//                NotificationFragment nf = new NotificationFragment();
//                fm.beginTransaction().replace(R.id.containernotification,nf).commit();
                Intent gotomain = new Intent(New_message.this,MainActivity.class);
                startActivity(gotomain);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sbody = body.getText().toString();
                ssubject = subject.getText().toString();
                sreciever = reciever.getText().toString().split(",");
                if (sreciever == null) {
                    Toast.makeText(getApplicationContext(), "No reciever was mentioned", Toast.LENGTH_LONG).show();
                    return;
                } else if (sreciever != null) {


                    if ((category.getSelectedItem()).toString().equals("Students")) {
                        for (String user : sreciever) {
                            if (user.length() == 10) {
                                if (user.charAt(0) != 'I' || user.charAt(1) != 'T') {
                                    Toast.makeText(getApplicationContext(), "Invalid Reciever " + user, Toast.LENGTH_LONG).show();
                                    return;
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid reciever number " + user, Toast.LENGTH_LONG).show();
                                return;
                            }

                        }
                    }
                    if ((category.getSelectedItem()).toString().equals("Group")) {
                        for (String user : sreciever) {
                            if (user == null) {
                                Toast.makeText(getApplicationContext(), "Invalid Group Number", Toast.LENGTH_LONG).show();
                                return;
                            }


                        }
                    }
                    if ((category.getSelectedItem()).toString().equals("Year")) {
                        for (String user : sreciever) {
                            if (!(Pattern.matches("[a-zA-Z]+", user))) {
                                Toast.makeText(getApplicationContext(), "Invalid Year Number", Toast.LENGTH_LONG).show();
                                return;
                            }


                        }
                    }
                } else if (ssubject == null) {

                    Toast.makeText(getApplicationContext(), "Please fill the subject", Toast.LENGTH_LONG).show();
                    return;
                }
                else if( sbody == null)
                {
                    Toast.makeText(getApplicationContext(), "Please fill the Body", Toast.LENGTH_LONG).show();
                    return;
                }

                ssender = suns.getName();


                for (int getreclist = 0; getreclist < sreciever.length; getreclist++) {
                    Arrayreciver.add(sreciever[getreclist]);
                }

                System.out.println("this is the set of recievers " + Arrayreciver);
                notices = new Notices(ssender, Arrayreciver, sbody, ssubject);
                reffmgsinsert.child(notices.getMsg_id()).setValue(notices);
                Toast.makeText(getApplicationContext(), "Successfully Published", Toast.LENGTH_SHORT).show();
                Arrayreciver.clear();

            }



        });



    }



}
