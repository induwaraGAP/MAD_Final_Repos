package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalapp.firebase_models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private String IT_NO, Name, Group, Faculty, Password, Type;
    private Integer Year, Semester;
    private EditText it, name, year, semester, group, faculty, password, type;
    private DatabaseReference ref_reg;
    private Button regbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         ref_reg = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        it = findViewById(R.id.it_no);
//        IT_NO =  it.getText().toString();
//
//        name = (EditText) findViewById(R.id.name);
//        Name = name.getText().toString();
//
//        year =  (EditText) findViewById(R.id.year);
//        Year =  Integer.parseInt(year.getText().toString());
//
//        semester =  (EditText) findViewById(R.id.semester);
//        Semester =  Integer.parseInt(semester.getText().toString());
//
//        group =  (EditText) findViewById(R.id.group);
//        Group =  group.getText().toString();
//
//        faculty =  (EditText) findViewById(R.id.faculty);
//        Faculty =  faculty.getText().toString();
//
//        password =  (EditText) findViewById(R.id.password);
//        Password =  password.getText().toString();
//
//        type=  (EditText) findViewById(R.id.type);
//        Type =  type.getText().toString();
//        regbtn =  (Button) findViewById(R.id.regbtn);
//        regbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Users usr = new Users(IT_NO, Name, Year, Semester, Group, Faculty, Password, Type);
//
//                ref_reg.child(IT_NO).setValue(usr);
//
//            }
//        });
//
//
//


    }
}
