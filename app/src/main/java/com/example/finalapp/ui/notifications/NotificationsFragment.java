package com.example.finalapp.ui.notifications;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalapp.AsynchronousTask;
import com.example.finalapp.R;
import com.example.finalapp.model.TimeTableDays;
import com.example.finalapp.ConnectionReceiver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class NotificationsFragment extends Fragment {

    private TimeTableDays timeSlot = null;
    private EditText txtSubjectID, txtBatchGroup;
    private TextView txtStartTime, txtEndTime, txtHallNumber, txtLecIns, spinnerFaculty, spinnerYear, spinnerSemester, spinnerDay;
    private Spinner spinnerClassType;
    private String subjectID = "", batchGroup = "", classType = "", combine_id = "";
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private boolean status;
    private ProgressBar progressDelete;
    private RelativeLayout loadingLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        Button btnSearch = root.findViewById(R.id.btnDeleteSearch);
        Button btnDeleteReset = root.findViewById(R.id.btnDeleteReset);
        txtSubjectID = root.findViewById(R.id.txtDeleteSubjectCode);
        txtBatchGroup = root.findViewById(R.id.txtDeleteBatchGroup);
        txtHallNumber = root.findViewById(R.id.txtDeleteHallLab);
        txtLecIns = root.findViewById(R.id.txtDeleteLecIns);
        txtStartTime = root.findViewById(R.id.txtDeleteStartTime);
        txtEndTime = root.findViewById(R.id.txtDeleteEndTime);
        spinnerClassType = root.findViewById(R.id.spinnerDeleteClassType);
        spinnerFaculty = root.findViewById(R.id.spinnerDeleteFaculty);
        spinnerYear = root.findViewById(R.id.spinnerDeleteYear);
        spinnerSemester = root.findViewById(R.id.spinnerDeleteSemester);
        spinnerDay = root.findViewById(R.id.spinnerDeleteDay);
        Button btnResetDelete = root.findViewById(R.id.btnResetDelete);
        Button btnDelete = root.findViewById(R.id.btnDelete);
        progressDelete = root.findViewById(R.id.loadingDelete);
        loadingLayout = root.findViewById(R.id.loadingPanelDelete);

        progressDelete.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> classTypeSpinner = ArrayAdapter.createFromResource(getContext(), R.array.classType, android.R.layout.simple_spinner_item);
        classTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassType.setAdapter(classTypeSpinner);

        spinnerClassType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    classType = adapterView.getItemAtPosition(i).toString().trim();
                else
                    classType = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                classType = "";
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingLayout.setVisibility(View.VISIBLE);
                progressDelete.setVisibility(View.VISIBLE);


                subjectID = txtSubjectID.getText().toString().trim();
                batchGroup = txtBatchGroup.getText().toString().trim().replaceAll("\\s+", "_").replace(".", "_");

                if (subjectID.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (classType.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (batchGroup.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else {




                    if (ConnectionReceiver.getIntConnectionStatus() && ConnectionReceiver.getNetConnectionStatus()) {

                        try {

                            database = FirebaseDatabase.getInstance();

                            dbRef = database.getReference("TimeTable");

                            combine_id = subjectID + "_" + classType + "_" + batchGroup;

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    if (dataSnapshot.hasChildren()) {

                                        status = false;

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                            if (Objects.requireNonNull(snapshot.getValue(TimeTableDays.class)).getCombine_id().equals(combine_id)) {

                                                status = true;

                                                timeSlot = snapshot.getValue(TimeTableDays.class);


                                                if (timeSlot != null) {
                                                    spinnerDay.setText(timeSlot.getDay());
                                                }
                                                spinnerSemester.setText(String.format(Locale.getDefault(), "%d", timeSlot.getSem()));
                                                spinnerYear.setText(String.format(Locale.getDefault(), "%d", timeSlot.getYear()));
                                                spinnerFaculty.setText(timeSlot.getFaculty());
                                                txtStartTime.setText(timeSlot.getStarttime());
                                                txtEndTime.setText(timeSlot.getEndtime());
                                                txtHallNumber.setText(timeSlot.getHallno());

                                                String str = "";

                                                for (String lecStr : timeSlot.getLecturer()) {

                                                    str = str.concat(lecStr.trim().concat("\n"));


                                                }

                                                txtLecIns.setText(str);

                                                break;
                                            } else {
                                                status = false;

                                            }
                                        }

                                        if (status) {
                                            progressDelete.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), getResources().getString(R.string.resultsFound), Toast.LENGTH_SHORT).show();


                                        } else {
                                            progressDelete.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), getResources().getString(R.string.noResults), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        progressDelete.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    progressDelete.setVisibility(View.GONE);
                                    loadingLayout.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (DatabaseException e) {
                            progressDelete.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressDelete.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

                    }



                }


            }
        });

        btnDeleteReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.VISIBLE);
                progressDelete.setVisibility(View.VISIBLE);
                timeSlot = null;
                txtBatchGroup.setText("");
                txtSubjectID.setText("");
                spinnerClassType.setSelection(0);
                progressDelete.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });

        btnResetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.VISIBLE);
                progressDelete.setVisibility(View.VISIBLE);
                timeSlot = null;
                txtStartTime.setText("");
                txtLecIns.setText("");
                txtHallNumber.setText("");
                txtEndTime.setText("");
                spinnerYear.setText("");
                spinnerSemester.setText("");
                spinnerFaculty.setText("");
                spinnerDay.setText("");
                progressDelete.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingLayout.setVisibility(View.VISIBLE);
                progressDelete.setVisibility(View.VISIBLE);


                subjectID = txtSubjectID.getText().toString().trim();
                batchGroup = txtBatchGroup.getText().toString().trim().replaceAll("\\s+", "_").replace(".", "_");


                if (subjectID.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (batchGroup.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (classType.isEmpty()) {
                    progressDelete.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else {

                    if (timeSlot != null) {
                        progressDelete.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);

                        AlertDialog confirmDelete = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                                //set message, title, and icon
                                .setTitle(getResources().getString(R.string.confirmDelete))
                                .setMessage(getResources().getString(R.string.deleteConfirmation))
                                .setIcon(R.drawable.ic_delete_forever_black_24dp)

                                .setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        loadingLayout.setVisibility(View.VISIBLE);
                                        progressDelete.setVisibility(View.VISIBLE);


                                        if (ConnectionReceiver.getIntConnectionStatus() && ConnectionReceiver.getNetConnectionStatus()) {

                                            try {

                                                database = FirebaseDatabase.getInstance();
                                                dbRef = database.getReference("TimeTable");

                                                dbRef.child(timeSlot.getCombine_id()).removeValue();

                                                clearFields();
                                                progressDelete.setVisibility(View.GONE);
                                                loadingLayout.setVisibility(View.GONE);

                                                Toast.makeText(getContext(), getResources().getString(R.string.deleteSuccess), Toast.LENGTH_SHORT).show();

                                            } catch (DatabaseException e) {
                                                progressDelete.setVisibility(View.GONE);
                                                loadingLayout.setVisibility(View.GONE);
                                                Toast.makeText(getContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();

                                            }


                                        } else {
                                            timeSlot = null;
                                            progressDelete.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                                        }


                                        dialog.dismiss();
                                    }

                                })


                                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        progressDelete.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        dialog.dismiss();

                                    }
                                }).create();
                        confirmDelete.show();


                    } else {
                        progressDelete.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.noDelete), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    private void clearFields() {
        loadingLayout.setVisibility(View.VISIBLE);
        progressDelete.setVisibility(View.VISIBLE);
        timeSlot = null;
        txtSubjectID.setText("");
        txtBatchGroup.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
        txtHallNumber.setText("");
        txtLecIns.setText("");
        spinnerClassType.setSelection(0);
        spinnerDay.setText("");
        spinnerFaculty.setText("");
        spinnerSemester.setText("");
        spinnerYear.setText("");
        progressDelete.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }


}