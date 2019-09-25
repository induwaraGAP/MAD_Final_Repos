package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private Button regbtn,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         ref_reg = FirebaseDatabase.getInstance().getReference().child("Users");

        it = findViewById(R.id.it_no);


        name = (EditText) findViewById(R.id.name);


        year =  (EditText) findViewById(R.id.year);


        semester =  (EditText) findViewById(R.id.semester);


        group =  (EditText) findViewById(R.id.group);


        faculty =  (EditText) findViewById(R.id.faculty);


        password =  (EditText) findViewById(R.id.password);


        type=  (EditText) findViewById(R.id.type);

        regbtn =  (Button) findViewById(R.id.regbtn);
        backbtn = (Button) findViewById(R.id.btnback);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent gotologin =  new Intent(LoginActivity.this,)
            }
        });
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IT_NO =  it.getText().toString();
                Name = name.getText().toString();
                Year =  Integer.parseInt(year.getText().toString());
                Semester =  Integer.parseInt(semester.getText().toString());
                Group =  group.getText().toString();
                Faculty =  faculty.getText().toString();
                Password =  password.getText().toString();
                Type =  type.getText().toString();

                Users usr = new Users(IT_NO, Name, Year, Semester, Group, Faculty, Password, Type);

                ref_reg.child(IT_NO).setValue(usr);



            }
        });





    }
}
