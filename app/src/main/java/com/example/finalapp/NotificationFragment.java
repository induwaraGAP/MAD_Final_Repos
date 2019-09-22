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

import com.example.finalapp.firebase_models.Notices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    private DatabaseReference reffshownoti,reffdelnoti;

    private Notices notices;
    private ArrayList<String> recievers = new ArrayList<String>();
    private EditText rec,msge;
    private Button send;
    private View view;
    private String[] topic;
    private String[] date;
    private String selecteditem;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_notification,container,false);


       arrayadapter =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_messages);
        listview =  (ListView) view.findViewById(R.id.listview1);
        send = (Button) view.findViewById(R.id.newmsg);
        listview.setAdapter(arrayadapter);
        reffshownoti = FirebaseDatabase.getInstance().getReference().child("Notices");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotonewmsg = new Intent(getActivity().getApplicationContext(),New_message.class);
                startActivity(gotonewmsg);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {

                final int which_one = i;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this notice?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                selecteditem = ((TextView)view).getText().toString();
                                reffdelnoti = FirebaseDatabase.getInstance().getReference().child("Notices").child(selecteditem);
                                reffdelnoti.removeValue();
                                list_of_messages.remove(which_one);
                                arrayadapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("no",null)
                        .show();

                return false;
            }
        });


        reffshownoti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set =  new HashSet<String>();
                Iterator i =  dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                  //  topic = ((DataSnapshot)i.next()).getKey().split("sep");
                 //   date = topic[1].split("_");
                  //  set.add("\n" +"Sender: "+ topic[0] + " Date: " + date[0]+" " +date[1] + date[2] + "\n" +"Subject: "+ topic[2] + "\n");
                    set.add(((DataSnapshot)i.next()).getKey());
                }
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
