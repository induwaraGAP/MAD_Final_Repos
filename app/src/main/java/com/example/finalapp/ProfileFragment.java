package com.example.finalapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private  SystemUser usrpro;
    private  Button btndel,btnup,logoutbtn;
    private  DatabaseReference reffdel;
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
        btndel = (Button) viewpro.findViewById(R.id.btndel);
        btnup = (Button) viewpro.findViewById(R.id.btnup);
        logoutbtn = (Button) viewpro.findViewById(R.id.btnlogout);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotologscreen = new Intent(getContext(),Logincheckuser.class);
                startActivity(gotologscreen);
            }
        });

        id.setText(suser.getId().toString());
        name.setText(suser.getName().toString());
        year.setText( String.valueOf( suser.getYear()));
        semester.setText(String.valueOf(suser.getSem()));
        faculty.setText(suser.getFaculty());
        group.setText(suser.getGroup());
        type.setText(suser.getType());

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregister =  new Intent(getContext(),LoginActivity.class);
                gotoregister.putExtra("id",suser.getId().toString());
                gotoregister.putExtra("type",suser.getType().toString());
                startActivity(gotoregister);
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reffdel= FirebaseDatabase.getInstance().getReference().child("Users").child(suser.getId());

                                reffdel.removeValue();
                                Intent gotologinchecker =  new Intent(getContext(),Logincheckuser.class);
                                startActivity(gotologinchecker);

                            }
                        })


                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return viewpro;
    }

}
