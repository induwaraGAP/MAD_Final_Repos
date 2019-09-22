package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Subject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Subject_Insert extends AppCompatActivity {

    private EditText subjectid,sub_name,faculty,users;
    private Button add;
    private String ssubjectid,ssub_names,sfaculty,susers;
    private Subject subject;
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject__insert);

        final ArrayList<String> a =  new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        subjectid = (EditText) findViewById(R.id.subjectid);
        sub_name = (EditText) findViewById(R.id.subjectname);
        faculty = (EditText) findViewById(R.id.faculty);
        users = (EditText) findViewById(R.id.sliitid);
        add =  (Button) findViewById(R.id.addbtn);
        reff = FirebaseDatabase.getInstance().getReference().child("Subject");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssubjectid =  subjectid.getText().toString();
                ssub_names = sub_name.getText().toString();
                sfaculty =  faculty.getText().toString();
                susers = users.getText().toString();

                Subject subject = new Subject(ssubjectid, ssub_names, sfaculty, null,a);
                reff.child(ssubjectid).setValue(subject);
                Toast.makeText(Subject_Insert.this,"data inserter",Toast.LENGTH_LONG).show();
            }
        });

        //reff = FirebaseDatabase.getInstance().getReference().child("sub_id");









    }
}
