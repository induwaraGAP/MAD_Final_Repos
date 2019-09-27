package com.example.finalapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapp.firebase_models.Notices;
import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private ListView listview;
    private ArrayAdapter<String> arrayadapter;
    private ArrayList<String> list_of_messages = new ArrayList<>();
    private DatabaseReference reffshownoti,reffdelnoti,reffshownotibody;
    private Notices notices;
    private ArrayList<String> recievers = new ArrayList<String>();
    private EditText rec,msge;
    private Button send;
    private View view;
    private String[] topic;
    private String[] date;
    private String selecteditem;
    private String id;
    private HashMap<String,String> idmapnotice =  new HashMap<String, String>();
    private String Display;
    private  String body ;
    private HashMap<Integer,ArrayList<String>> recmap =  new HashMap<Integer, ArrayList<String>>();
    private String subjectnode;
    private SystemUser sysuser = new SystemUser();
    private Integer status = 0;

    public NotificationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("id " +sysuser.getId());
        System.out.println("name "+ sysuser.getName());
        System.out.println("group  " + sysuser.getGroup());
        System.out.println("year"+ sysuser.getYear());
        System.out.println("sem " +sysuser.getSem());
        System.out.println("faculty "+ sysuser.getFaculty());
        view = inflater.inflate(R.layout.fragment_notification,container,false);

        arrayadapter =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_messages);

        listview =  (ListView) view.findViewById(R.id.listview1);

        send = (Button) view.findViewById(R.id.newmsg);

        listview.setAdapter(arrayadapter);

        reffshownoti = FirebaseDatabase.getInstance().getReference().child("Notices");

        if(sysuser.getType().toString().equals("student"))
        {
            send.setVisibility(View.GONE);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotonewmsg = new Intent(getContext(),New_message.class);
                startActivity(gotonewmsg);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {

                final int which_one = i;
                System.out.println("this is the which one " + which_one);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select one ?")
                        .setMessage("")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( sysuser.getType().toString().equals("student"))
                                {
                                    list_of_messages.remove(which_one);

                                    arrayadapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(),"Delete from the List",Toast.LENGTH_LONG);
                                }
                                else {
                                    selecteditem = ((TextView) view).getText().toString();

                                    reffdelnoti = FirebaseDatabase.getInstance().getReference().child("Notices").child(idmapnotice.get(selecteditem));

                                    reffdelnoti.removeValue();

                                    list_of_messages.remove(which_one);

                                    arrayadapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(),"Delete from the List & Database",Toast.LENGTH_LONG);
                                }
                            }
                        }).setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selecteditem = ((TextView)view).getText().toString();
                                Intent gotonotiupdate =  new Intent(getActivity().getApplicationContext(),NotificationUpdate.class);
                                gotonotiupdate.putExtra("id",idmapnotice.get(selecteditem));
                                gotonotiupdate.putExtra("sender",topic[0]);
                                gotonotiupdate.putExtra("Date",date);
                                gotonotiupdate.putExtra("body",body);
                                gotonotiupdate.putExtra("subject",subjectnode);
                                gotonotiupdate.putExtra("recievers",recmap.get(which_one));
                                startActivity(gotonotiupdate);
                                selecteditem = ((TextView)view).getText().toString();

                        }
                }).show();

                return false;
            }
        });
        reffshownoti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                Set<String> set =  new HashSet<String>();
                Iterator i =  dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                    DataSnapshot ds =  (DataSnapshot)i.next();

                    id = ds.getKey();
                    recievers = (ArrayList<String>) ds.child("recievers").getValue();
                    status = 0;
                    for(String recnames  : recievers)
                    {
                        System.out.println("this is recname " + recnames);
                        if( (recnames.equals(sysuser.getId())) || (recnames.equals(sysuser.getGroup())) || (recnames.equals(String.valueOf(sysuser.getSem()))) || (recnames.equals( String.valueOf(sysuser.getYear()))) || (sysuser.getType().equals("lecturer")) ){
                            status = 1;
                        }
                    }
                    if(status == 1) {
                        topic = id.split("sep");
                        System.out.println("this is the topic  " + Arrays.toString(topic));
                        date = topic[1].split("_");

                        System.out.println("these are the recievers");
                        body = ds.child("message").getValue().toString();

                        recmap.put(counter, (ArrayList) ds.child("recievers").getValue());

                        subjectnode = ds.child("subject").getValue().toString();

                        Display = "\n" + "Sender: " + topic[0] +
                                "\n" + "Date: " + date[0] + " " + date[1] + date[2] +
                                "\n" + "Subject: " + subjectnode + "\n"
                                + "Body : " + body + "\n";

                        idmapnotice.put(Display, id);

                        set.add(Display);
                        counter++;
                    }

                }
                counter = 0;
                list_of_messages.clear();

                list_of_messages.addAll(set);

                arrayadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent gotomsgdetail =  new Intent(getActivity().getApplicationContext(),MessageDetail.class);
//                gotomsgdetail.putExtra("noticename",((TextView)view).getText().toString());
//                startActivity(gotomsgdetail);
//
//            }
//        });


       return  view;
    }

}
