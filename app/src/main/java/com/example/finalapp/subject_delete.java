package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class subject_delete extends AppCompatActivity {

    private ArrayAdapter<String> arrayadaptersubject;
    private ArrayList<String> list_of_subjects = new ArrayList<>();
    private ListView listviewsubject;
    private Button back;
    private DatabaseReference reffshowsubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_delete);

        arrayadaptersubject =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list_of_subjects);

        listviewsubject =  (ListView) findViewById(R.id.listviewsub);

        back = (Button) findViewById(R.id.back);

        listviewsubject.setAdapter(arrayadaptersubject);

        reffshowsubject = FirebaseDatabase.getInstance().getReference().child("Subject");

        listviewsubject.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final int which_one = i;
                System.out.println("this is the which one " + which_one);
                new AlertDialog.Builder(subject_delete.this)
                        .setTitle("Select one ?")
                        .setMessage("")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String selecteditem = ((TextView) view).getText().toString();

                                DatabaseReference reffdelsub = FirebaseDatabase.getInstance().getReference().child("Subject").child(selecteditem);

                                reffdelsub.removeValue();

                                list_of_subjects.remove(which_one);

                                arrayadaptersubject.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selecteditem = ((TextView) view).getText().toString();
                        Intent gotosubupdate = new Intent(getApplicationContext(), Subject_Insert.class);
                        gotosubupdate.putExtra("id", selecteditem);

                        startActivity(gotosubupdate);


                    }
                }).show();
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomenu = new Intent(getApplicationContext(),subject_delete.class);
                startActivity(gotomenu);
            }
        });


        reffshowsubject.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set =  new HashSet<String>();
                Iterator i =  dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                    DataSnapshot ds =  (DataSnapshot)i.next();

                    set.add(ds.child("sub_id").getValue().toString());

                }

                list_of_subjects.clear();

                list_of_subjects.addAll(set);

                arrayadaptersubject.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
