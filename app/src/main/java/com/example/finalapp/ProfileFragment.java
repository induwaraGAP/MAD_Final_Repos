package com.example.finalapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalapp.models.SystemUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView id, name, year, semester, faculty, group, type;
    private SystemUser suser =  new SystemUser();
    private  View viewpro;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewpro = inflater.inflate(R.layout.fragment_profile,container,false);

        id = (TextView) viewpro.findViewById(R.id.f_id);
        name =  (TextView) viewpro.findViewById(R.id.f_name);
        year =  (TextView) viewpro.findViewById(R.id.f_year);
        semester =  (TextView) viewpro.findViewById(R.id.f_semester);
        faculty =  (TextView) viewpro.findViewById(R.id.f_faculty);
        group =  (TextView) viewpro.findViewById(R.id.f_group);
        type =  (TextView) viewpro.findViewById(R.id.f_type);

        return viewpro;
    }

}
