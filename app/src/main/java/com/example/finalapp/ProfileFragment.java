package com.example.finalapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ListView listviewprofile;
    private ArrayAdapter<String> arrayadapterprofile;
    private ArrayList<String> profile_details_list = new ArrayList<>();
    private DatabaseReference reffprodetails,reffdelpro;
    private View viewpro;
    private  Button delbtn;
    private SystemUser suser =  new SystemUser();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewpro = inflater.inflate(R.layout.fragment_notification,container,false);


        arrayadapterprofile =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,profile_details_list);
        listviewprofile =  (ListView) viewpro.findViewById(R.id.listview1);
        delbtn = (Button) viewpro.findViewById(R.id.btndel);
        listviewprofile.setAdapter(arrayadapterprofile);
        reffprodetails = FirebaseDatabase.getInstance().getReference().child("Users").child(suser.getId());
        return viewpro;
    }

}
