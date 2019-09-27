package com.example.finalapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private DatabaseReference reffshowtb;
    private ArrayList<String> list_of_tb = new ArrayList<>();
    private View viewtb;
    private ArrayAdapter<String> arrayadaptertb,spinneradapter;
    private ListView listviewtb;
    private Spinner spinnerday;
    private String starttime,endtime,sub_id,day,classtype,hallno,lecturer,display;
    private ArrayList<String> datesarray = new ArrayList<String>();
    private ArrayList<String> daylist =  new ArrayList<String>();
    private SystemUser usrsys =  new SystemUser();

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (usrsys.getType().equals("Lecturer")) {
            list_of_tb.add("NOT SUPPORTED FOR LECTURER LOGIN");
        }
            reffshowtb = FirebaseDatabase.getInstance().getReference().child("TimeTable");

            viewtb = inflater.inflate(R.layout.fragment_users, container, false);

            arrayadaptertb = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_tb);

            listviewtb = (ListView) viewtb.findViewById(R.id.tblistview);

            listviewtb.setAdapter(arrayadaptertb);

            spinnerday = (Spinner) viewtb.findViewById(R.id.dayspinner);

            daylist.add("Monday");
            daylist.add("Tuesday");
            daylist.add("Wednesday");
            daylist.add("Thursday");
            daylist.add("Friday");
            spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, daylist);
            spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerday.setAdapter(spinneradapter);
            //reffshowtb.addListenerForSingleValueEvent(valueEventListener);
            spinnerday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    reffshowtb.addListenerForSingleValueEvent(valueEventListener);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            reffshowtb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Set<String> set = new HashSet<String>();
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        DataSnapshot ds = (DataSnapshot) i.next();

                        starttime = ds.child("starttime").getValue().toString();
                        endtime = ds.child("endtime").getValue().toString();
                        classtype = ds.child("class_type").getValue().toString();
                        hallno = ds.child("hallno").getValue().toString();
                        sub_id = ds.child("sub_id").getValue().toString();
                        day = ds.child("day").getValue().toString();
                        if (day.equals("Monday")) {
                            display = "\n Subject" + sub_id + "\n Hall No:" + hallno + "\n Time Duration:" + starttime + "-" + endtime + "\n Class Type:" + classtype + "\n";

                            set.add(display);
                        }

                    }

                    list_of_tb.clear();

                    list_of_tb.addAll(set);
                    Collections.reverse(list_of_tb);

                    arrayadaptertb.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return viewtb;
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot ds = (DataSnapshot) i.next();
                    if (usrsys.getType().equals("student")) {
                        if ((ds.child("faculty").getValue().toString().equals(usrsys.getFaculty())) && (ds.child("year").getValue().toString().equals(String.valueOf(usrsys.getYear()))) && (ds.child("sem").getValue().toString().equals(String.valueOf(usrsys.getSem()))) && (ds.child("group").getValue().toString().equals(usrsys.getGroup()))) {
                            starttime = ds.child("starttime").getValue().toString();
                            endtime = ds.child("endtime").getValue().toString();
                            classtype = ds.child("class_type").getValue().toString();
                            hallno = ds.child("hallno").getValue().toString();
                            sub_id = ds.child("sub_id").getValue().toString();
                            day = ds.child("day").getValue().toString();
                            if (day.equals(spinnerday.getSelectedItem().toString())) {
                                display = "\n Subject" + sub_id + "\n Hall No:" + hallno + "\n Time Duration:" + starttime + "-" + endtime + "\n Class Type:" + classtype + "\n";

                                set.add(display);
                            }
                        }
                    }

                }

                list_of_tb.clear();

                list_of_tb.addAll(set);
                Collections.reverse(list_of_tb);

                arrayadaptertb.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


}
