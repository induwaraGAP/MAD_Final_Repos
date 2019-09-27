package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private String IT_NO, Name, Group, Faculty, Password, Type;
    private Integer Year, Semester;
    private EditText it, name, year, semester, group, faculty, password, type;
    private DatabaseReference ref_reg;
    private Button regbtn,backbtn;
    private Spinner typespin;
    private ArrayAdapter<String> typespinadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent getnotidetails =  getIntent();

        typespin = (Spinner) findViewById(R.id.spinner);
        backbtn = (Button) findViewById(R.id.backbtn) ;
        ArrayList<String> itemstype=  new ArrayList<String>();
        itemstype.clear();
        itemstype.add("Select_one_to_unlock");
        itemstype.add("student");
        itemstype.add("lecuturer");
        itemstype.add("admin");

        typespinadapter = new ArrayAdapter<String>(LoginActivity.this,android.R.layout.simple_list_item_1,itemstype);
        typespinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespin.setAdapter(typespinadapter);
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
        it.setEnabled(false);
        name.setEnabled(false);
        year.setEnabled(false);
        semester.setEnabled(false);
        group.setEnabled(false);
        faculty.setEnabled(false);
        password.setEnabled(false);
        type.setEnabled(false);
        regbtn.setEnabled(false);

        if( getnotidetails.getStringExtra("id") != null)
        {
            it.setText(getnotidetails.getStringExtra("id"));
            it.setEnabled(false);
            regbtn.setText("Update");

            if(getnotidetails.getStringExtra("type").toString().equals("student"))
            {
                name.setEnabled(true);
                year.setEnabled(true);
                semester.setEnabled(true);
                group.setEnabled(true);
                faculty.setEnabled(true);
                password.setEnabled(true);
                type.setText("student");
                type.setEnabled(false);
                regbtn.setEnabled(true);
            }
            else if(getnotidetails.getStringExtra("type").toString().equals("lecturer"))
            {
                name.setEnabled(true);
                year.setText("0");
                year.setEnabled(true);
                semester.setText("0");
                semester.setEnabled(true);
                group.setText("0");
                group.setEnabled(true);
                faculty.setEnabled(true);
                password.setEnabled(true);
                type.setText("lecturer");
                type.setEnabled(true);

                regbtn.setEnabled(true);
            }

        }



        typespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(typespin.getSelectedItem().toString().equals("lecuturer"))
                {
                    it.setEnabled(true);
                    name.setEnabled(true);
                    year.setText("0");
                    year.setEnabled(true);
                    semester.setText("0");
                    semester.setEnabled(true);
                    group.setText("0");
                    group.setEnabled(true);
                    faculty.setEnabled(true);
                    password.setEnabled(true);
                    type.setText("lecturer");
                    type.setEnabled(true);

                    regbtn.setEnabled(true);
                }
                else if(typespin.getSelectedItem().toString().equals("student"))
                {
                    it.setEnabled(true);
                    name.setEnabled(true);
                    year.setEnabled(true);
                    semester.setEnabled(true);
                    group.setEnabled(true);
                    faculty.setEnabled(true);
                    password.setEnabled(true);
                    type.setText("student");
                    type.setEnabled(false);
                    regbtn.setEnabled(true);
                }
                else if(typespin.getSelectedItem().toString().equals("admin"))
                {
                    it.setEnabled(true);
                    name.setEnabled(true);
                    year.setEnabled(true);
                    semester.setEnabled(true);
                    group.setEnabled(true);
                    faculty.setEnabled(true);
                    password.setEnabled(true);
                    type.setText("admin");
                    type.setEnabled(false);
                    regbtn.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if( getnotidetails.getStringExtra("id") != null)
        {
            it.setText(getnotidetails.getStringExtra("id"));
            it.setEnabled(false);
            regbtn.setText("Update");

        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gobacklogin =  new Intent(getApplicationContext(),Logincheckuser.class);
                startActivity(gobacklogin);
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
                Toast.makeText(getApplicationContext(),"Succesfully Inserted",Toast.LENGTH_LONG).show();

            }
        });





    }
}
