package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Logincheckuser extends AppCompatActivity {

    private DatabaseReference reffchecklogin;
    private EditText userid,password;
    private Button login,newlog;

    private  String suserid,spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincheckuser);
        userid =  (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById( R.id.password);
        login = (Button) findViewById(R.id.login);
        newlog  = (Button) findViewById(R.id.new_account);
        newlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoreg =  new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(gotoreg);
            }
        });

        reffchecklogin= FirebaseDatabase.getInstance().getReference("Users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"came to onclick",Toast.LENGTH_SHORT).show();


                reffchecklogin.addListenerForSingleValueEvent(valueEventListener);

            }
        });


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            System.out.println("this is the password from passwrod field " + password.getText());
            Log.i("details" ,"came");
            Set<String> set =  new HashSet<String>();
            Iterator i =  dataSnapshot.getChildren().iterator();
            while(i.hasNext())
            {

                DataSnapshot ds =  (DataSnapshot)i.next();

                suserid = ds.child("it_NO").getValue().toString();
                spassword = ds.child("password").getValue().toString();
                //System.out.println("this is rge susers " + suserid);
                //System.out.println("this is the password " +spassword);

                if(suserid.equals(userid.getText().toString().trim()) && (spassword.equals(password.getText().toString().trim()))) {
                    Log.i("details" ,"FOUND PASS");

                    SystemUser usr = new SystemUser(ds.child("name").getValue().toString(),suserid,ds.child("group").getValue().toString()  , Integer.parseInt(ds.child("year").getValue().toString() ) ,Integer.parseInt(ds.child("sem").getValue().toString()),ds.child("faculty").getValue().toString(),ds.child("type").getValue().toString());
                   if(ds.child("type").getValue().toString().equals("admin"))
                   {
                       Intent gotoadminpanel =  new Intent(getApplicationContext(),MenuActivity.class);
                       startActivity(gotoadminpanel);
                   }
                   else {
                       Intent gotomainfirst = new Intent(Logincheckuser.this, MainActivity.class);
                       startActivity(gotomainfirst);
                   }
                }


            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
