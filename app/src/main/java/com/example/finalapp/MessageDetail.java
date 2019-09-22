package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageDetail extends AppCompatActivity {
    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private String msge;
    private String msg_id;
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        btn_send_msg = (Button) findViewById(R.id.Sendbtn);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conversation = (TextView)findViewById(R.id.chat_convo);
        msge =  input_msg.getText().toString();
         msg_id =  getIntent().getExtras().get("noticename").toString();
         System.out.println("this msge id " + msg_id);
         root = FirebaseDatabase.getInstance().getReference().child("Notices").child("message");
         btn_send_msg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
         });

    }
}
