package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Notices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotificationUpdate extends AppCompatActivity {
    private TextView sender,Date;
    private EditText body,subject;
    private Button updatebtn;
    private String ssender,sdate,ssubjectname,sbodytext,id;
    private String[] datearray;
    private ArrayList<String> receiverslist =  new ArrayList<String>();
    private DatabaseReference reffmsgupdate =  FirebaseDatabase.getInstance().getReference().child("Notices");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_update);

        sender = (TextView) findViewById(R.id.rsender);
        Date = (TextView) findViewById(R.id.rdate);
        subject = (EditText) findViewById(R.id.rsubject);
        body = (EditText) findViewById(R.id.rbody);
        updatebtn = (Button) findViewById(R.id.btnupdate);
        Intent getnotidetails =  getIntent();

        id = getnotidetails.getStringExtra("id");
        ssender =  getnotidetails.getStringExtra("sender");
        datearray = getnotidetails.getStringArrayExtra("Date");
        sdate = datearray[0]+"_"+datearray[1]+"_"+datearray[2]+"_"+datearray[3]+"_"+datearray[4]+"_"+datearray[5];
        sbodytext =  getnotidetails.getStringExtra("body");
        ssubjectname =  getnotidetails.getStringExtra("subject");
        receiverslist =  getnotidetails.getStringArrayListExtra("recievers");

        sender.setText(ssender);
        Date.setText(sdate);
        subject.setText(ssubjectname);
        body.setText(sbodytext);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("this is the id " + id);
                Notices not =  new Notices();
                not.setSender(ssender);
                not.setRecievers(receiverslist);
                not.setMessage(body.getText().toString());
                not.setSubject(subject.getText().toString());
                not.setMsg_id(id);
                not.setDate(sdate);
                reffmsgupdate.child(not.getMsg_id()).setValue(not);
                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
            }
        });





    }
}
