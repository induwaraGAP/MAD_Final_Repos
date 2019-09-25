package com.example.finalapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;
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
    private ArrayAdapter<String> arrayadaptertb;
    private ListView listviewtb;
    private String starttime,endtime,sub_id,day,classtype,hallno,lecturer,display;
    ArrayList<String> datesarray = new ArrayList<String>();

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reffshowtb = FirebaseDatabase.getInstance().getReference().child("TimeTable");

        viewtb = inflater.inflate(R.layout.fragment_users,container,false);

        arrayadaptertb =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_tb);

        listviewtb =  (ListView) viewtb.findViewById(R.id.tblistviewmonday);

        listviewtb.setAdapter(arrayadaptertb);

        reffshowtb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set =  new HashSet<String>();
                Iterator i =  dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                    DataSnapshot ds =  (DataSnapshot)i.next();

                    starttime = ds.child("starttime").getValue().toString();
                    endtime = ds.child("endtime").getValue().toString();
                    classtype = ds.child("class_type").getValue().toString();
                    hallno = ds.child("hallno").getValue().toString();
                    sub_id = ds.child("sub_id").getValue().toString();
                    day =  ds.child("day").getValue().toString();
                    if(day.equals("Monday")) {
                        display = "\n" + sub_id + "\n" + hallno + "\n" + starttime + "\n" + endtime + "\n" + classtype + "\n";

                        set.add(display);
                    }

                }
//                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//                String temp_correct = null;
//                Integer temp_correct_index = null;
//                try {
//
//                    String temp_date;
//
//                    Date d2;
//
//                    for(int checkmain = 0; checkmain < datesarray.size(); checkmain++) {
//                        Date temp = format.parse(datesarray.get(checkmain));
//                        temp_correct =  datesarray.get(checkmain);
//                        temp_date = datesarray.get(checkmain);
//                        temp_correct_index = checkmain;
//                        for (int check = checkmain; check < datesarray.size(); check++) {
//
//                            if ((format.parse(datesarray.get(check))).before(temp)) {
//                                System.out.println("check main " + datesarray.get(checkmain) + " check" +datesarray.get(check));
//
//                                temp = format.parse(datesarray.get(check));
//                                temp_correct = datesarray.get(check);
//                                temp_correct_index =  check;
//
//                                temp_date = datesarray.get(check);
//                            }
//
//                        }
//                        datesarray.set(temp_correct_index,datesarray.get(checkmain));
//                        datesarray.set(checkmain, temp_correct);
//
//                        System.out.println("sorting "+ checkmain + " " +datesarray);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                list_of_tb.clear();

                list_of_tb.addAll(set);

                arrayadaptertb.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return viewtb;
    }


}
