package com.example.finalapp.ui.dashboard;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalapp.ConnectionReceiver;
import com.example.finalapp.R;
import com.example.finalapp.model.TimeTableDays;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private TimeTableDays timeSlot = null;
    private Calendar calendar;
    private SimpleDateFormat format;
    private Date start, end, startNew, endNew;
    private int currentHour;
    private int currentMinute;
    private EditText txtSubjectID, txtBatchGroup, txtStartTime, txtEndTime, txtHallNumber, txtLecIns;
    private TimePickerDialog timePickerDialog;
    private Spinner spinnerClassType, spinnerFaculty, spinnerYear, spinnerSemester, spinnerDay;
    private String subjectID = "", batchGroup = "", classType = "", faculty = "", startTime = "", endTime = "", dayWeek = "", hall = "", combine_id = "";
    private List<String> lecturer;
    private HashSet<String> lec;
    private int yearValue = 0, semesterValue = 0, position = 0;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private boolean status = true, bool = true, parseStatus = true;
    private ProgressBar progressUpdate;
    private RelativeLayout loadingLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        Button btnSearch = root.findViewById(R.id.btnSearch);
        Button btnUpdateReset = root.findViewById(R.id.btnUpdateReset);
        txtSubjectID = root.findViewById(R.id.txtUpdateSubjectCode);
        txtBatchGroup = root.findViewById(R.id.txtUpdateBatchGroup);
        txtHallNumber = root.findViewById(R.id.txtUpdateHallLab);
        txtLecIns = root.findViewById(R.id.txtUpdateLecIns);
        txtStartTime = root.findViewById(R.id.txtUpdateStartTime);
        txtEndTime = root.findViewById(R.id.txtUpdateEndTime);
        spinnerClassType = root.findViewById(R.id.spinnerUpdateClassType);
        spinnerFaculty = root.findViewById(R.id.spinnerUpdateFaculty);
        spinnerYear = root.findViewById(R.id.spinnerUpdateYear);
        spinnerSemester = root.findViewById(R.id.spinnerUpdateSemester);
        spinnerDay = root.findViewById(R.id.spinnerUpdateDay);
        Button btnResetUpdate = root.findViewById(R.id.btnResetUpdate);
        Button btnUpdate = root.findViewById(R.id.btnUpdate);
        progressUpdate = root.findViewById(R.id.loadingUpdate);
        loadingLayout = root.findViewById(R.id.loadingPanelUpdate);

        progressUpdate.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> classTypeSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.classType, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> facultySpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.faculties, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> yearSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.years, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> semesterSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.semesters, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> daySpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.dayOfTheWeek, android.R.layout.simple_spinner_item);

        classTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerClassType.setAdapter(classTypeSpinner);
        spinnerFaculty.setAdapter(facultySpinner);
        spinnerYear.setAdapter(yearSpinner);
        spinnerSemester.setAdapter(semesterSpinner);
        spinnerDay.setAdapter(daySpinner);

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

        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    faculty = adapterView.getItemAtPosition(i).toString().trim();
                else
                    faculty = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                faculty = "";
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    yearValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString().trim());
                else
                    yearValue = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                yearValue = 0;
            }
        });

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    semesterValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString().trim());
                else
                    semesterValue = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                semesterValue = 0;
            }
        });

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    dayWeek = adapterView.getItemAtPosition(i).toString().trim();
                else
                    dayWeek = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dayWeek = "";
            }
        });


        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                showTimePickerForStartTime(view);
            }
        });


        txtStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerForStartTime(view);
                }
            }
        });


        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                showTimePickerForEndTime(view);
            }
        });


        txtEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerForEndTime(view);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingLayout.setVisibility(View.VISIBLE);
                progressUpdate.setVisibility(View.VISIBLE);


                subjectID = txtSubjectID.getText().toString().trim();
                batchGroup = txtBatchGroup.getText().toString().trim().replaceAll("\\s+", "_").replace(".", "_");

                if (subjectID.isEmpty()) {

                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (classType.isEmpty()) {

                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (batchGroup.isEmpty()) {

                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
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

                                                position = 0;

                                                for (String str : getResources().getStringArray(R.array.faculties)) {

                                                    if (timeSlot.getFaculty().equals(str)) {
                                                        spinnerFaculty.setSelection(position);
                                                        break;
                                                    }

                                                    position++;
                                                }
                                                position = 0;

                                                for (String str : getResources().getStringArray(R.array.years)) {

                                                    if (timeSlot.getYear().toString().equals(str)) {
                                                        spinnerYear.setSelection(position);
                                                        break;
                                                    }

                                                    position++;
                                                }

                                                position = 0;

                                                for (String str : getResources().getStringArray(R.array.semesters)) {

                                                    if (timeSlot.getSem().toString().equals(str)) {
                                                        spinnerSemester.setSelection(position);
                                                        break;
                                                    }

                                                    position++;
                                                }

                                                position = 0;

                                                for (String str : getResources().getStringArray(R.array.dayOfTheWeek)) {

                                                    if (timeSlot.getDay().equals(str)) {
                                                        spinnerDay.setSelection(position);
                                                        break;
                                                    }

                                                    position++;
                                                }

                                                position = 0;

                                                txtStartTime.setText(timeSlot.getStarttime());
                                                txtEndTime.setText(timeSlot.getEndtime());
                                                txtHallNumber.setText(timeSlot.getHallno());
                                                String str = "";

                                                for (String lecStr : timeSlot.getLecturer()) {

                                                    str = str.concat(lecStr.concat("\n"));


                                                }

                                                txtLecIns.setText(str);

                                                break;
                                            } else {
                                                status = false;

                                            }
                                        }

                                        if (status) {


                                            progressUpdate.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.resultsFound), Toast.LENGTH_SHORT).show();

                                        } else {


                                            progressUpdate.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.noResults), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {


                                        progressUpdate.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                    progressUpdate.setVisibility(View.GONE);
                                    loadingLayout.setVisibility(View.GONE);
                                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (DatabaseException e) {


                            progressUpdate.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressUpdate.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        timeSlot = null;
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

                    }


                }


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingLayout.setVisibility(View.VISIBLE);
                progressUpdate.setVisibility(View.VISIBLE);


                subjectID = txtSubjectID.getText().toString().trim();
                batchGroup = txtBatchGroup.getText().toString().trim().replaceAll("\\s+", "_").replace(".", "_");
                startTime = txtStartTime.getText().toString();
                endTime = txtEndTime.getText().toString();
                hall = txtHallNumber.getText().toString();
                String[] arr = txtLecIns.getText().toString().trim().split("\n");

                lec = new HashSet<>();

                for (String str : arr) {
                    lec.add(str.trim());
                }

                lecturer = new ArrayList<>();

                lecturer.addAll(lec);

                if (subjectID.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (batchGroup.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (classType.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (faculty.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (yearValue == 0) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (semesterValue == 0) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (dayWeek.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (startTime.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (endTime.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (hall.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (lecturer.isEmpty()) {
                    progressUpdate.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else {


                    if (ConnectionReceiver.getIntConnectionStatus() && ConnectionReceiver.getNetConnectionStatus()) {

                        if (timeSlot != null) {

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        format = new SimpleDateFormat("HH:mm", Locale.getDefault());

                                        try {

                                            startNew = format.parse(startTime);
                                            endNew = format.parse(endTime);

                                            start = format.parse(Objects.requireNonNull(data.getValue(TimeTableDays.class)).getStarttime());
                                            end = format.parse(Objects.requireNonNull(data.getValue(TimeTableDays.class)).getEndtime());

                                        } catch (ParseException e) {
                                            parseStatus = false;

                                            break;
                                        }


                                        if (startNew.before(endNew)) {
                                            bool = true;

                                            for (String lec : Objects.requireNonNull(data.getValue(TimeTableDays.class)).getLecturer()) {

                                                for (String lecStr : lecturer) {
                                                    if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) && lecStr.equals(lec)) {


                                                        if (endNew.before(start) || endNew.equals(start)) {

                                                            status = true;
                                                            break;

                                                        } else if (startNew.after(end) || startNew.equals(end)) {
                                                            status = true;
                                                            break;

                                                        } else {

                                                            status = false;
                                                            break;
                                                        }


                                                    } else {
                                                        status = true;

                                                    }
                                                }


                                            }

                                            if (!status)
                                                break;
                                            else {
                                                if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) &&
                                                        Objects.requireNonNull(data.getValue(TimeTableDays.class)).getGroup().equals(batchGroup)) {


                                                    if (endNew.before(start) || endNew.equals(start)) {
                                                        status = true;
                                                        break;

                                                    } else if (startNew.after(end) || startNew.equals(end)) {
                                                        status = true;
                                                        break;

                                                    } else {

                                                        status = false;
                                                        break;
                                                    }


                                                } else if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) &&
                                                        Objects.requireNonNull(data.getValue(TimeTableDays.class)).getHallno().equals(hall)) {

                                                    if (endNew.before(start) || endNew.equals(start)) {
                                                        status = true;
                                                        break;

                                                    } else if (startNew.after(end) || startNew.equals(end)) {
                                                        status = true;
                                                        break;

                                                    } else {

                                                        status = false;
                                                        break;
                                                    }

                                                } else {
                                                    status = true;

                                                }

                                            }

                                        } else {
                                            bool = false;
                                            break;
                                        }
                                    }


                                    if (status && bool && parseStatus) {

                                        try {

                                            dbRef.child(timeSlot.getCombine_id()).child("faculty").setValue(faculty);
                                            dbRef.child(timeSlot.getCombine_id()).child("year").setValue(yearValue);
                                            dbRef.child(timeSlot.getCombine_id()).child("sem").setValue(semesterValue);
                                            dbRef.child(timeSlot.getCombine_id()).child("day").setValue(dayWeek);
                                            dbRef.child(timeSlot.getCombine_id()).child("starttime").setValue(startTime);
                                            dbRef.child(timeSlot.getCombine_id()).child("endtime").setValue(endTime);
                                            dbRef.child(timeSlot.getCombine_id()).child("hallno").setValue(hall);
                                            dbRef.child(timeSlot.getCombine_id()).child("lecturer").setValue(lecturer);

                                            clearFields();

                                            progressUpdate.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);

                                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.updateSuccess), Toast.LENGTH_SHORT).show();


                                        } catch (DatabaseException e) {
                                            progressUpdate.setVisibility(View.GONE);
                                            loadingLayout.setVisibility(View.GONE);
                                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (status && parseStatus) {
                                        progressUpdate.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.timeGapError), Toast.LENGTH_SHORT).show();
                                    } else if (status) {
                                        progressUpdate.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);

                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.parseError), Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressUpdate.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.timeslotTaken), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    progressUpdate.setVisibility(View.GONE);
                                    loadingLayout.setVisibility(View.GONE);
                                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            progressUpdate.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.noSearch), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressUpdate.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        timeSlot = null;
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

                    }


                }


            }
        });


        btnUpdateReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.VISIBLE);
                progressUpdate.setVisibility(View.VISIBLE);
                timeSlot = null;
                txtBatchGroup.setText("");
                txtSubjectID.setText("");
                spinnerClassType.setSelection(0);
                progressUpdate.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });

        btnResetUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.VISIBLE);
                progressUpdate.setVisibility(View.VISIBLE);
                timeSlot = null;
                txtStartTime.setText("");
                txtLecIns.setText("");
                txtHallNumber.setText("");
                txtEndTime.setText("");
                spinnerYear.setSelection(0);
                spinnerSemester.setSelection(0);
                spinnerFaculty.setSelection(0);
                spinnerDay.setSelection(0);
                progressUpdate.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTimePickerForStartTime(View view) {

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);


        txtStartTime.setShowSoftInputOnFocus(false);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        timePickerDialog = new TimePickerDialog(Objects.requireNonNull(getActivity()).getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {


                txtStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, true);

        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTimePickerForEndTime(View view) {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        txtEndTime.setShowSoftInputOnFocus(false);

        timePickerDialog = new TimePickerDialog(Objects.requireNonNull(getActivity()).getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {


                txtEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, true);

        timePickerDialog.show();
    }


    private void clearFields() {
        loadingLayout.setVisibility(View.VISIBLE);
        progressUpdate.setVisibility(View.VISIBLE);
        timeSlot = null;
        txtSubjectID.setText("");
        txtBatchGroup.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
        txtHallNumber.setText("");
        txtLecIns.setText("");
        spinnerClassType.setSelection(0);
        spinnerDay.setSelection(0);
        spinnerFaculty.setSelection(0);
        spinnerSemester.setSelection(0);
        spinnerYear.setSelection(0);
        progressUpdate.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }


}