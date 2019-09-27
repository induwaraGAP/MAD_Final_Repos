package com.example.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button btnTimeTable;
    private Button btnsubinsert,btnsubupdatedel,loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnTimeTable = findViewById(R.id.btnTimeTable);
        btnsubinsert =  findViewById(R.id.subinsert);
        btnsubupdatedel =  findViewById(R.id.subupdate);
        loginbtn =  findViewById(R.id.backlogin);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TimeSlotActivity.class);
                startActivity(intent);
            }
        });

        btnsubinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoinsert = new Intent(MenuActivity.this, Subject_Insert.class);
                startActivity(gotoinsert);
            }
        });

        btnsubupdatedel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodel = new Intent(MenuActivity.this, subject_delete.class);
                startActivity(gotodel);
            }
        });


    }
}