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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class fragment_free extends Fragment {
    private View viewfree;
    private ListView free_listview;
    private DatabaseReference reffget ;
    private ArrayList<String> hitems=  new ArrayList<String>();
    private ArrayList<String> mitems=  new ArrayList<String>();
    private ArrayList<String> yitems=  new ArrayList<String>();
    private ArrayList<String> sitems=  new ArrayList<String>();
    private ArrayList<String> gitems=  new ArrayList<String>();
    private ArrayList<String> fitems=  new ArrayList<String>();
    private ArrayList<String> ditems=  new ArrayList<String>();
    private ArrayAdapter<String> hadapter;
    private ArrayAdapter<String> madapter;
    private ArrayAdapter<String> yadapter;
    private ArrayAdapter<String> sadapter;
    private ArrayAdapter<String> gadapter;
    private ArrayAdapter<String> fadapter;
    private ArrayAdapter<String> dadapter;
    private ArrayAdapter<String> free_slot_adapter;

    private HashMap<String,HashMap<String,String>> tb = new HashMap<String, HashMap<String, String>>();
    private HashMap<String,String> temptb = new HashMap<String, String>();
    private String id;
    private String hday,hfaculty,hgroup,hsem,hyear,hstarttme,hendtime;

    private Button btnsearch;
    private String hh,mm,group,faculty,day,starttime,endtime,dispaytime,year,sem;

    private ArrayList<String> list_of_free_times =  new ArrayList<String>();
    private long remian_min,remain_hour;


    public fragment_free() {

    }

    public ArrayList sethspinner(int start, int end, ArrayList<String> temp)
    {

        for(int h = start;h  <= end;h++)
        {
            temp.add(String.valueOf(h));
        }
        return temp;
    }
    public ArrayList setGspinner(int start, int end, ArrayList<String> temp)
    {

        for(int h = start;h  <= end;h++)
        {
            temp.add("G"+String.valueOf(h) );
        }
        return temp;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewfree = inflater.inflate(R.layout.fragment_fragment_free,container,false);
        reffget = FirebaseDatabase.getInstance().getReference().child("TimeTable");
        free_slot_adapter =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_free_times);
        free_listview = viewfree.findViewById(R.id.freelist);
        free_listview.setAdapter(free_slot_adapter);
        final Spinner hspinner = viewfree.findViewById(R.id.hspinner);
        hitems = sethspinner(0,6,hitems);
        hadapter = new ArrayAdapter<String>(getContext().getApplicationContext(),android.R.layout.simple_list_item_1,hitems);
        hadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hspinner.setAdapter(hadapter);

        final Spinner mspinner = viewfree.findViewById(R.id.mspinner);
        mitems = sethspinner(0,60,mitems);
        madapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mitems);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(madapter);

        final Spinner yspinner = viewfree.findViewById(R.id.yspinner);
        yitems = sethspinner(1,4,yitems);
        yadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,yitems);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yspinner.setAdapter(yadapter);

        final Spinner sspinner = viewfree.findViewById(R.id.sspinner);
        sitems = sethspinner(1,2,sitems);
        sadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,sitems);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sspinner.setAdapter(sadapter);

        final Spinner gspinner = viewfree.findViewById(R.id.gspinner);
        gitems = setGspinner(1,14,gitems);
        gadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,gitems);
        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gspinner.setAdapter(gadapter);

        final Spinner fspinner = viewfree.findViewById(R.id.fspinner);
        fitems.add("Computing");
        fitems.add("Engineering");
        fitems.add("Buisness");
        fadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,fitems);
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fspinner.setAdapter(fadapter);

        final Spinner dspinner = viewfree.findViewById(R.id.dspinner);
        ditems.add("Monday");
        ditems.add("Tuesday");
        ditems.add("Wednesday");
        ditems.add("Thursday");
        fitems.add("Friday");
        fitems.add("Saturday");
        fitems.add("Sunday");
        dadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,ditems);
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspinner.setAdapter(dadapter);

        btnsearch = viewfree.findViewById(R.id.searchbtn);
        reffget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Iterator i =  dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                    DataSnapshot ds =  (DataSnapshot)i.next();

                    id = ds.getKey();
                    hday = ds.child("day").getValue().toString();
                    System.out.println("this is day " + hday);
                    hfaculty = ds.child("faculty").getValue().toString();
                    hgroup = ds.child("group").getValue().toString();
                    hsem = ds.child("sem").getValue().toString();
                    hyear =  ds.child("year").getValue().toString();
                    hstarttme =  ds.child("starttime").getValue().toString();
                    hendtime =  ds.child("endtime").getValue().toString();

                    temptb.put("day",hday);
                    temptb.put("faculty",hfaculty);
                    temptb.put("group",hgroup);
                    temptb.put("sem",hsem);
                    temptb.put("year",hyear);
                    temptb.put("starttime",hstarttme);
                    temptb.put("endtime",hendtime);
                    System.out.println("this is the temptb" + temptb);
                    tb.put(id,new HashMap<String, String>(temptb)  );



                }
                System.out.println("this is temo tb " + tb);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hh =  hspinner.getSelectedItem().toString();
               mm =  mspinner.getSelectedItem().toString();
                year = (String) yspinner.getSelectedItem();
                sem = (String) sspinner.getSelectedItem();
                group = (String) gspinner.getSelectedItem().toString();
                faculty = (String) fspinner.getSelectedItem();
                day = (String) dspinner.getSelectedItem();
                System.out.println("hh "+hh+" mm "+mm+" year: "+year+" sem "+ sem+" group "+group+" faculty: "+faculty+ " day "+day);
                HashMap<Integer,String[]>  datestore =  new HashMap<Integer, String[]>();
                ArrayList<String> datesarray = new ArrayList<String>();
                ArrayList<String> freetimes =  new ArrayList<String>();
//                hh = "2";
//                mm = "0";
//                year = 2;
//                sem = 2;
//                group = "g9";
//                faculty = "Computing";
//                day = "Monday";
                int counter = 0;
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                for(Map.Entry<String,HashMap<String,String>> mainele : tb.entrySet())
                {

                    temptb.clear();
                    temptb.putAll(mainele.getValue());
                    if( (temptb.get("day").equals(day)) && (temptb.get("faculty").equals(faculty)) && (temptb.get("sem").equals(sem))&& (temptb.get("group").equals(group)) && (temptb.get("year").equals(year)))
                    {

                        System.out.println("came inside the if condition");
                        //datestore.put(counter,new String[]{temptb.get("starttime"),temptb.get("endtime")});

                            datesarray.add(temptb.get("starttime") +":00");
                            datesarray.add(temptb.get("endtime") +":00");

                        System.out.println(temptb.get("starttime") +"  " + temptb.get("endtime"));

                    }
                }

                System.out.println("this is dates array unsorted " + datesarray);

                System.out.println("after sorted " + datesarray);
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String temp_correct = null;
                Integer temp_correct_index = null;
                try {

                  String temp_date;

                  Date d2;
                for(int checkmain = 0;checkmain < datesarray.size();checkmain++) {
                    Date temp = format.parse(datesarray.get(checkmain));
                    temp_correct =  datesarray.get(checkmain);
                    temp_date = datesarray.get(checkmain);
                    temp_correct_index = checkmain;
                    for (int check = checkmain; check < datesarray.size(); check++) {

                        if ((format.parse(datesarray.get(check))).before(temp)) {
                            System.out.println("check main " + datesarray.get(checkmain) + " check" +datesarray.get(check));

                            temp = format.parse(datesarray.get(check));
                            temp_correct = datesarray.get(check);
                            temp_correct_index =  check;

                            temp_date = datesarray.get(check);
                        }


                    }
                    datesarray.set(temp_correct_index,datesarray.get(checkmain));
                    datesarray.set(checkmain, temp_correct);

                    System.out.println("sorting "+ checkmain + " " +datesarray);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Date d1,d2;
                System.out.println("this is datesarray final " + datesarray);
                for(int findfreeindex = 0;findfreeindex < datesarray.size();findfreeindex++)
                {
                    try {
                        d1 = format.parse(datesarray.get(findfreeindex));
                        if(findfreeindex == 0)
                        {
                            d1 = format.parse( "08:00:00");
                            starttime = "08:00:00";
                            d2 =  format.parse(datesarray.get(0));
                            endtime = datesarray.get(0);
                        }
                        else if(findfreeindex + 1 ==datesarray.size() )
                        {

                            d2 = format.parse("17:30:00");
                            endtime = "17:30:00";
                            d1 = format.parse(datesarray.get(findfreeindex));
                            starttime = datesarray.get(findfreeindex);
                        }
                        else if(findfreeindex % 2 == 0)
                        {
                            continue;
                        }
                        else if(findfreeindex + 1 != datesarray.size())
                        {
                            d2 = format.parse(datesarray.get(findfreeindex + 1));
                            endtime  = datesarray.get(findfreeindex + 1);
                            d1 = format.parse(datesarray.get(findfreeindex ));
                            starttime = datesarray.get(findfreeindex);

                        }

                        else
                        {
                            d2 = null;
                        }

                        long diff = d2.getTime() - d1.getTime();
                        System.out.println("change " + "d1 : " + d1 + " d2 : " +d2 );
                        System.out.println("this the remaining time diff minutes " + diff/(60*1000)%60);
                        System.out.println("this is time hours" + diff/(60*60*1000)%24);
                        remain_hour =diff/(60*60*1000)%24;
                        remian_min =diff/(60*1000)%60 ;
                        if((remain_hour >= Long.parseLong(hh) && remian_min >= Long.parseLong(mm)) && (remain_hour > Long.parseLong(hh)))
                        {
                            dispaytime = "May " + day+"\n" +"Start - Time : "+ starttime +"\n" +"End - Time : "+ endtime+"\n";
                            list_of_free_times.add(dispaytime);
                        }

                    }catch (Exception e)
                    {
                        System.out.println("error in try catch" + e);
                    }
                }

                free_slot_adapter.notifyDataSetChanged();


            }
        });


        return viewfree;
    }

}
