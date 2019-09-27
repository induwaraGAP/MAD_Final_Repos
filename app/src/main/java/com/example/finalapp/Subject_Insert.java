package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Subject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Subject_Insert extends AppCompatActivity {

    private EditText subjectid,sub_name,faculty,users;
    private Button add,BACK;
    private TextView sbanner;
    private String ssubjectid,ssub_names,sfaculty,susers;
    private Subject subject;
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__insert);

        Intent getnotidetails =  getIntent();

        sbanner = (TextView) findViewById(R.id.banner);
        subjectid = (EditText) findViewById(R.id.subjectid);
        sub_name = (EditText) findViewById(R.id.subjectname);
        faculty = (EditText) findViewById(R.id.faculty);
        users = (EditText) findViewById(R.id.lecturername);
        add =  (Button) findViewById(R.id.addbtn);
        reff = FirebaseDatabase.getInstance().getReference().child("Subject");
        BACK = (Button) findViewById(R.id.backbtn);
        if( getnotidetails.getStringExtra("id") != null)
        {
            subjectid.setText(getnotidetails.getStringExtra("id"));
            subjectid.setEnabled(false);
            add.setText("Update");
            sbanner.setText("Update Your Subject");
        }
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomenuagain  = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(gotomenuagain);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssubjectid =  subjectid.getText().toString();
                ssub_names = sub_name.getText().toString();
                sfaculty =  faculty.getText().toString();
                susers = users.getText().toString();
                if(ssubjectid == null)
                {
                    Toast.makeText(Subject_Insert.this,"Subject id cannot be null",Toast.LENGTH_LONG).show();
                }
                else if(ssub_names == null)
                {
                    Toast.makeText(Subject_Insert.this,"Subject name cannot be null",Toast.LENGTH_LONG).show();
                }
                else if(sfaculty ==  null)
                {
                    Toast.makeText(Subject_Insert.this,"Faculty cannot be null",Toast.LENGTH_LONG).show();

                }
                else if(susers == null)
                {
                    Toast.makeText(Subject_Insert.this,"Lecturer cannot be null",Toast.LENGTH_LONG).show();

                }
                else {
                    Subject subject = new Subject(ssubjectid, ssub_names, sfaculty, susers);
                    reff.child(ssubjectid).setValue(subject);
                    Toast.makeText(Subject_Insert.this, "data inserted", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
